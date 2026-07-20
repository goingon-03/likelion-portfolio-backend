package com.example.weathercollector.message;

import com.example.weathercollector.entity.Notification;
import com.example.weathercollector.repository.NotificationRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
public class NotificationConsumer {

    private final NotificationRepository notificationRepository;

    public NotificationConsumer(
            NotificationRepository notificationRepository
    ) {
        this.notificationRepository = notificationRepository;
    }

    @RabbitListener(queues = "${weather.rabbitmq.queue}")
    @Transactional
    public void consume(NotificationMessage message) {
        System.out.println(
                "[NotificationConsumer] 메시지 수신, weatherDataId="
                        + message.weatherDataId()
        );

        String notificationText = createNotificationText(message);

        Notification notification = new Notification(
                message.weatherDataId(),
                notificationText,
                LocalDateTime.now()
        );

        notificationRepository.save(notification);

        System.out.println(
                "[NotificationConsumer] 알림 저장 성공, weatherDataId="
                        + message.weatherDataId()
        );
    }

    private String createNotificationText(
            NotificationMessage message
    ) {
        return "날씨 수집 완료 - 기온: "
                + message.temperature()
                + "℃, 습도: "
                + message.humidity()
                + "%, 강수량: "
                + message.precipitation()
                + "mm";
    }
}