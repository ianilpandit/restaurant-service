package com.restaurant_service.repo;


import com.restaurant_service.entity.FoodItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FoodItemRepository extends JpaRepository<FoodItem, UUID> {}
