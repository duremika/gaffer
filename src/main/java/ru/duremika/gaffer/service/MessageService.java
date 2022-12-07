package ru.duremika.gaffer.service;

import org.springframework.stereotype.Service;
import ru.duremika.gaffer.message.Message;
import ru.duremika.gaffer.message.impl.MessageToUser;

@Service
public class MessageService {

    public Message handleMessageFromUser(Message message) {
        return new MessageToUser("Hi!");
    }
}
