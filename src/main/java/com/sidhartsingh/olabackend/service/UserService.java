package com.sidhartsingh.olabackend.service;

import com.sidhartsingh.olabackend.exception.UserException;
import com.sidhartsingh.olabackend.modal.Ride;
import com.sidhartsingh.olabackend.modal.User;

import java.util.List;

public interface UserService {
    User createUser(User user) throws UserException;
    User getReqUserProfile(String token) throws UserException;
    User findUserById(Integer Id) throws UserException;
//    User findUserByEmail(String email) throws UserException;
    List<Ride> completedRides(Integer userId) throws UserException;
}
