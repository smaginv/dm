package ru.smaginv.debtmanager.dm.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import ru.smaginv.debtmanager.dm.message.Message;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class KafkaConfig {

    private final List<String> bootstrapServers;
    private final Map<String, Map<String, Integer>> topics;

    @Autowired
    public KafkaConfig(PropertiesConfig propertiesConfig) {
        this.topics = propertiesConfig.kafka().getTopics();
        this.bootstrapServers = propertiesConfig.kafka().getBootstrapServers();
    }

    @Bean
    public ProducerFactory<String, Message> producerFactory() {
        Map<String, Object> config = new HashMap<>();
        bootstrapServers.forEach(address -> config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, address));
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(config);
    }

    @Bean
    public KafkaTemplate<String, Message> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public KafkaAdmin.NewTopics topics() {
        return new KafkaAdmin.NewTopics(topics.keySet()
                .stream()
                .map(topic -> TopicBuilder
                        .name(topic)
                        .partitions(topics.get(topic).get("partitions"))
                        .replicas(topics.get(topic).get("replicas"))
                        .build())
                .toArray(NewTopic[]::new));
    }
}
