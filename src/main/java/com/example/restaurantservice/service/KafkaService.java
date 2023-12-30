package com.example.restaurantservice.service;

import com.example.restaurantservice.model.dto.OrderDTO;
import com.example.restaurantservice.model.entity.Item;
import com.example.restaurantservice.model.entity.Order;
import com.example.restaurantservice.model.entity.Restaurant;
import com.example.restaurantservice.model.enums.Topic;
import com.example.restaurantservice.repository.OrderRepository;
import com.example.restaurantservice.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class KafkaService {
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final RestaurantRepository restaurantRepository;
    private final OrderRepository orderRepository;

    public String sendRestaurantMenus() {
        Map<Integer, Set<Item>> restaurantMenuMap = new HashMap<>();
        List<Restaurant> allRestaurants = restaurantRepository.findAll();

        for (Restaurant r: allRestaurants) {
            restaurantMenuMap.put(r.getId(), r.getMenu());
        }

        kafkaTemplate.send(Topic.RESTAURANT_MENU.toString(), restaurantMenuMap);
        return "RESTAURANT_MENU was published to Kafka";
    }

    public String orderAccepted(Order order) {
        OrderDTO orderDTO = OrderDTO.fromOrder(order);

        kafkaTemplate.send(Topic.ORDER_ACCEPTED.name(), orderDTO);
        return "ORDER_ACCEPTED was published to Kafka";
    }

    public String orderAvailable(Order order) {
        OrderDTO orderDTO = OrderDTO.fromOrder(order);
        kafkaTemplate.send(Topic.ORDER_AVAILABLE.name(), orderDTO);
        return "ORDER_AVAILABLE was published to Kafka";
    }
}