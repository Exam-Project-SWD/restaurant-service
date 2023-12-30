package com.example.restaurantservice.service;

import com.example.restaurantservice.model.dto.OrderDTO;
import com.example.restaurantservice.model.entity.Order;
import com.example.restaurantservice.model.enums.OrderStatus;
import com.example.restaurantservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {
    @Autowired
    private KafkaService kafkaService;
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

    @Transactional
    public OrderDTO updateOrderStatus(int id, OrderStatus status) {
        Optional<Order> order = orderRepository.findById(id);
        //TODO: Maybe switch case that does X depending on status?
        if (order.get().getStatus() != status) {
            order.get().setStatus(status);
            orderRepository.save(order.get());
            kafkaService.acceptOrder(id);
        }
        return OrderDTO.fromOrder(order.get());
    }
}