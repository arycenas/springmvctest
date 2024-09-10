package com.training.springmvc.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.training.springmvc.model.Asteroids;
import com.training.springmvc.request.AsteroidRequest;
import com.training.springmvc.response.AsteroidResponse;
import com.training.springmvc.service.AsteroidService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(value = "v1", produces = "application/json")
@Tag(name = "Asteroids Controller", description = "Operations to manage asteroids data")
public class AsteroidController {

    @Autowired
    private AsteroidService asteroidService;

    @Operation(summary = "Fetch and sort asteroids by start date and end date")
    @GetMapping("/asteroids")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Asteroids Fetched Successfully", content = @Content(schema = @Schema(implementation = List.class)))
    })
    public ResponseEntity<List<AsteroidResponse>> getAsteroids(@RequestBody AsteroidRequest asteroidRequest) {
        List<Asteroids> asteroids = asteroidService.fetchAsteroidApi(asteroidRequest.getStartDate(),
                asteroidRequest.getEndDate(), asteroidRequest.getSortBy(), asteroidRequest.getSortDirection());

        List<AsteroidResponse> asteroidResponse = asteroids.stream()
                .map(asteroid -> new AsteroidResponse(asteroid.getName(), asteroid.getDiameter(),
                        asteroid.getDistance(), asteroid.getVelocity()))
                .collect(Collectors.toList());

        return new ResponseEntity<>(asteroidResponse, HttpStatus.OK);
    }
}
