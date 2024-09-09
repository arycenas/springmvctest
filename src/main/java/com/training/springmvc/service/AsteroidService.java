package com.training.springmvc.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.training.springmvc.model.Asteroids;

@Service
public class AsteroidService {

    private static final String API_KEY = "9vaeEgNEFNmo4QMaYhdLlsSWeHkw3thvGhIiZhkp";
    private static final String URL_TEMPLATE = "https://api.nasa.gov/neo/rest/v1/feed?start_date={start_date}&end_date={end_date}&api_key={api_key}";

    public List<Asteroids> fetchAsteroidApi(String startDate, String endDate, String sortBy, String sortDirection) {
        RestTemplate restTemplate = new RestTemplate();

        String url = URL_TEMPLATE.replace("{start_date}", startDate).replace("{end_date}", endDate).replace("{api_key}",
                API_KEY);

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        List<Asteroids> asteroidList = parseAsteroidData(response.getBody());

        sortAsteroid(asteroidList, sortBy, sortDirection);

        return asteroidList;
    }

    private List<Asteroids> parseAsteroidData(String jsonData) {
        List<Asteroids> asteroidList = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            JsonNode root = objectMapper.readTree(jsonData);
            JsonNode nearEarthObjects = root.path("near_earth_objects");

            for (JsonNode dateNode : nearEarthObjects) {
                for (JsonNode asteroidNode : dateNode) {
                    Asteroids asteroid = new Asteroids();
                    asteroid.setName(asteroidNode.path("name").asText());
                    asteroid.setDiameter(asteroidNode.path("estimated_diameter").path("kilometers")
                            .path("estimated_diameter_max").asDouble());
                    asteroid.setDistance(asteroidNode.path("close_approach_data").get(0).path("miss_distance")
                            .path("kilometers").asDouble());
                    asteroid.setVelocity(asteroidNode.path("close_approach_data").get(0).path("relative_velocity")
                            .path("kilometers_per_hour").asDouble());
                    asteroidList.add(asteroid);
                }
            }
        } catch (JsonProcessingException e) {
        }

        return asteroidList;
    }

    private void sortAsteroid(List<Asteroids> asteroidList, String sortBy, String sortDirection) {
        Comparator<Asteroids> comparator;

        switch (sortBy) {
            case "diameter":
                comparator = Comparator.comparing(Asteroids::getDiameter);
                break;
            case "distance":
                comparator = Comparator.comparing(Asteroids::getDiameter);
                break;
            case "velocity":
                comparator = Comparator.comparing(Asteroids::getVelocity);
                break;
            case "name":
            default:
                comparator = Comparator.comparing(Asteroids::getName);
                break;
        }

        if (sortDirection.equalsIgnoreCase("desc")) {
            comparator = comparator.reversed();
        }

        asteroidList.sort(comparator);
    }
}
