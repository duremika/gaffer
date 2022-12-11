package ru.duremika.core.requirement.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import ru.duremika.core.dto.UserState;
import ru.duremika.core.message.impl.MessageFromUser;
import ru.duremika.core.requirement.Requirement;

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
