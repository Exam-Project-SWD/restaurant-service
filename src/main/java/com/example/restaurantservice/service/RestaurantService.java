package com.example.restaurantservice.service;

import com.example.restaurantservice.model.LoginRequest;
import com.example.restaurantservice.model.MenuRequest;
import com.example.restaurantservice.model.RegisterRestaurantRequest;
import com.example.restaurantservice.model.entity.Item;
import com.example.restaurantservice.model.entity.Restaurant;
import com.example.restaurantservice.repository.RestaurantRepository;
import com.example.restaurantservice.util.JwtToken;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class RestaurantService {
    @Autowired
    private RestaurantRepository restaurantRepository;
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    private final JwtToken jwtToken;

    public Restaurant registerNewRestaurant(RegisterRestaurantRequest request) {
        Restaurant restaurant = Restaurant.builder()
                .name(request.name())
                .phone(request.phone())
                .email(request.email())
                .password(encoder.encode(request.password()))
                .build();
        restaurantRepository.save(restaurant);
        return restaurant;
    }

    public String generateToken(LoginRequest request) throws Exception {
        Restaurant restaurant = restaurantRepository.findByEmail(request.email());

        if (restaurant != null && authenticate(request.password(), restaurant.getPassword())) {
            //return generated token
            return jwtToken.generateToken(request.email());
        } else {
            throw new Exception("email does not exist in the database");
        }
    }

    public boolean authenticate(String inputPassword, String storedPassword) {
        return encoder.matches(inputPassword, storedPassword);
    }

    public Collection<Item> getMenu(int id) {
        return restaurantRepository.findById(id).get().getMenu();
    }

//    public Collection<Item> addMenu(MenuRequest request) {
//        Restaurant restaurant = restaurantRepository.findById(request.restaurantId());
//        Set<Item> items = new HashSet<>(request.items());
//        restaurant.setMenu(items);
//        return restaurantRepository.save(restaurant).getMenu();
//    }
}
