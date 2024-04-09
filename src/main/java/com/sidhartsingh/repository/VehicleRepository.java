package com.sidhartsingh.repository;

import com.sidhartsingh.modal.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {
}
