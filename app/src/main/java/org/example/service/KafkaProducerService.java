package org.example.service;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class KafkaProducerService implements AutoCloseable {

    private final KafkaProducer<String, String> kafkaProducer;
    private final String topic = "exchange-rates";

    public KafkaProducerService() {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        kafkaProducer = new KafkaProducer<>(props);
    }

    public void sendMessage(String key, String message) {
        kafkaProducer.send(new ProducerRecord<>(topic, key, message));
        System.out.println("📤 Sent to Kafka: " + message);
    }

    @Override
    public void close() {
        kafkaProducer.close();
    }
}
