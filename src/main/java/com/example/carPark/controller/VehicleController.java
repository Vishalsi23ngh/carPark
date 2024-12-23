package com.example.carPark.controller;

import com.example.carPark.model.Vehicle;
import com.example.carPark.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vehicle")
@RequiredArgsConstructor
public class VehicleController {

    @Autowired
    private   VehicleService vehicleService;

    @PostMapping("/add")
    public String addVehicle(@RequestParam String registrationNumber, @RequestParam String color, @RequestParam String type) {
        return vehicleService.addVehicle(registrationNumber, color, type);
    }

    @DeleteMapping("/remove")
    public String removeVehicle(@RequestParam String registrationNumber) {
        return vehicleService.removeVehicle(registrationNumber);
    }

    @GetMapping("/all")
    public List<Vehicle> getAllVehicles() {
        return vehicleService.getAllVehicles();
    }

    @GetMapping("/find")
    public Vehicle findVehicle(@RequestParam String registrationNumber) {
        return vehicleService.findVehicleByRegistration(registrationNumber)
                .orElseThrow(() -> new RuntimeException("Vehicle not found: " + registrationNumber));
    }
}
