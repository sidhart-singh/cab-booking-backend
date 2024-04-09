package com.sidhartsingh.olabackend.service;

import com.sidhartsingh.olabackend.config.JwtUtil;
import com.sidhartsingh.olabackend.exception.UserException;
import com.sidhartsingh.olabackend.modal.Ride;
import com.sidhartsingh.olabackend.modal.User;
import com.sidhartsingh.olabackend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public UserServiceImpl(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public User createUser(User user) throws UserException {
        return null;
    }

    @Override
    public User getReqUserProfile(String token) throws UserException {
        String email = jwtUtil.getEmailFromJwt(token);

        User user = userRepository.findByEmail(email);
        if(user != null)
            return user;

        throw new UserException("Invalid token.");
    }

    @Override
    public User findUserById(Integer Id) throws UserException {
        Optional<User> optionalUser = userRepository.findById(Id);
        if(optionalUser.isPresent())
                return optionalUser.get();

        throw new UserException("User not found with id: " + Id);
    }

    @Override
    public List<Ride> completedRides(Integer userId) throws UserException {
        List<Ride> completedRides = userRepository.getCompletedRides(userId);

        return completedRides;
    }
}
