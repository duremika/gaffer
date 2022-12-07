package ru.duremika.gaffer.requirement.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import ru.duremika.gaffer.message.Message;
import ru.duremika.gaffer.message.impl.MessageFromUser;
import ru.duremika.gaffer.requirement.Requirement;
import ru.duremika.gaffer.requirement.exception.NotApplicableRequirementException;

import java.util.List;

@JsonTypeName("not")
public class Not implements Requirement {
    @JsonProperty
    Requirement requirement;

    @Override
    public boolean check(Message message) throws Exception {
        if (!(message instanceof MessageFromUser)) {
            throw new NotApplicableRequirementException("requirement \"or\" are used only with the MessageFromUser");
        }
        return !requirement.check(message);
    }
}
