package com.example.restaurantservice.model;

import com.example.restaurantservice.model.entity.Item;
import lombok.*;

import java.util.Collection;
import java.util.HashSet;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ItemsRequest {
        private int restaurantId;
        private Collection<Item> items = new HashSet<>();
}