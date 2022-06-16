package ru.smaginv.debtmanager.dm.message;

public interface MessageService {

    <RQ, RS> Message createMessage(String username, String requestURI, String httpMethod,
                                   RQ requestBody, RS responseBody);

    void sendMessage(Message message);
}
