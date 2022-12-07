package ru.duremika.gaffer.requirement.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import ru.duremika.gaffer.message.Message;
import ru.duremika.gaffer.message.impl.MessageFromUser;
import ru.duremika.gaffer.requirement.Requirement;
import ru.duremika.gaffer.requirement.exception.NotApplicableRequirementException;

import java.util.List;
import java.util.Map;

@JsonTypeName("substrings_in_message")
public class SubstringsInMessage implements Requirement {
    @JsonProperty
    List<String> substrings;

    @Override
    public boolean check(Message message, Map<String, Object> args) throws NotApplicableRequirementException {
        if (!(message instanceof MessageFromUser)) {
            throw new NotApplicableRequirementException("requirement SubstringsInMessage are used only with the MessageFromUser");
        }
        String textMessage = ((MessageFromUser) message).getText();
        for (String substring : substrings) {
            if (textMessage.contains(substring)) {
                return true;
            }
        }
        return false;
    }
}
