package com.BusinessGuru.MentorshipManagementBackend.services;

import com.BusinessGuru.MentorshipManagementBackend.Dto.UserSyncDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


@Service
public class KafkaMessageListener {

    private static final Logger logger = LoggerFactory.getLogger(KafkaMessageListener.class);

    @Autowired
    private ProfileService profileService;

    @Autowired
    private ObjectMapper objectMapper;

    @KafkaListener(topics = "profile5", groupId = "jt-group")
    public void consumeEvents(ConsumerRecord<String, String> record) {
        try {
            logger.info("Received message: {}", record);
            // Extract the value from the ConsumerRecord - it's a JSON string
            String jsonValue = record.value();
            // Parse the JSON string to your DTO
            UserSyncDto syncDto = objectMapper.readValue(jsonValue, UserSyncDto.class);
            logger.info("Converted to UserSyncDto: {}", syncDto);
            profileService.syncUserFromAuthentication(syncDto);
        } catch (Exception e) {
            logger.error("Error processing Kafka message: {}", e.getMessage(), e);
        }
    }
}