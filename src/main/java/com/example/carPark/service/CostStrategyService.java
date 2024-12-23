package com.example.carPark.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CostStrategyService {

    public double calculateCost(String vehicleType, int hours) {
        double costPerHour = 0;

        // Set cost based on vehicle type
        switch (vehicleType.toLowerCase()) {
            case "car":
                costPerHour = 20.0; // example rate for car
                break;
            case "bike":
                costPerHour = 10.0; // example rate for bike
                break;
            case "scooter":
                costPerHour = 5.0; // example rate for scooter
                break;
            case "truck":
                costPerHour = 50.0; // example rate for truck
                break;
            default:
                System.out.println("Invalid vehicle type");
                return 0; // Invalid vehicle type
        }

        // Calculate total cost
        return costPerHour * hours;
    }

}
