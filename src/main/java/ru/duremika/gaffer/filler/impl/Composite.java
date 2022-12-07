package ru.duremika.gaffer.filler.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import ru.duremika.gaffer.engine.Cache;
import ru.duremika.gaffer.filler.Filler;
import ru.duremika.gaffer.message.Message;
import ru.duremika.gaffer.message.impl.MessageFromUser;
import ru.duremika.gaffer.requirement.exception.NotApplicableRequirementException;
import ru.duremika.gaffer.scenario.Scenario.Node.Field;

import java.util.List;

@RequiredArgsConstructor
@JsonTypeName("composite")
public class Composite implements Filler {
    @JsonProperty
    private List<Filler> fillers;

    @Override
    public boolean fill(Message message, Field field) throws Exception {
        for (Filler filler : fillers ) {
            if (filler.fill(message, field)) {
                return true;
            }
        }
        return false;
    }
}
