package com.example.weathercollector.message;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class NotificationPublisher {

    private final RabbitTemplate rabbitTemplate;
    private final String exchangeName;
    private final String routingKey;

    public NotificationPublisher(
            RabbitTemplate rabbitTemplate,
            @Value("${weather.rabbitmq.exchange}") String exchangeName,
            @Value("${weather.rabbitmq.routing-key}") String routingKey
    ) {
        this.rabbitTemplate = rabbitTemplate;
        this.exchangeName = exchangeName;
        this.routingKey = routingKey;
    }

    public void publish(NotificationMessage message) {
        System.out.println(
                "[NotificationPublisher] 메시지 발행, weatherDataId="
                        + message.weatherDataId()
        );

        rabbitTemplate.convertAndSend(
                exchangeName,
                routingKey,
                message
        );
    }
}