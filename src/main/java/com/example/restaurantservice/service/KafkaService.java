package com.example.restaurantservice.service;

import com.example.restaurantservice.model.entity.Item;
import com.example.restaurantservice.model.entity.Restaurant;
import com.example.restaurantservice.model.enums.Topic;
import com.example.restaurantservice.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

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

    public String sendRestaurantMenus() {
        Map<Integer, Set<Item>> restaurantMenuMap = new HashMap<>();
        List<Restaurant> allRestaurants = restaurantRepository.findAll();

        for (Restaurant r: allRestaurants) {
            restaurantMenuMap.put(r.getId(), r.getMenu());
        }

        kafkaTemplate.send(Topic.RESTAURANT_MENU.toString(), restaurantMenuMap);
        return "RESTAURANT_MENU was published to Kafka";
    }
}
