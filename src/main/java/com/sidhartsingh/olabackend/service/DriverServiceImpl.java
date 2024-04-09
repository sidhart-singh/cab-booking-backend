package com.sidhartsingh.olabackend.service;

import com.sidhartsingh.olabackend.modal.*;
import com.sidhartsingh.olabackend.config.JwtUtil;
import com.sidhartsingh.olabackend.domain.UserRole;
import com.sidhartsingh.olabackend.exception.DriverException;
import com.sidhartsingh.olabackend.repository.DriverRepository;
import com.sidhartsingh.olabackend.repository.LicenseRepository;
import com.sidhartsingh.olabackend.repository.RideRepository;
import com.sidhartsingh.olabackend.repository.VehicleRepository;
import com.sidhartsingh.olabackend.request.DriverSignupRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DriverServiceImpl implements DriverService{
    private DriverRepository driverRepository;
    private Calculator distanceCalculator;
    private PasswordEncoder passwordEncoder;
    private JwtUtil jwtUtil;
    private VehicleRepository vehicleRepository;
    private LicenseRepository licenseRepository;
    private RideRepository rideRepository;

    public DriverServiceImpl(DriverRepository driverRepository, Calculator distanceCalculator, PasswordEncoder passwordEncoder, JwtUtil jwtUtil, VehicleRepository vehicleRepository, LicenseRepository licenseRepository, RideRepository rideRepository) {
        this.driverRepository = driverRepository;
        this.distanceCalculator = distanceCalculator;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.vehicleRepository = vehicleRepository;
        this.licenseRepository = licenseRepository;
        this.rideRepository = rideRepository;
    }

    @Override
    public Driver registerDriver(DriverSignupRequest driverSignupRequest) {
        License license = driverSignupRequest.getLicense();
        Vehicle vehicle = driverSignupRequest.getVehicle();

        License createdLicense =  new License();
        createdLicense.setLicenseState(license.getLicenseState());
        createdLicense.setLicenseNumber(license.getLicenseNumber());
        createdLicense.setLicenseExpiration(license.getLicenseExpiration());

        License savedLicense = licenseRepository.save(createdLicense);

        Vehicle createdVehicle = new Vehicle();
        createdVehicle.setId(vehicle.getId());
        createdVehicle.setCapacity(vehicle.getCapacity());
        createdVehicle.setColor(vehicle.getColor());
        createdVehicle.setLicensePlate(vehicle.getLicensePlate());
        createdVehicle.setMake(vehicle.getMake());
        createdVehicle.setModel(vehicle.getModel());
        createdVehicle.setYear(vehicle.getYear());

        Vehicle saveVehicle = vehicleRepository.save(createdVehicle);

        Driver createdDriver = new Driver();
        String encodedPassword = passwordEncoder.encode(driverSignupRequest.getPassword());
        createdDriver.setEmail(driverSignupRequest.getEmail());
        createdDriver.setFullName(driverSignupRequest.getName());
        createdDriver.setMobile(driverSignupRequest.getMobile());
        createdDriver.setPassword(encodedPassword);
        createdDriver.setLicense(savedLicense);
        createdDriver.setVehicle(saveVehicle);
        createdDriver.setRole(UserRole.DRIVER);
        createdDriver.setLatitude(driverSignupRequest.getLatitude());
        createdDriver.setLongitude(driverSignupRequest.getLongitude());

        Driver savedDriver = driverRepository.save(createdDriver);

        savedLicense.setDriver(savedDriver);
        saveVehicle.setDriver(savedDriver);

        licenseRepository.save(savedLicense);
        vehicleRepository.save(saveVehicle);

        return savedDriver;
    }

    @Override
    public List<Driver> getAvailableDrivers(Double pickupLatitude, Double pickupLongitude, Ride ride) {
        List<Driver> allDrivers = driverRepository.findAll();
        List<Driver> availableDrivers = new ArrayList<>();

        for(Driver driver : allDrivers){
            if(driver.getCurrentRide() != null && driver.getCurrentRide().getStatus() != RideStatus.COMPLETED
                || ride.getDeclinedDrivers().contains(driver.getId())){
                continue;
            }

            double driverLatitude = driver.getLatitude();
            double driverLongitude = driver.getLongitude();

//            double distance = distanceCalculator.calculateDistance(driverLatitude, driverLongitude, pickupLatitude, pickupLongitude);
//          check radius for nearness then add on condition
            availableDrivers.add(driver);
        }

        return availableDrivers;
    }

    @Override
    public Driver findNearestDriver(List<Driver> availableDrivers, Double pickupLatitude, Double pickupLongitude) {
        Driver nearestDriver = null;

        double min = Double.MAX_VALUE;
        for(Driver driver : availableDrivers){
            double driverLatitude = driver.getLatitude();
            double driverLongitude = driver.getLongitude();

            double distance = distanceCalculator.calculateDistance(pickupLatitude, pickupLongitude, driverLatitude, driverLongitude);
            if(min > distance) {
                min = distance;
                nearestDriver = driver;
            }
        }

        return nearestDriver;
    }

    @Override
    public Driver getRequestedDriverProfile(String jwt) throws DriverException {
        String email = jwtUtil.getEmailFromJwt(jwt);
        Driver driver = driverRepository.findByEmail(email);

        if(driver == null)
            throw new DriverException("Driver with email: " + email + " does not exists.");

        return driver;
    }

    @Override
    public Ride getDriversCurrentRide(Integer driverId) throws DriverException {
        Driver driver = findDriverById(driverId);

        return driver.getCurrentRide();
    }

    @Override
    public List<Ride> getAllocatedRides(Integer driverId) throws DriverException {
        List<Ride> allocatedRides = driverRepository.getAllocatedRides(driverId);

        return allocatedRides;
    }

    @Override
    public Driver findDriverById(Integer driverId) throws DriverException {
        Optional<Driver> optional = driverRepository.findById(driverId);
        if(optional.isPresent())
            return optional.get();

        throw new DriverException("Driver does not exists with id: " + driverId);
    }

    @Override
    public List<Ride> completedRides(Integer driverId) throws DriverException {
        List<Ride> rides = driverRepository.getCompletedRides(driverId);

        return rides;
    }
}
