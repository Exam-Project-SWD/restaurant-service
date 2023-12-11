package com.example.restaurantservice.model;

import com.example.restaurantservice.model.entity.Item;

import java.util.Collection;
import java.util.HashSet;

public record MenuRequest(
        int restaurantId,
        Collection<Item> items
) {
}
