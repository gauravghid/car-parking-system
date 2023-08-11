package com.org.carparkingsystem.model;

import java.time.LocalDateTime;

public class Car {

    String carNumber;

    @Override
    public String toString() {
        return "Car{" +
                "carNumber='" + carNumber + '\'' +
                '}';
    }

    public Car(String carNumber) {
        this.carNumber = carNumber;
    }

    public String getCarNumber() {
        return carNumber;
    }

}
