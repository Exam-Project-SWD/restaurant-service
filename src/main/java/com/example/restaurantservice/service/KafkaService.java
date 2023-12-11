package com.example.restaurantservice.service;

import com.example.restaurantservice.model.entity.Item;
import com.example.restaurantservice.model.entity.Restaurant;
import com.example.restaurantservice.model.enums.Topic;
import com.example.restaurantservice.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Service
public class KafkaService {
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final RestaurantRepository restaurantRepository;

    @Autowired
    public KafkaService(KafkaTemplate<String, Object> kafkaTemplate, RestaurantRepository restaurantRepository) {
        Objects.requireNonNull(kafkaTemplate, "kafkaTemplate cannot be null");
        Objects.requireNonNull(restaurantRepository, "restaurantRepository cannot be null");
        this.kafkaTemplate = kafkaTemplate;
        this.restaurantRepository = restaurantRepository;
    }

    public Set<Item> sendRestaurantMenu(int restaurantId) {
        Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(restaurantId);
        Set<Item> menu = optionalRestaurant.get().getMenu();
        kafkaTemplate.send(Topic.RESTAURANT_MENU.toString(), menu);
        System.out.println("RESTAURANT_MENU was published to Kafka");
        return menu;
    }
}
