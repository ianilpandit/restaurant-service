package com.restaurant_service.service.impl;

import com.restaurant_service.dto.FoodItemDTO;
import com.restaurant_service.dto.RestaurantRequestDTO;
import com.restaurant_service.dto.RestaurantResponseDTO;
import com.restaurant_service.entity.FoodItem;
import com.restaurant_service.entity.Restaurant;
import com.restaurant_service.repo.FoodItemRepository;
import com.restaurant_service.repo.RestaurantRepository;
import com.restaurant_service.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
                .name(dto.getName())
                .address(dto.getAddress())
                .build();

        Restaurant savedRestaurant = restaurantRepository.save(restaurant); // save to get generated ID

        // Step 2: Map FoodItemDTOs → FoodItem entities and set the restaurant
        List<FoodItem> foodItems = new ArrayList<>();
        if (dto.getMenu() != null) {
            foodItems = dto.getMenu().stream()
                    .map(itemDto -> FoodItem.builder()
                            .name(itemDto.getName())
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


    private RestaurantResponseDTO toResponseDTO(Restaurant restaurant) {
        List<FoodItemDTO> menu = restaurant.getMenu().stream()
                .map(item -> FoodItemDTO.builder()
                        .name(item.getName())
                        .description(item.getDescription())
                        .price(item.getPrice())
                        .build())
                .collect(Collectors.toList());

        return RestaurantResponseDTO.builder()
                .id(restaurant.getId())
                .name(restaurant.getName())
                .address(restaurant.getAddress())
                .menu(menu)
                .build();
    }


    @Override
    public List<RestaurantResponseDTO> getAllRestaurants() {
        return restaurantRepository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }


    @Override
    public Map<Long, Double> getFoodPrices(List<Long> foodItemIds) {
        return foodItemRepository.findAllById(foodItemIds).stream()
                .collect(Collectors.toMap(FoodItem::getId, FoodItem::getPrice));
    }
}
