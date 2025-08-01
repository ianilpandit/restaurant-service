package com.restaurant_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FoodItemResponseDTO {
    private UUID foodItemId;
    private String foodName;
    private String description;
    private double price;
    private UUID restaurantId;
}
