package com.example.restaurantservice.model.dto;

import com.example.restaurantservice.model.entity.Order;
import com.example.restaurantservice.model.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderDTO {
    private int customerId;
    private int restaurantId;
    private OrderStatus status;
    private List<OrderItemDTO> items;
    private Timestamp createdAt;
    private Timestamp deliveryTime;
    private boolean withDelivery;
    private String processId;
    private double orderPrice;

    public OrderDTO(int customerId, int restaurantId, OrderStatus status, Timestamp createdAt, List<OrderItemDTO> items, boolean withDelivery, String processId) {
        this.customerId = customerId;
        this.restaurantId = restaurantId;
        this.status = status;
        this.createdAt = createdAt;
        this.items = items;
        this.withDelivery = withDelivery;
        this.processId = processId;
    }

    public static OrderDTO fromOrder(Order order) {
        return new OrderDTO(
                order.getCustomerId(),
                order.getRestaurantId(),
                order.getStatus(),
                order.getCreatedAt(),
                OrderItemDTO.fromList(order.getItems()),
                order.isWithDelivery(),
                order.getProcessId());
    }
}