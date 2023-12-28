package com.example.restaurantservice.controller;

import com.example.restaurantservice.model.ItemsRequest;
import com.example.restaurantservice.model.LoginRequest;
import com.example.restaurantservice.model.RegisterRestaurantRequest;
import com.example.restaurantservice.model.dto.RestaurantDto;
import com.example.restaurantservice.model.entity.Item;
import com.example.restaurantservice.model.entity.Restaurant;
import com.example.restaurantservice.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Set;

@RequiredArgsConstructor
@RestController
@RequestMapping("/restaurant")
public class RestaurantController {
    private static final Logger LOGGER = LoggerFactory.getLogger(Authentication.class);

    @Autowired
    private RestaurantService restaurantService;

    @PostMapping("/register")
    public Restaurant registerRestaurant(@RequestBody RegisterRestaurantRequest request) {
        return restaurantService.registerNewRestaurant(request);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) throws Exception {
        LOGGER.debug("Request was made with email: " + request.email());
        String token = restaurantService.generateToken(request);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/add-items")
    public ResponseEntity<Set<Item>> addNewItems(@RequestBody ItemsRequest request) {
        return ResponseEntity.ok(restaurantService.addNewItems(request));
    }

    @GetMapping("/menu")
    public ResponseEntity<Collection<Item>> getMenu(@RequestParam("id") int id) {
        return ResponseEntity.ok(restaurantService.getMenu(id));
    }

    @GetMapping("/restaurants")
    public ResponseEntity<Collection<RestaurantDto>> getMenus() {
        return ResponseEntity.ok(restaurantService.getAllRestaurants());
    }
}