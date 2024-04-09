package com.sidhartsingh.olabackend.service;

import com.sidhartsingh.olabackend.exception.DriverException;
import com.sidhartsingh.olabackend.exception.RideException;
import com.sidhartsingh.olabackend.modal.Driver;
import com.sidhartsingh.olabackend.modal.Ride;
import com.sidhartsingh.olabackend.modal.RideStatus;
import com.sidhartsingh.olabackend.modal.User;
import com.sidhartsingh.olabackend.repository.DriverRepository;
import com.sidhartsingh.olabackend.repository.RideRepository;
import com.sidhartsingh.olabackend.request.RideRequest;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class RideServiceImpl implements RideService{
    private final DriverService driverService;
    private final RideRepository rideRepository;
    private final Calculator calculator;
    private final DriverRepository driverRepository;
//    private final NotificationRepository notificationRepository;

    public RideServiceImpl(DriverService driverService, RideRepository rideRepository, Calculator calculator, DriverRepository driverRepository) {
        this.driverService = driverService;
        this.rideRepository = rideRepository;
        this.calculator = calculator;
        this.driverRepository = driverRepository;
    }

    @Override
    public Ride requestRide(RideRequest rideRequest, User user) throws DriverException {
        double pickupLatitude  = rideRequest.getPickupLatitude();
        double pickupLongitude = rideRequest.getPickupLongitude();
        double destinationLatitude = rideRequest.getDestinationLatitude();
        double destinationLongitude = rideRequest.getDestinationLongitude();
        String pickupArea = rideRequest.getPickupArea();
        String destinationArea = rideRequest.getDestinationArea();

        Ride existingRide = new Ride();

        List<Driver> availableDrivers = driverService.getAvailableDrivers(pickupLatitude, pickupLongitude, existingRide);
        Driver nearestDriver = driverService.findNearestDriver(availableDrivers, pickupLatitude, pickupLongitude);
        if(nearestDriver == null)
            throw new DriverException("No Driver Available");

        System.out.println("Duration ------ before ride");

        Ride ride = createRideRequest(user, nearestDriver,
                    pickupLatitude, pickupLongitude,
                    destinationLatitude, destinationLongitude,
                    pickupArea, destinationArea);

        System.out.println("Duration ------ after ride");

        return ride;
    }

    @Override
    public Ride createRideRequest(User user, Driver nearestDriver, double pickupLatitude, double pickupLongitude, double destinationLatitude, double destinationLongitude, String pickupArea, String destinationArea) {
        Ride ride = new Ride();

        ride.setDriver(nearestDriver);
        ride.setUser(user);
        ride.setPickupLatitude(pickupLatitude);
        ride.setPickupLongitude(pickupLongitude);
        ride.setDestinationLatitude(destinationLatitude);
        ride.setDestinationLongitude(destinationLongitude);
        ride.setDestinationArea(destinationArea);

        return rideRepository.save(ride);
    }

    @Override
    public void acceptRide(Integer rideId) throws RideException {
        Ride ride = findRideById(rideId);
        ride.setStatus(RideStatus.ACCEPTED);

        Driver driver = ride.getDriver();
        driver.setCurrentRide(ride);

        Random random = new Random();
        int OTP = random.nextInt(9000) + 1000;
        ride.setOTP(OTP);

        driverRepository.save(driver);
        rideRepository.save(ride);
    }

    @Override
    public void declineRide(Integer rideId, Integer driverId) throws RideException {
        Ride ride = findRideById(rideId);
        ride.getDeclinedDrivers().add(driverId);

        List<Driver> availableDrivers = driverService.getAvailableDrivers(ride.getPickupLatitude(), ride.getPickupLongitude(), ride);
        Driver nearestDriver = driverService.findNearestDriver(availableDrivers, ride.getPickupLatitude(), ride.getPickupLongitude());

        ride.setDriver(nearestDriver);

        rideRepository.save(ride);
    }

    @Override
    public void startRide(Integer rideId, int OTP) throws RideException {
        Ride ride = findRideById(rideId);

        if(OTP != ride.getOTP())
            throw new RideException("Please provide a valid OTP.");

        ride.setStatus(RideStatus.STARTED);
        ride.setStartTime(LocalDateTime.now());
        rideRepository.save(ride);
    }

    @Override
    public void completeRide(Integer rideId) throws RideException {
        Ride ride = findRideById(rideId);
        ride.setStatus(RideStatus.COMPLETED);
        ride.setEndTime(LocalDateTime.now());

        double distance = calculator.calculateDistance(ride.getDestinationLatitude(), ride.getDestinationLongitude(), ride.getPickupLatitude(), ride.getDestinationLongitude());

        LocalDateTime startTime = ride.getStartTime();
        LocalDateTime endTime = ride.getEndTime();
        Duration duration = Duration.between(startTime, endTime);
        long durationMilliSeconds = duration.toMillis();

        double fare = calculator.calculateFare(distance);
        ride.setDistance(Math.round(distance * 100.0) / 100.0);
        ride.setFare((double)Math.round(fare));
        ride.setDuration(durationMilliSeconds);
        ride.setEndTime(LocalDateTime.now());

        Driver driver = ride.getDriver();
        driver.getRides().add(ride);
        driver.setCurrentRide(null);

        Integer driversRevenue = (int) (driver.getTotalRevenue() + Math.round(fare * 0.8));
        driver.setTotalRevenue(driversRevenue);

        driverRepository.save(driver);
        rideRepository.save(ride);
    }

    @Override
    public void cancelRide(Integer rideId) throws RideException {
        Ride ride = findRideById(rideId);
        ride.setStatus(RideStatus.CANCELLED);

        rideRepository.save(ride);
    }

    @Override
    public Ride findRideById(Integer rideId) throws RideException {
        Optional<Ride> ride = rideRepository.findById(rideId);
        if(ride.isPresent())
            return ride.get();

        throw new RideException("Ride does not exists with Id: " + rideId);
    }
}
