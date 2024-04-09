package com.sidhartsingh.controller;

import com.sidhartsingh.dto.RideDTO;
import com.sidhartsingh.dto.mapper.DtoMapper;
import com.sidhartsingh.exception.DriverException;
import com.sidhartsingh.exception.RideException;
import com.sidhartsingh.exception.UserException;
import com.sidhartsingh.modal.Driver;
import com.sidhartsingh.modal.Ride;
import com.sidhartsingh.modal.User;
import com.sidhartsingh.request.RideRequest;
import com.sidhartsingh.request.StartRideRequest;
import com.sidhartsingh.response.MessageResponse;
import com.sidhartsingh.service.DriverService;
import com.sidhartsingh.service.RideService;
import com.sidhartsingh.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rides/")
public class RideController {
    private final RideService rideService;
    private final DriverService driverService;
    private final UserService userService;

    public RideController(RideService rideService, DriverService driverService, UserService userService) {
        this.rideService = rideService;
        this.driverService = driverService;
        this.userService = userService;
    }

    @PostMapping("/request")
    public ResponseEntity<RideDTO> userRequestRideHandler(@RequestBody RideRequest rideRequest, @RequestHeader("Authorization") String jwt) throws UserException, DriverException {
        User user = userService.getReqUserProfile(jwt);
        Ride ride = rideService.requestRide(rideRequest, user);

        RideDTO rideDTO = DtoMapper.toRideDTO(ride);

        return new ResponseEntity<>(rideDTO, HttpStatus.ACCEPTED);
    }

    @PutMapping("/{rideId}/accept")
    public ResponseEntity<MessageResponse> acceptRideHandler(@PathVariable Integer rideId) throws RideException {
        rideService.acceptRide(rideId);
        MessageResponse response = new MessageResponse("Ride is accepted by driver.");

        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @PutMapping("/{rideId}/decline")
    public ResponseEntity<MessageResponse> declineRideHandler(@RequestHeader("Authorization") String jwt, @PathVariable Integer rideId) throws DriverException, RideException {
        Driver driver = driverService.getRequestedDriverProfile(jwt);
        rideService.declineRide(rideId, driver.getId());

        MessageResponse response = new MessageResponse("Ride declined by driver.");

        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @PutMapping("/{rideId}/start")
    public ResponseEntity<MessageResponse> rideStartHandler(@PathVariable Integer rideId, @RequestBody StartRideRequest request) throws RideException {
        rideService.startRide(rideId, request.getOTP());
        MessageResponse response = new MessageResponse("Ride is started.");

        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @GetMapping("/{rideId}")
    public ResponseEntity<RideDTO> findRideByIdHandler(@PathVariable Integer rideId, @RequestHeader("Authorization") String jwt) throws UserException, RideException {
        User user = userService.getReqUserProfile(jwt);
        Ride ride = rideService.findRideById(rideId);

        RideDTO rideDTO = DtoMapper.toRideDTO(ride);

        return new ResponseEntity<>(rideDTO, HttpStatus.ACCEPTED);
    }
}
