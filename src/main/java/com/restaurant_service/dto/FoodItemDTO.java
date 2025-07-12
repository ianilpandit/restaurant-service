package com.restaurant_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FoodItemDTO {
    private Long id;
    private String name;
    private String description;
    private double price;
    private Long restaurantId;
}
