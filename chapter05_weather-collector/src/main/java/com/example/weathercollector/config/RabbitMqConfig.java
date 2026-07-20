package com.example.weathercollector.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    @Bean
    public DirectExchange notificationExchange(
            @Value("${weather.rabbitmq.exchange}") String exchangeName
    ) {
        return new DirectExchange(exchangeName);
    }

    @Bean
    public Queue notificationQueue(
            @Value("${weather.rabbitmq.queue}") String queueName
    ) {
        return new Queue(queueName, true);
    }

    @Bean
    public Binding notificationBinding(
            Queue notificationQueue,
            DirectExchange notificationExchange,
            @Value("${weather.rabbitmq.routing-key}") String routingKey
    ) {
        return BindingBuilder
                .bind(notificationQueue)
                .to(notificationExchange)
                .with(routingKey);
    }

    @Bean
    public MessageConverter messageConverter() {
        return new JacksonJsonMessageConverter(
                "com.example.weathercollector.message"
        );
    }
}