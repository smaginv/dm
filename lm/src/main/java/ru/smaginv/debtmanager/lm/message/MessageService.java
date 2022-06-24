package ru.smaginv.debtmanager.lm.message;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.smaginv.debtmanager.lm.repository.log.LogRepository;

@Log4j2
@Service
public class MessageService {

    private final MessageMapper messageMapper;
    private final LogRepository logRepository;

    @Autowired
    public MessageService(MessageMapper messageMapper, LogRepository logRepository) {
        this.messageMapper = messageMapper;
        this.logRepository = logRepository;
    }

    @KafkaListener(topics = "${kafka.topic}", groupId = "${kafka.group-id}")
    public void receive(Message message) {
        log.info("received message: {}", message);
        logRepository.save(messageMapper.map(message));
    }
}
