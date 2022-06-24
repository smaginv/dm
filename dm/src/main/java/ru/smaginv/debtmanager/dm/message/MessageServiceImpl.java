package ru.smaginv.debtmanager.dm.message;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.smaginv.debtmanager.dm.config.PropertiesConfig;

import java.util.Map;

@Log4j2
@Service
public class MessageServiceImpl implements MessageService {

    private final KafkaTemplate<String, Message> kafkaTemplate;
    private final Map<String, Map<String, Integer>> topics;

    @Autowired
    public MessageServiceImpl(PropertiesConfig propertiesConfig, KafkaTemplate<String, Message> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
        this.topics = propertiesConfig.kafka().getTopics();
    }

    @Override
    public <RQ, RS> Message createMessage(String username, String requestURI, String httpMethod,
                                          RQ requestBody, RS responseBody) {
        Message message = new Message(username, requestURI, httpMethod, requestBody, responseBody);
        log.info("create message: {}", message);
        return message;
    }

    public void sendMessage(Message message) {
        log.info("send message: {}, into topics: {}", message, topics);
        topics.keySet().forEach(topic -> kafkaTemplate.send(topic, message));
    }
}
