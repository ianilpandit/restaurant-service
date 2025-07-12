package com.restaurant_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RestaurantRequestDTO {
    private String name;
    private String address;
    private List<FoodItemDTO> menu;
}
