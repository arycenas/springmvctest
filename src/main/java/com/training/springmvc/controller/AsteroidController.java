package com.training.springmvc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.training.springmvc.model.Asteroids;
import com.training.springmvc.service.AsteroidService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@Api(value = "Asteroids Controller", description = "Operations to manage asteroids data")
public class AsteroidController {

    @Autowired
    private AsteroidService asteroidService;

    @ApiOperation(value = "Fetch and sort asteroids by start date and end date")
    @GetMapping("/asteroids")
    public List<Asteroids> getAsteroids(
            @ApiParam(value = "Start date for fetching asteroids", required = true) @RequestParam String startDate,
            @ApiParam(value = "End date for fetching asteroids", required = true) @RequestParam String endDate,
            @ApiParam(value = "Sort criteria", defaultValue = "name") @RequestParam(required = false, defaultValue = "name") String sortBy,
            @ApiParam(value = "Sort direction (asc or desc)", defaultValue = "asc") @RequestParam(required = false, defaultValue = "asc") String sortDirection) {
        return asteroidService.fetchAsteroidApi(startDate, endDate, sortBy, sortDirection);
    }
}
