package com.sidhartsingh.service;

import com.sidhartsingh.exception.DriverException;
import com.sidhartsingh.exception.RideException;
import com.sidhartsingh.modal.Driver;
import com.sidhartsingh.modal.Ride;
import com.sidhartsingh.modal.User;
import com.sidhartsingh.request.RideRequest;
import org.springframework.stereotype.Service;

@Service
public interface RideService {
    Ride requestRide(RideRequest rideRequest, User user) throws DriverException;
    Ride createRideRequest(User user, Driver nearestDriver,
                           double pickupLatitude, double pickupLongitude,
                           double destinationLatitude, double destinationLongitude,
                           String pickupArea, String destinationArea);
    void acceptRide(Integer rideId) throws RideException;
    void declineRide(Integer rideId, Integer driverId) throws RideException;
    void startRide(Integer rideId, int OTP) throws RideException;
    void completeRide(Integer rideId) throws RideException;
    void cancelRide(Integer rideId) throws RideException;
    Ride findRideById(Integer rideId) throws RideException;

}
