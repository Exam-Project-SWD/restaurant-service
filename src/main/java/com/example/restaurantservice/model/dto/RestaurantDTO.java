package com.example.restaurantservice.model.dto;

import java.io.Serializable;
import java.util.Set;

/**
 * DTO for {@link com.example.restaurantservice.model.entity.Restaurant}
 */
public record RestaurantDTO(int id, String name, String phone, String email,
                            Set<ItemDTO> menu) implements Serializable {
}