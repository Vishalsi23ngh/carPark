package com.example.carPark.controller;

import com.example.carPark.service.ParkingLotService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class ParkingLotController {

    @Autowired
    private  ParkingLotService parkingLotService;


    @PostMapping("/initialize")
    public String initialize(@RequestParam int floors, @RequestParam int spacesPerFloor) {
        return parkingLotService.initialize(floors, spacesPerFloor);
    }

    @PostMapping("/addVehicle")
    public String addVehicle(@RequestParam String registrationNumber,
                             @RequestParam String color,
                             @RequestParam String type) {
        return parkingLotService.addVehicle(registrationNumber, color, type);
    }

    @DeleteMapping("/removeVehicle")
    public String removeVehicle(@RequestParam String registrationNumber) {
        return parkingLotService.removeVehicle(registrationNumber);
    }

    @GetMapping("/status")
    public String displayStatus() {
        return parkingLotService.displayStatus();
    }
}
