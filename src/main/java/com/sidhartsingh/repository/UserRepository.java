package com.sidhartsingh.repository;

import com.sidhartsingh.modal.Ride;
import com.sidhartsingh.modal.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);
    @Query("SELECT R FROM Ride R WHERE R.status=com.sidhartsingh.modal.RideStatus.COMPLETED AND R.user.id=:userId")
    List<Ride> getCompletedRides(@Param("userId") Integer userId);
}
