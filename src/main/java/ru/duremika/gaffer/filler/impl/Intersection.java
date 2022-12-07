package ru.duremika.gaffer.filler.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import ru.duremika.gaffer.engine.Cache;
import ru.duremika.gaffer.filler.Filler;
import ru.duremika.gaffer.message.Message;
import ru.duremika.gaffer.message.impl.MessageFromUser;
import ru.duremika.gaffer.requirement.exception.NotApplicableRequirementException;
import ru.duremika.gaffer.scenario.Scenario.Node.Field;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@JsonTypeName("intersection")
public class Intersection implements Filler {
//    private final Cache<String, Cache<?, ?>> cache;

    @JsonProperty
    private Map<String, List<String>> cases;

    @Override
    public boolean fill(Message message, Field field) throws Exception {
        if (!(message instanceof MessageFromUser)) {
            throw new NotApplicableRequirementException("requirement SubstringsInMessage are used only with the MessageFromUser");
        }
        String userId = ((MessageFromUser) message).getUserId();
//        if (cache.get(userId) != null) {
//            String scenarioName = field.node.scenario.getName();
//            if (cache.get(scenarioName) != null) {
//
//            }
//        } else {
//            field.value = ((MessageFromUser) message).getText();
//            Cache<String, Scenario> state = new Cache<>();
//            Scenario scenario = field.node.scenario;
//            state.put(scenario.getName(), scenario);
//            cache.put(userId, state);
//        }
        return true;
    }
}
