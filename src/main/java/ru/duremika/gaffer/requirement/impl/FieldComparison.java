package ru.duremika.gaffer.requirement.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.RequiredArgsConstructor;
import ru.duremika.gaffer.message.Message;
import ru.duremika.gaffer.message.impl.MessageFromUser;
import ru.duremika.gaffer.requirement.Operator;
import ru.duremika.gaffer.requirement.Requirement;
import ru.duremika.gaffer.requirement.exception.NotApplicableRequirementException;

@RequiredArgsConstructor
@JsonTypeName("field_comparison")
public class FieldComparison implements Requirement {
    @JsonProperty
    private String scenario;

    @JsonProperty
    private String node;

    @JsonProperty
    private String field;

    @JsonProperty
    private Operator operator;

    @Override
    public boolean check(Message message) throws Exception {
        if (!(message instanceof MessageFromUser)) {
            throw new NotApplicableRequirementException("requirement \"field_comparison\" are used only with the MessageFromUser");
        }

        return false;
    }
}
