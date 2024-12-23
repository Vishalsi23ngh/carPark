package com.example.carPark.service;

import com.example.carPark.Enum.VehicleType;
import com.example.carPark.model.Vehicle;
import com.example.carPark.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VehicleService {


    @Autowired
    private  VehicleRepository vehicleRepository;

    public String addVehicle(String registrationNumber, String color, String type) {
        try {
            VehicleType vehicleType = VehicleType.valueOf(type.toUpperCase());

            Vehicle vehicle = new Vehicle();
            vehicle.setRegistrationNumber(registrationNumber);
            vehicle.setColor(color);
            vehicle.setVehicleType(vehicleType);

            vehicleRepository.save(vehicle);
            return "Vehicle added successfully with registration number: " + registrationNumber;
        } catch (IllegalArgumentException e) {
            return "Invalid vehicle type: " + type;
        }
    }


    public String removeVehicle(String registrationNumber) {
        Optional<Vehicle> vehicle = vehicleRepository.findAll().stream()
                .filter(v -> v.getRegistrationNumber().equals(registrationNumber))
                .findFirst();

        if (vehicle.isPresent()) {
            vehicleRepository.delete(vehicle.get());
            return "Vehicle with registration number " + registrationNumber + " removed successfully.";
        }
        return "Vehicle with registration number " + registrationNumber + " not found.";
    }


    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    /**
     * Finds a vehicle by its registration number.
     *
     */
    public Optional<Vehicle> findVehicleByRegistration(String registrationNumber) {
        return vehicleRepository.findAll().stream()
                .filter(v -> v.getRegistrationNumber().equals(registrationNumber))
                .findFirst();
    }

    /**
     * Fetches vehicles by type.
     *
     */
    public List<Vehicle> getVehiclesByType(String type) {
        try {
            VehicleType vehicleType = VehicleType.valueOf(type.toUpperCase());
            return vehicleRepository.findAll().stream()
                    .filter(v -> v.getVehicleType() == vehicleType)
                    .toList();
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid vehicle type: " + type);
        }
    }
}
