package com.example.restaurantservice.controller;

import com.example.restaurantservice.model.LoginRequest;
import com.example.restaurantservice.model.MenuRequest;
import com.example.restaurantservice.model.RegisterRestaurantRequest;
import com.example.restaurantservice.model.entity.Item;
import com.example.restaurantservice.model.entity.Restaurant;
import com.example.restaurantservice.service.KafkaService;
import com.example.restaurantservice.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@RestController
@RequestMapping("/restaurant")
public class RestaurantController {
    private static final Logger LOGGER = LoggerFactory.getLogger(Authentication.class);

    @Autowired
    private RestaurantService restaurantService;
    @Autowired
    private KafkaService kafkaService;

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

//    @PostMapping("menu")
//    public Collection<Item> addMenu(@RequestBody MenuRequest request) {
//        return restaurantService.addMenu(request);
//    }

    @GetMapping("/menu")
    public ResponseEntity<Collection<Item>> getMenu(@RequestParam("id") int id) {
        return ResponseEntity.ok(restaurantService.getMenu(id));
    }

    @GetMapping("/publish")
    public ResponseEntity<String> publishMenu() {
        return ResponseEntity.ok(kafkaService.sendRestaurantMenus());
    }
}