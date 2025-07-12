package com.restaurant_service.controller;


import com.restaurant_service.dto.RestaurantRequestDTO;
import com.restaurant_service.dto.RestaurantResponseDTO;
import com.restaurant_service.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/restaurants")
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;

    @PostMapping
    public ResponseEntity<RestaurantResponseDTO> create(@RequestBody RestaurantRequestDTO dto) {
        return ResponseEntity.ok(restaurantService.createRestaurant(dto));
    }

    @GetMapping
    public ResponseEntity<List<RestaurantResponseDTO>> getAll() {
        return ResponseEntity.ok(restaurantService.getAllRestaurants());
    }

    @PostMapping("/foods/prices")
    public ResponseEntity<Map<Long, Double>> getFoodPrices(@RequestBody List<Long> ids) {
        return ResponseEntity.ok(restaurantService.getFoodPrices(ids));
    }
}
