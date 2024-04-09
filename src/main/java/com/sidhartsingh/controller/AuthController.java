package com.sidhartsingh.controller;

import com.sidhartsingh.config.JwtUtil;
import com.sidhartsingh.domain.UserRole;
import com.sidhartsingh.exception.UserException;
import com.sidhartsingh.modal.Driver;
import com.sidhartsingh.modal.User;
import com.sidhartsingh.repository.DriverRepository;
import com.sidhartsingh.repository.UserRepository;
import com.sidhartsingh.request.DriverSignupRequest;
import com.sidhartsingh.request.LoginRequest;
import com.sidhartsingh.request.SignupRequest;
import com.sidhartsingh.response.JwtResponse;
import com.sidhartsingh.service.CustomUserDetailService;
import com.sidhartsingh.service.DriverService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserRepository userRepository;
    private final DriverRepository driverRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final CustomUserDetailService customUserDetailService;
    private final DriverService driverService;

    public AuthController(UserRepository userRepository, DriverRepository driverRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil, CustomUserDetailService customUserDetailService, DriverService driverService) {
        this.userRepository = userRepository;
        this.driverRepository = driverRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.customUserDetailService = customUserDetailService;
        this.driverService = driverService;
    }

    @PostMapping("/user/signup")
    public ResponseEntity<JwtResponse> signupHandler(@RequestBody SignupRequest request) throws UserException {
        String fullName = request.getFullName();
        String email = request.getEmail();
        String mobile = request.getMobile();
        String password = request.getPassword();

        User user = userRepository.findByEmail(email);
        if(user != null){
            throw new UserException("User already exists with email " + email);
        }

        String encodedPassword = passwordEncoder.encode(password);
        User newUser = new User();
        newUser.setEmail(email);
        newUser.setFullName(fullName);
        newUser.setPassword(encodedPassword);
        newUser.setMobile(mobile);
        newUser.setRote(UserRole.USER);

        User savedUser = userRepository.save(newUser);

        Authentication authentication = new UsernamePasswordAuthenticationToken(savedUser.getEmail(), savedUser.getPassword());

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtil.generateJwtToken(authentication);

        JwtResponse response = new JwtResponse();
        response.setJwt(jwt);
        response.setAuthenticated(true);
        response.setError(false);
        response.setType(UserRole.USER);
        response.setMessage("Account Created Successfully. " + savedUser.getFullName());

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtResponse> signIn(@RequestBody LoginRequest loginRequest) {
        String username = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        Authentication authentication = authenticate(password, username);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtil.generateJwtToken(authentication);

        JwtResponse response = new JwtResponse();
        response.setJwt(jwt);
        response.setAuthenticated(true);
        response.setError(false);
        response.setType(UserRole.USER);
        response.setMessage("Account logged in successfully. ");

        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @PostMapping("/driver/signup")
    public ResponseEntity<JwtResponse> driverSignup(@RequestBody DriverSignupRequest request){
        Driver driver = driverRepository.findByEmail(request.getEmail());

        JwtResponse jwtResponse = new JwtResponse();
        if(driver != null){
            jwtResponse.setAuthenticated(false);
            jwtResponse.setErrorDetails("Email already exists.");
            jwtResponse.setError(true);

            return new ResponseEntity<>(jwtResponse, HttpStatus.BAD_REQUEST);
        }

        Driver newDriver = driverService.registerDriver(request);

        Authentication authentication = new UsernamePasswordAuthenticationToken(newDriver.getEmail(), newDriver.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtil.generateJwtToken(authentication);

        jwtResponse.setJwt(jwt);
        jwtResponse.setAuthenticated(true);
        jwtResponse.setError(false);
        jwtResponse.setType(UserRole.USER);
        jwtResponse.setMessage("Account logged in successfully. ");
        jwtResponse.setMessage("Account Created Successfully. " + newDriver.getFullName());

        return new ResponseEntity<>(jwtResponse, HttpStatus.ACCEPTED);
    }

    private Authentication authenticate(String username, String password){
        UserDetails userDetails = customUserDetailService.loadUserByUsername(username);
        if(userDetails == null)
            throw new BadCredentialsException("Invalid username or password from authentication method");

        if(!passwordEncoder.matches(password, userDetails.getPassword()))
            throw new BadCredentialsException("Invalid username or password");

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
