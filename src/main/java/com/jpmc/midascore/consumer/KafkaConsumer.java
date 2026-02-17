package com.jpmc.midascore.consumer;

import com.jpmc.midascore.foundation.Transaction;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {

    @KafkaListener(topics = "${general.kafka-topic}", groupId = "midas-core-group")
    public void receiver(Transaction data) {
        System.out.println("Received message: " + data.toString());
    }
}
