package com.example.restaurantservice.repository;

import com.example.restaurantservice.model.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {
    Optional<Restaurant> findById(int id);
    Restaurant findByEmail(String email);
}