package com.example.carPark.service;

import com.example.carPark.Enum.VehicleType;
import com.example.carPark.model.Floor;
import com.example.carPark.model.ParkingLot;
import com.example.carPark.model.Vehicle;
import com.example.carPark.repository.FloorRepository;
import com.example.carPark.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ParkingLotService {


    @Autowired
    private  FloorRepository floorRepository;


    @Autowired
    private  VehicleRepository vehicleRepository;

    private ParkingLot parkingLot;

    /**
     * Initializes the parking lot with the specified number of floors and spaces per floor.
     */
    public String initialize(int floors, int spacesPerFloor) {
        parkingLot = new ParkingLot();
        parkingLot.setTotalFloors(floors);

        for (int i = 1; i <= floors; i++) {
            Floor floor = new Floor();
            floor.setFloorNumber(i);
            floor.setCapacity(Map.of(
                    VehicleType.CAR, spacesPerFloor,
                    VehicleType.BIKE, spacesPerFloor / 2,
                    VehicleType.BUS, spacesPerFloor / 3
            ));
            floorRepository.save(floor);
        }

        return "Parking Lot initialized with " + floors + " floors and " + spacesPerFloor + " spaces per floor.";
    }

    /**
     * Adds a vehicle to the parking lot if there is available space.
     */
    public String addVehicle(String registrationNumber, String color, String type) {
        VehicleType vehicleType;
        try {
            vehicleType = VehicleType.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException e) {
            return "Invalid vehicle type: " + type;
        }

        // Find the first available floor for the given vehicle type
        for (Floor floor : floorRepository.findAll()) {
            int availableSpaces = floor.getCapacity().getOrDefault(vehicleType, 0);
            if (availableSpaces > 0) {
                // Reduce capacity and save vehicle
                floor.getCapacity().put(vehicleType, availableSpaces - 1);
                floorRepository.save(floor);

                Vehicle vehicle = new Vehicle();
                vehicle.setRegistrationNumber(registrationNumber);
                vehicle.setColor(color);
                vehicle.setVehicleType(vehicleType);
                vehicleRepository.save(vehicle);

                return "Vehicle added successfully: " + registrationNumber;
            }
        }
        return "No available space for " + vehicleType + " vehicles.";
    }

    /**
     * Removes a vehicle from the parking lot based on the registration number.
     */
    public String removeVehicle(String registrationNumber) {
        Optional<Vehicle> optionalVehicle = vehicleRepository.findAll().stream()
                .filter(v -> v.getRegistrationNumber().equals(registrationNumber))
                .findFirst();

        if (optionalVehicle.isPresent()) {
            Vehicle vehicle = optionalVehicle.get();
            VehicleType vehicleType = vehicle.getVehicleType();

            // Update the floor capacity
            for (Floor floor : floorRepository.findAll()) {
                if (floor.getCapacity().containsKey(vehicleType)) {
                    int availableSpaces = floor.getCapacity().get(vehicleType);
                    floor.getCapacity().put(vehicleType, availableSpaces + 1);
                    floorRepository.save(floor);
                    break;
                }
            }

            vehicleRepository.delete(vehicle);
            return "Vehicle removed successfully: " + registrationNumber;
        }
        return "Vehicle not found: " + registrationNumber;
    }

    /**
     * Displays the current status of the parking lot.
     */
    public String displayStatus() {
        StringBuilder status = new StringBuilder("Parking Lot Status:\n");

        for (Floor floor : floorRepository.findAll()) {
            status.append("Floor ").append(floor.getFloorNumber()).append(":\n");
            floor.getCapacity().forEach((vehicleType, availableSpaces) -> {
                status.append("  ").append(vehicleType).append(" - Available: ").append(availableSpaces).append("\n");
            });
        }

        return status.toString();
    }
}
