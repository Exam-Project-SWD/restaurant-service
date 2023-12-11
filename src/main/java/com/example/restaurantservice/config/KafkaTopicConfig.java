package com.example.restaurantservice.config;

import com.example.restaurantservice.model.enums.Topic;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {
    @Bean
    public NewTopic restaurantMenu() {
        return TopicBuilder.name(Topic.RESTAURANT_MENU.toString()).build();
    }
}