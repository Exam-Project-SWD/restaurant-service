package com.example.restaurantservice.service;

import com.example.restaurantservice.model.dto.OrderDTO;
import com.example.restaurantservice.model.entity.Order;
import com.example.restaurantservice.model.enums.OrderStatus;
import com.example.restaurantservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    public Order saveOrder(OrderDTO dto) {
        Order toSave = Order.fromDto(dto);
        return orderRepository.save(toSave);
    }

    public Collection<OrderDTO> getAllOrdersWithStatusPending(int restaurantId) {
        Collection<Order> orders = orderRepository.findOrdersByStatusAndRestaurantId(OrderStatus.PENDING, restaurantId);
        Collection<OrderDTO> orderDTOS = new ArrayList<>();

        for (Order o: orders) {
            OrderDTO orderDTO = OrderDTO.fromOrder(o);
            orderDTOS.add(orderDTO);
        }
        return orderDTOS;
    }
}