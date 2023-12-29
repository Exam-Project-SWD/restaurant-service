package com.example.restaurantservice.model.entity;

import com.example.restaurantservice.model.dto.OrderDTO;
import com.example.restaurantservice.model.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "`order`")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "customerId")
    private int customerId;
    @Column(name = "restaurantId")
    private int restaurantId;
    @Column(name = "createdAt")
    private Timestamp createdAt;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Builder.Default
    private List<OrderItem> items = new ArrayList<>();
    @Column(name = "orderPrice")
    private double orderPrice;
    @Column(name = "deliveryPrice")
    private double deliveryPrice;
    @Column(name = "withDelivery")
    private boolean withDelivery;
    @Column(name = "courierId")
    private Integer courierId;
    @Column(name = "processId")
    private String processId;

    public static Order fromDto(OrderDTO dto) {
        Order order = Order.builder()
                .customerId(dto.getCustomerId())
                .restaurantId(dto.getRestaurantId())
                .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                .status(dto.getStatus())
                .items(OrderItem.fromList(dto.getItems()))
                .orderPrice(dto.getOrderPrice())
                .withDelivery(dto.isWithDelivery())
                .deliveryPrice(29)      //TODO: Should be calculated based on distance - for now always 29 kr
                .build();
        for (OrderItem item : order.getItems()) {
            item.setOrder(order);
        }
        return order;
    }
}