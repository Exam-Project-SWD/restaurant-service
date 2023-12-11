package com.example.restaurantservice.model;

public record RegisterRestaurantRequest(
        String name,
        String phone,
        String email,
        String password
) {
}