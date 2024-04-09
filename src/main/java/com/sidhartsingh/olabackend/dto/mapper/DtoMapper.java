package com.sidhartsingh.olabackend.dto.mapper;

import com.sidhartsingh.olabackend.dto.DriverDTO;
import com.sidhartsingh.olabackend.dto.RideDTO;
import com.sidhartsingh.olabackend.dto.UserDTO;
import com.sidhartsingh.olabackend.modal.Driver;
import com.sidhartsingh.olabackend.modal.Ride;
import com.sidhartsingh.olabackend.modal.User;

public class DtoMapper {
    public static DriverDTO toDriverDTO(Driver driver){
        DriverDTO driverDTO = new DriverDTO();
        driverDTO.setEmail(driver.getEmail());
        driverDTO.setId(driver.getId());
        driverDTO.setLatitude(driver.getLatitude());
        driverDTO.setLongitude(driver.getLongitude());
        driverDTO.setMobile(driver.getMobile());
        driverDTO.setName(driver.getFullName());
        driverDTO.setRating(driver.getRating());
        driverDTO.setRole(driver.getRole());
        driverDTO.setVehicle(driver.getVehicle());

        return driverDTO;
    }

    public static UserDTO toUserDTO(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(user.getEmail());
        userDTO.setId(user.getId());
        userDTO.setMobile(user.getMobile());
        userDTO.setName(user.getFullName());

        return userDTO;
    }

    public static RideDTO toRideDTO(Ride ride){
        DriverDTO driverDTO = new DriverDTO();
        UserDTO userDTO = new UserDTO();
        RideDTO rideDTO = new RideDTO();

        rideDTO.setDestinationLatitude(ride.getDestinationLatitude());
        rideDTO.setDestinationLongitude(ride.getDestinationLongitude());
        rideDTO.setDistance(ride.getDistance());
        rideDTO.setDriver(driverDTO);
        rideDTO.setDuration(ride.getDuration());
        rideDTO.setEndTime(ride.getStartTime());
        rideDTO.setStartTime(ride.getStartTime());
        rideDTO.setPickupLatitude(ride.getPickupLatitude());
        rideDTO.setPickupLongitude(ride.getPickupLongitude());
        rideDTO.setStatus(ride.getStatus());
        rideDTO.setUser(userDTO);
        rideDTO.setPickupArea(ride.getPickupArea());
        rideDTO.setDestinationArea(ride.getDestinationArea());
        rideDTO.setPaymentDetails(ride.getPaymentDetails());
        rideDTO.setOTP(ride.getOTP());

        return rideDTO;
    }
}
