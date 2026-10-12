package com.oros.notificationservice.listener;

import com.oros.notificationservice.event.OrderPlacedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class NotificationListener {

    @KafkaListener(topics = "notificationTopic", groupId = "notificationGroup")
    public void handleNotification(OrderPlacedEvent orderNumber) {
        log.info("Received notification for order number: {}", orderNumber);
    }

}
