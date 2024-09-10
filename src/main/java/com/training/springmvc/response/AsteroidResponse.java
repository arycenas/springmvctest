package com.training.springmvc.response;

import lombok.Data;

@Data
public class AsteroidResponse {

    private String name;
    private double diameter;
    private double distance;
    private double velocity;

    public AsteroidResponse() {
    }

    public AsteroidResponse(String name, double diameter, double distance, double velocity) {
        this.name = name;
        this.diameter = diameter;
        this.distance = distance;
        this.velocity = velocity;
    }
}
