package com.sidhartsingh.controller;

import com.sidhartsingh.exception.DriverException;
import com.sidhartsingh.modal.Driver;
import com.sidhartsingh.modal.Ride;
import com.sidhartsingh.service.DriverService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/drivers")
public class DriverController {
    private DriverService driverService;

    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }

    @GetMapping("/profile")
    public ResponseEntity<Driver> getRequestedDriverProfileHandler(@RequestHeader("Authorization")String jwt) throws DriverException {
        Driver driver = driverService.getRequestedDriverProfile(jwt);

        return new ResponseEntity<>(driver, HttpStatus.OK);
    }

    @GetMapping("/{driverId}/current_ride")
    public ResponseEntity<Ride> getDriversCurrentRideHandler(@PathVariable Integer driverId) throws DriverException {
        Ride ride = driverService.getDriversCurrentRide(driverId);

        return new ResponseEntity<>(ride, HttpStatus.ACCEPTED);
    }

    @GetMapping("/{driverId}/allocated")
    public ResponseEntity<List<Ride>> getAllocatedRidesHandler(@PathVariable Integer driverId) throws DriverException {
        List<Ride> allocatedRides = driverService.getAllocatedRides(driverId);

        return new ResponseEntity<>(allocatedRides, HttpStatus.ACCEPTED);
    }

    @GetMapping("/rides/completed")
    public ResponseEntity<List<Ride>> getCompletedRidesHandler(@RequestHeader("Authorization")String jwt) throws DriverException {
        Driver driver = driverService.getRequestedDriverProfile(jwt);
        List<Ride> rides = driverService.completedRides(driver.getId());

        return new ResponseEntity<>(rides, HttpStatus.ACCEPTED);
    }

}
