package com.example.carPark.model;

import com.example.carPark.Enum.VehicleType;
import jakarta.persistence.*;
import lombok.*;

import java.util.Map;

@Entity
@Getter
@Setter
public class Floor {
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long id;

     private int floorNumber;

     @ElementCollection
     private Map<VehicleType, Integer> capacity;

     public int getFloorNumber() {
          return floorNumber;
     }

     public void setFloorNumber(int floorNumber) {
          this.floorNumber = floorNumber;
     }

     public Map<VehicleType, Integer> getCapacity() {
          return capacity;
     }

     public void setCapacity(Map<VehicleType, Integer> capacity) {
          this.capacity = capacity;
     }
}

