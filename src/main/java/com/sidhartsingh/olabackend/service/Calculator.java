package com.sidhartsingh.olabackend.service;

import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class Calculator {
    private static final int EARTH_RADIUS = 6371;
    public double calculateDistance(double sourceLatitude, double sourceLongitude, double destinationLatitude, double destinationLongitude){
        double distanceLatitude = Math.toRadians(destinationLatitude - sourceLatitude);
        double distanceLongitude = Math.toRadians(destinationLongitude - sourceLongitude);

        double a = Math.sin(distanceLatitude / 2) * Math.sin(distanceLatitude / 2)
                + Math.cos(Math.toRadians(sourceLatitude)) * Math.cos(Math.toRadians(destinationLatitude))
                + Math.sin(distanceLongitude / 2) * Math.sin(distanceLongitude / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        double distance = EARTH_RADIUS * c;

        return distance;
    }

    public long calculateDuration(LocalDateTime startTime, LocalDateTime endTime) {
        Duration duration = Duration.between(startTime, endTime);

        return duration.getSeconds();
    }

    public double calculateFare(double distance) {
        double baseFare = 11;
        double totalFare = baseFare * distance;

        return totalFare;
    }
}
