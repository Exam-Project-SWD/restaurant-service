package com.example.restaurantservice.model.dto;

import java.io.Serializable;

/**
 * DTO for {@link com.example.restaurantservice.model.entity.Item}
 */
public record ItemDto(int id, String name, String description, double price) implements Serializable {
}