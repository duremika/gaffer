package ru.duremika.gaffer.scenario;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import ru.duremika.gaffer.action.Action;
import ru.duremika.gaffer.filler.Filler;
import ru.duremika.gaffer.requirement.Requirement;

import java.util.List;
import java.util.Map;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Scenario {
    @JsonProperty
    private boolean enabled = true;

    @JsonProperty("start_node")
    private String startNode;

    @JsonProperty("scenario_nodes")
    private Map<String, Node> scenarioNodes;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Node {
        @JsonProperty
        private Requirement requirement;

        @JsonProperty
        private List<Action> actions;

        @JsonProperty("available_nodes")
        private List<String> availableNodes;

        @JsonProperty
        private Map<String, Field> fields;

        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Field {
            @JsonProperty
            private Filler filler;

            @JsonProperty
            private List<Action> questions;
        }
    }
}
