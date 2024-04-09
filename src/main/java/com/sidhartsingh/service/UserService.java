package com.sidhartsingh.service;

import com.sidhartsingh.exception.UserException;
import com.sidhartsingh.modal.Ride;
import com.sidhartsingh.modal.User;

import java.util.List;

public interface UserService {
    User createUser(User user) throws UserException;
    User getReqUserProfile(String token) throws UserException;
    User findUserById(Integer Id) throws UserException;
//    User findUserByEmail(String email) throws UserException;
    List<Ride> completedRides(Integer userId) throws UserException;
}
