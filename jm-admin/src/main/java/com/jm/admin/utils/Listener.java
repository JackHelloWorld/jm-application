package com.jm.admin.utils;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Listener {

	@KafkaListener(topics = {"admin.sys.topic"})
    public void listen(ConsumerRecord<?, ?> record) {
        log.info("kafka的key:{} ",record.key());
        log.info("kafka的value: {}", record.value().toString());
    }
	
	
}
