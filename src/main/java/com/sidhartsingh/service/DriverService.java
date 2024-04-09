package com.sidhartsingh.service;

import com.sidhartsingh.exception.DriverException;
import com.sidhartsingh.modal.Driver;
import com.sidhartsingh.modal.Ride;
import com.sidhartsingh.request.DriverSignupRequest;

import java.util.List;

public interface DriverService {
     Driver registerDriver(DriverSignupRequest driverSignupRequest);
     List<Driver> getAvailableDrivers(Double pickupLatitude, Double pickupLongitude, Ride ride);
     Driver findNearestDriver(List<Driver> availableDrivers, Double pickupLatitude, Double pickupLongitude);
     Driver getRequestedDriverProfile(String jwt) throws DriverException; // getReqDriverProfile
     Ride getDriversCurrentRide(Integer driverId) throws DriverException;
     List<Ride> getAllocatedRides(Integer driverId) throws DriverException;
     Driver findDriverById(Integer driverId) throws DriverException;
     List<Ride> completedRides(Integer driverId) throws DriverException;
}
