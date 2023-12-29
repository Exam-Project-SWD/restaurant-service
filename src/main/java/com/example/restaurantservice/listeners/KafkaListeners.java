package com.example.restaurantservice.listeners;

import com.example.restaurantservice.model.dto.OrderDTO;
import com.example.restaurantservice.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaListeners {
    @Autowired
    private final OrderService orderService;

    @KafkaListener(topics = "NEW_ORDER", id = "new-order-id")
    public void listen(String message) {
        System.out.println(message);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            OrderDTO order = objectMapper.readValue(message, OrderDTO.class);

            orderService.saveOrder(order);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}