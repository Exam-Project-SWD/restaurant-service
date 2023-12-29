package com.example.restaurantservice.controller;

import com.example.restaurantservice.model.dto.OrderDTO;
import com.example.restaurantservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private final OrderService orderService;

    @GetMapping("/pending")
    public ResponseEntity<Collection<OrderDTO>> getPendingOrders(@RequestParam("restaurantId") int id) {
        return ResponseEntity.ok(orderService.getAllOrdersWithStatusPending(id));
    }
}
