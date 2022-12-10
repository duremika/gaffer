package ru.duremika.gaffer.action.impl;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.duremika.gaffer.action.Action;
import ru.duremika.gaffer.message.impl.MessageToUser;

import java.util.List;
import java.util.Random;

@Slf4j
@RequiredArgsConstructor
@JsonTypeName("answer_to_user")
@JsonIgnoreProperties(ignoreUnknown = true)
public class AnswerToUser implements Action {
    @JsonProperty
    List<String> answer;

    @JsonProperty
    List<List<String>> buttons;

    private static final Random random = new Random();

    @Override
    public MessageToUser run() {
        return new MessageToUser(random(answer), buttons);
    }

    private static String random(List<String> answer) {
        int index = random.nextInt(answer.size());
        return answer.get(index);
    }
}
