package com.jpmc.midascore.consumer;

import com.jpmc.midascore.entity.TransactionRecord;
import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.foundation.Incentive;
import com.jpmc.midascore.foundation.Transaction;
import com.jpmc.midascore.repository.TransactionRepository;
import com.jpmc.midascore.repository.UserRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class KafkaConsumer {

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final RestTemplate restTemplate;

    public KafkaConsumer(TransactionRepository transactionRepository, UserRepository userRepository, RestTemplate restTemplate) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
        this.restTemplate = restTemplate;
    }

    @KafkaListener(topics = "${general.kafka-topic}", groupId = "midas-core-group")
    public void receiver(Transaction data) {
        UserRecord sender = userRepository.findById(data.getSenderId());
        UserRecord receiver = userRepository.findById(data.getRecipientId());

        if (sender != null && receiver != null && sender.getBalance() >= data.getAmount()) {
            String url = "http://localhost:8080/incentive";
            Incentive incentiveResponse = restTemplate.postForObject(url, data, Incentive.class);

            float bonus = (incentiveResponse != null) ? incentiveResponse.getAmount() : 0f;

            sender.setBalance(sender.getBalance() - data.getAmount());
            receiver.setBalance(receiver.getBalance() + data.getAmount() + bonus);

            userRepository.save(sender);
            userRepository.save(receiver);

            TransactionRecord record = new TransactionRecord(sender, receiver, data.getAmount(), bonus);
            transactionRepository.save(record);
        }




    }
}
