package com.sidhartsingh.olabackend.controller;

import com.sidhartsingh.olabackend.dto.RideDTO;
import com.sidhartsingh.olabackend.dto.mapper.DtoMapper;
import com.sidhartsingh.olabackend.exception.DriverException;
import com.sidhartsingh.olabackend.exception.RideException;
import com.sidhartsingh.olabackend.exception.UserException;
import com.sidhartsingh.olabackend.modal.Driver;
import com.sidhartsingh.olabackend.modal.Ride;
import com.sidhartsingh.olabackend.modal.User;
import com.sidhartsingh.olabackend.request.RideRequest;
import com.sidhartsingh.olabackend.request.StartRideRequest;
import com.sidhartsingh.olabackend.response.MessageResponse;
import com.sidhartsingh.olabackend.service.DriverService;
import com.sidhartsingh.olabackend.service.RideService;
import com.sidhartsingh.olabackend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rides")
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

    @PutMapping("/{rideId}/complete")
    public ResponseEntity<MessageResponse> rideCompleteHandler(@PathVariable Integer rideId) throws RideException {
        rideService.completeRide(rideId);

        MessageResponse response = new MessageResponse("Ride is completed. Thank you for riding with OLA.");

        return new ResponseEntity<>(response,  HttpStatus.ACCEPTED);
    }

    @GetMapping("/{rideId}")
    public ResponseEntity<RideDTO> findRideByIdHandler(@PathVariable Integer rideId, @RequestHeader("Authorization") String jwt) throws UserException, RideException {
        User user = userService.getReqUserProfile(jwt);
        Ride ride = rideService.findRideById(rideId);

        RideDTO rideDTO = DtoMapper.toRideDTO(ride);

        return new ResponseEntity<>(rideDTO, HttpStatus.ACCEPTED);
    }
}
