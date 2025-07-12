package com.restaurant_service.service;

import com.restaurant_service.dto.RestaurantRequestDTO;
import com.restaurant_service.dto.RestaurantResponseDTO;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface RestaurantService {
    RestaurantResponseDTO createRestaurant(RestaurantRequestDTO dto);
    List<RestaurantResponseDTO> getAllRestaurants();
    Map<UUID, Double> getFoodPrices(List<UUID> foodItemIds);
}
