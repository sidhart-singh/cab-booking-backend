package com.sidhartsingh.olabackend.modal;

public enum RideStatus {
//    User requests for a ride
    REQUESTED,
//    Driver accepts the request
    ACCEPTED,
//    Driver goes to the user and confirms a OTP then ride STARTS
    STARTED,
//    User reaches destination
    COMPLETED,
//    Driver cancels the request
    CANCELLED
}
