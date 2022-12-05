package ru.duremika.gaffer.message;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import ru.duremika.gaffer.message.impl.AnswerToUser;
import ru.duremika.gaffer.message.impl.MessageFromUser;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "message_name")
@JsonSubTypes({
        @JsonSubTypes.Type(name = "MESSAGE_FROM_USER", value = MessageFromUser.class),
        @JsonSubTypes.Type(name = "ANSWER_TO_USER", value = AnswerToUser.class),
})
public interface Message {
}
