package com.sidhartsingh.repository;

import com.sidhartsingh.modal.Ride;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RideRepository extends JpaRepository<Ride, Integer> {
}
