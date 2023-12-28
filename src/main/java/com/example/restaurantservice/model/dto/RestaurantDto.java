package com.example.restaurantservice.model.dto;

import java.io.Serializable;
import java.util.Set;

/**
 * DTO for {@link com.example.restaurantservice.model.entity.Restaurant}
 */
public record RestaurantDto(int id, String name, String phone, String email,
                            Set<ItemDto> menu) implements Serializable {
}