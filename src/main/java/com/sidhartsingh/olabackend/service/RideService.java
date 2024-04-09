package com.sidhartsingh.olabackend.service;

import com.sidhartsingh.olabackend.exception.DriverException;
import com.sidhartsingh.olabackend.exception.RideException;
import com.sidhartsingh.olabackend.modal.Driver;
import com.sidhartsingh.olabackend.modal.Ride;
import com.sidhartsingh.olabackend.modal.User;
import com.sidhartsingh.olabackend.request.RideRequest;
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
