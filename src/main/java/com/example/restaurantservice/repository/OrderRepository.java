package com.example.restaurantservice.repository;

import com.example.restaurantservice.model.entity.Order;
import com.example.restaurantservice.model.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    Collection<Order> findOrdersByStatusAndRestaurantId(OrderStatus status, int id);
}