package com.sidhartsingh.controller;

import com.sidhartsingh.exception.UserException;
import com.sidhartsingh.modal.Ride;
import com.sidhartsingh.modal.User;
import com.sidhartsingh.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.*;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> findUserByIdHandler(@PathVariable Integer userId) throws UserException {
        User user = userService.findUserById(userId);

        return new ResponseEntity<>(user, HttpStatus.ACCEPTED);
    }

    @GetMapping("/profile")
    public ResponseEntity<User> getReqUserProfileHandler(@RequestHeader("Authorization") String jwt) throws UserException {
        User user = userService.getReqUserProfile(jwt);

        return new ResponseEntity<>(user, HttpStatus.ACCEPTED);
    }

    @GetMapping("/rides/completed")
    public ResponseEntity<List<Ride>> getCompletedRidesHandler(@RequestHeader("Authorization") String jwt) throws UserException {
        User user = userService.getReqUserProfile(jwt);
        List<Ride> rides = userService.completedRides(user.getId());

        return new ResponseEntity<>(rides, HttpStatus.ACCEPTED);
    }
}
