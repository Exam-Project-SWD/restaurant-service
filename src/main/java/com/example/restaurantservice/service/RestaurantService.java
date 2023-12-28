package com.example.restaurantservice.service;

import com.example.restaurantservice.model.ItemsRequest;
import com.example.restaurantservice.model.LoginRequest;
import com.example.restaurantservice.model.RegisterRestaurantRequest;
import com.example.restaurantservice.model.dto.ItemDto;
import com.example.restaurantservice.model.dto.RestaurantDto;
import com.example.restaurantservice.model.entity.Item;
import com.example.restaurantservice.model.entity.Restaurant;
import com.example.restaurantservice.repository.RestaurantRepository;
import com.example.restaurantservice.util.JwtToken;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RestaurantService {
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    private final JwtToken jwtToken;
    @Autowired
    private RestaurantRepository restaurantRepository;
    @Autowired
    private KafkaService kafkaService;

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

    public Set<Item> addNewItems(ItemsRequest request) {
        Restaurant restaurant = new Restaurant();
        if (restaurantRepository.findById(request.getRestaurantId()).isPresent()) {
            restaurant = restaurantRepository.findById(request.getRestaurantId()).get();
        }
        restaurant.getMenu().addAll(request.getItems());
        kafkaService.sendRestaurantMenus();
        return restaurantRepository.save(restaurant).getMenu();
    }

    // TODO: Better conversion pattern
    public Collection<RestaurantDto> getAllRestaurants() {
        Collection<Restaurant> restaurants = restaurantRepository.findAll();
        Collection<RestaurantDto> restaurantDtos = restaurants.stream()
                .map(restaurant -> new RestaurantDto(
                        restaurant.getId(),
                        restaurant.getName(),
                        restaurant.getPhone(),
                        restaurant.getEmail(),
                        restaurant.getMenu().stream().map(item -> new ItemDto(
                                item.getId(),
                                item.getName(),
                                item.getDescription(),
                                item.getPrice()
                        )).collect(Collectors.toSet())))
                .toList();
        return restaurantDtos;
    }
}
