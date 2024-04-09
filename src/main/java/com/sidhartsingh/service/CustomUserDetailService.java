package com.sidhartsingh.service;

import com.sidhartsingh.modal.User;
import com.sidhartsingh.modal.Driver;
import com.sidhartsingh.repository.DriverRepository;
import com.sidhartsingh.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;
    private final DriverRepository driverRepository;

    public CustomUserDetailService(UserRepository userRepository, DriverRepository driverRepository) {
        this.userRepository = userRepository;
        this.driverRepository = driverRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

        User user = userRepository.findByEmail(username);
        if(user != null){
            return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), grantedAuthorities);
        }

        Driver driver = driverRepository.findByEmail(username);
        if(driver != null){
            return new org.springframework.security.core.userdetails.User(driver.getEmail(), driver.getPassword(), grantedAuthorities);
        }

        throw new UsernameNotFoundException("User not found with email " + username);
    }
}
