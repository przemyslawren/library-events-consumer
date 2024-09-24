package com.learnkafka.service;

import com.learnkafka.entity.FailureRecord;
import com.learnkafka.jpa.FailureRecordRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class FailureService {
    private final FailureRecordRepository failureRecordRepository;


    public void saveFailedRecord(ConsumerRecord<Integer,String> consumerRecord, Exception e, String status) {
        var failureRecord = FailureRecord.builder()
                .id(null)
                .topic(consumerRecord.topic())
                .key_value(consumerRecord.key())
                .errorRecord(consumerRecord.value())
                .partition(consumerRecord.partition())
                .offset_value(consumerRecord.offset())
                .exception(e.getCause().getMessage())
                .status(status)
                .build();
        log.info("Saving failed record: {}", failureRecord);

        failureRecordRepository.save(failureRecord);
    }
}
