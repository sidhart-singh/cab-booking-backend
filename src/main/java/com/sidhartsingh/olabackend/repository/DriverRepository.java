package com.sidhartsingh.olabackend.repository;

import com.sidhartsingh.olabackend.modal.Driver;
import com.sidhartsingh.olabackend.modal.Ride;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DriverRepository extends JpaRepository<Driver, Integer> {
    Driver findByEmail(String email);
    @Query("SELECT R FROM Ride R WHERE R.status=com.sidhartsingh.olabackend.modal.RideStatus.REQUESTED AND R.driver.id=:driverId")
    List<Ride> getAllocatedRides(@Param("driverId") Integer driverId);
    @Query("SELECT R FROM Ride R WHERE R.status=com.sidhartsingh.olabackend.modal.RideStatus.COMPLETED AND R.driver.id=:driverId")
    List<Ride> getCompletedRides(@Param("driverId") Integer driverId);
}
