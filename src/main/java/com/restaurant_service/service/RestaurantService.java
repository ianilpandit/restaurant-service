package com.restaurant_service.service;

import com.restaurant_service.dto.RestaurantRequestDTO;
import com.restaurant_service.dto.RestaurantResponseDTO;

import java.util.List;
import java.util.Map;

public interface RestaurantService {
    RestaurantResponseDTO createRestaurant(RestaurantRequestDTO dto);
    List<RestaurantResponseDTO> getAllRestaurants();
    Map<Long, Double> getFoodPrices(List<Long> foodItemIds);
}
