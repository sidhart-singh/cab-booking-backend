package com.sidhartsingh.olabackend.repository;

import com.sidhartsingh.olabackend.modal.Ride;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RideRepository extends JpaRepository<Ride, Integer> {
}
