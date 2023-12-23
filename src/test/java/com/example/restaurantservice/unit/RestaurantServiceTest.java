package com.example.restaurantservice.unit;

import com.example.restaurantservice.model.ItemsRequest;
import com.example.restaurantservice.model.entity.Item;
import com.example.restaurantservice.model.entity.Restaurant;
import com.example.restaurantservice.repository.RestaurantRepository;
import com.example.restaurantservice.service.RestaurantService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class RestaurantServiceTest {
    @MockBean
    private RestaurantRepository restaurantRepository;
    @Autowired
    private RestaurantService restaurantService;

    @Disabled
    @Test
    void testAddNewItems() {
        Set<Item> currentMenu = new HashSet<>();
        Item item = Item.builder().id(1).name("soda").description("cola").price(25).build();
        currentMenu.add(item);

        Restaurant restaurant = Restaurant.builder()
                .id(1).name("pizzahut").email("ph@gmail.com").phone("12345678").password("password").menu(currentMenu).build();

        when(restaurantRepository.save(restaurant)).thenReturn(restaurant);
        when(restaurantRepository.findById(restaurant.getId())).thenReturn(Optional.of(restaurant));
        restaurantRepository.save(restaurant);


        Set<Item> menu = new HashSet<>(Arrays.asList(Item.builder().id(2).name("burger").description("hamburger").price(15.5).build(),
                Item.builder().id(3).name("pizza").description("pepperoni").price(65).build()));

        ItemsRequest request = new ItemsRequest(restaurant.getId(), menu);
        restaurantService.addNewItems(request);

        assertTrue(restaurant.getMenu().containsAll(menu));
    }
}
