package ru.duremika.gaffer.requirement.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import ru.duremika.gaffer.message.Message;
import ru.duremika.gaffer.message.impl.MessageFromUser;
import ru.duremika.gaffer.requirement.Requirement;
import ru.duremika.gaffer.requirement.exception.NotApplicableRequirementException;

import java.util.List;

@JsonTypeName("and")
public class And implements Requirement {
    @JsonProperty
    List<Requirement> requirements;

    @Override
    public boolean check(Message message) throws Exception {
        if (!(message instanceof MessageFromUser)) {
            throw new NotApplicableRequirementException("requirement \"and\" are used only with the MessageFromUser");
        }
        for (Requirement requirement : requirements) {
            if (!requirement.check(message)) {
                return false;
            }
        }
        return true;
    }
}
