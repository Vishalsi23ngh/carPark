package com.example.carPark.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
public class ParkingLot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    int totalFloors;

    @OneToMany(cascade = CascadeType.ALL)
    List<Floor> floors;

    /*  using the default gettter and setter because the annotation is not working properly in every class*/

    public int getTotalFloors() {
        return totalFloors;
    }

    public void setTotalFloors(int totalFloors) {
        this.totalFloors = totalFloors;
    }
}
