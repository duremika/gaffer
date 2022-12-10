package ru.duremika.gaffer.requirement.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import ru.duremika.gaffer.dto.UserState;
import ru.duremika.gaffer.message.impl.MessageFromUser;
import ru.duremika.gaffer.requirement.Requirement;

import java.util.List;

@JsonTypeName("substrings_in_message")
public class SubstringsInMessage implements Requirement {
    @JsonProperty
    List<String> substrings;

    @Override
    public boolean check(UserState userState) {

        String textMessage = ((MessageFromUser) userState.getMessage()).getText();
        for (String substring : substrings) {
            if (textMessage.contains(substring)) {
                return true;
            }
        }
        return false;
    }
}
