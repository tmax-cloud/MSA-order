package com.example.order.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;



@Configuration
public class KafkaConfiguration {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
}

