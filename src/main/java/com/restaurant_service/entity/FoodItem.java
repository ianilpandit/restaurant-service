package com.restaurant_service.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FoodItem {
    @Id
    @Column(name = "food_item_id", nullable = false, updatable = false)
    private UUID foodItemId;
    private String foodName;
    private String description;
    private double price;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    @JsonBackReference
    private Restaurant restaurant;

    @PrePersist
    public void prePersist() {
        if (foodItemId == null) {
            foodItemId = UUID.randomUUID();
        }
    }

}
