package ru.duremika.gaffer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.duremika.gaffer.classifier.Classifier;
import ru.duremika.gaffer.engine.Loader;
import ru.duremika.gaffer.message.Message;
import ru.duremika.gaffer.message.impl.MessageToUser;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final Loader loader;

    public Message handleMessageFromUser(Message message) {
        for (Classifier classifier : loader.classifiers) {
            try {
                if (classifier.getRequirement().check(message)) {
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new MessageToUser("I do not understand you");
    }
}
