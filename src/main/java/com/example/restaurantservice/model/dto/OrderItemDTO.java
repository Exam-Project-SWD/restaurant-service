package com.example.restaurantservice.model.dto;

import com.example.restaurantservice.model.entity.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderItemDTO {
    private int menuItemId;
    private int quantity;
    private int orderId;

    public static List<OrderItemDTO> fromList(List<OrderItem> items) {
        List<OrderItemDTO> orderItems = new ArrayList<>();
        for (OrderItem item : items
        ) {
            orderItems.add(new OrderItemDTO(
                    item.getMenuItemId(),
                    item.getQuantity(), item.getOrder().getId()));
        }
        return orderItems;
    }
}