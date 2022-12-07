package ru.duremika.gaffer.action.impl;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.RequiredArgsConstructor;
import ru.duremika.gaffer.action.Action;

import java.util.List;

@RequiredArgsConstructor
@JsonTypeName("answer_to_user")
@JsonIgnoreProperties(ignoreUnknown = true)
public class AnswerToUser implements Action {
    @JsonProperty
    List<String> answer;

    @JsonProperty
    List<List<String>> buttons;


    @Override
    public void run() {
        System.out.println(answer);
        System.out.println(buttons);
    }
}
