package com.restaurant_service.service.impl;

import com.restaurant_service.dto.FoodItemRequestDTO;
import com.restaurant_service.dto.FoodItemResponseDTO;
import com.restaurant_service.dto.RestaurantRequestDTO;
import com.restaurant_service.dto.RestaurantResponseDTO;
import com.restaurant_service.entity.FoodItem;
import com.restaurant_service.entity.Restaurant;
import com.restaurant_service.repo.FoodItemRepository;
import com.restaurant_service.repo.RestaurantRepository;
import com.restaurant_service.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final FoodItemRepository foodItemRepository;

    @Override
    public RestaurantResponseDTO createRestaurant(RestaurantRequestDTO dto) {
        // Step 1: Create and save the Restaurant first
        Restaurant restaurant = Restaurant.builder()
                .restaurantName(dto.getName())
                .address(dto.getAddress())
                .build();

        Restaurant savedRestaurant = restaurantRepository.save(restaurant); // save to get generated ID

        // Step 2: Map FoodItemDTOs → FoodItem entities and set the restaurant
        List<FoodItem> foodItems = new ArrayList<>();
        if (dto.getMenu() != null) {
            foodItems = dto.getMenu().stream()
                    .map(itemDto -> FoodItem.builder()
                            .foodName(itemDto.getName())
                            .description(itemDto.getDescription())
                            .price(itemDto.getPrice())
                            .restaurant(savedRestaurant) // assign saved restaurant
                            .build())
                    .collect(Collectors.toList());

            // Save all food items
            foodItemRepository.saveAll(foodItems);
        }

        // Step 3: Set menu in restaurant object (optional for return mapping)
        savedRestaurant.setMenu(foodItems);

        return toResponseDTO(savedRestaurant);
    }


    public RestaurantResponseDTO toResponseDTO(Restaurant restaurant) {
        return RestaurantResponseDTO.builder()
                .restaurantId(restaurant.getRestaurantID())
                .restaurantName(restaurant.getRestaurantName())
                .address(restaurant.getAddress())
                .menu(
                        restaurant.getMenu() != null ?
                                restaurant.getMenu().stream()
                                        .map(foodItem -> FoodItemResponseDTO.builder()
                                                .foodItemIid(foodItem.getFoodItemId())
                                                .foodName(foodItem.getFoodName())
                                                .description(foodItem.getDescription())
                                                .price(foodItem.getPrice())
                                                .restaurantId(
                                                        foodItem.getRestaurant() != null
                                                                ? foodItem.getRestaurant().getRestaurantID()
                                                                : null
                                                )
                                                .build())
                                        .collect(Collectors.toList())
                                : Collections.emptyList()
                )
                .build();
    }




    @Override
    public List<RestaurantResponseDTO> getAllRestaurants() {
        return restaurantRepository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }


    @Override
    public Map<UUID, Double> getFoodPrices(List<UUID> foodItemIds) {
        return foodItemRepository.findAllById(foodItemIds).stream()
                .collect(Collectors.toMap(FoodItem::getFoodItemId, FoodItem::getPrice));
    }
}
