package com.example.restaurantservice.model;

public record LoginRequest(
        String email,
        String password
) {
}