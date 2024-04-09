package com.sidhartsingh.olabackend.repository;

import com.sidhartsingh.olabackend.modal.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {
}
