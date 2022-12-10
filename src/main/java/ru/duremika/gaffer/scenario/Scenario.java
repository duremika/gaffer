package ru.duremika.gaffer.scenario;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import ru.duremika.gaffer.action.Action;
import ru.duremika.gaffer.filler.Filler;
import ru.duremika.gaffer.requirement.Requirement;

import java.util.Collections;
import java.util.HashMap;
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

    public Map<String, Map<String, Object>> getViewState() {
        Map<String, Map<String, Object>> view = new HashMap<>();
        for (Map.Entry<String, Node> node : scenarioNodes.entrySet()) {
            view.put(node.getKey(), new HashMap<>());
            if (node.getValue().fields == null) {
                continue;
            }
            for (Map.Entry<String, Node.Field> field : node.getValue().fields.entrySet()) {
                view.get(node.getKey()).put(field.getKey(), null);
            }
        }
        return view;
    }

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Node {
        @JsonProperty
        private Requirement requirement;

        public List<Action> getActions() {
            if (actions == null) {
                return Collections.emptyList();
            }
            return actions;
        }

        @JsonProperty
        private List<Action> actions;

        public List<String> getAvailableNodes() {
            if (availableNodes == null) {
                return Collections.emptyList();
            }
            return availableNodes;
        }

        @JsonProperty("available_nodes")
        private List<String> availableNodes;

        @JsonProperty
        private Map<String, Field> fields;

        @Getter
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Field {
            @JsonProperty
            private Filler filler;

            @JsonProperty
            private List<Action> questions;


            public List<Action> getQuestions() {
                if (questions == null) {
                    return Collections.emptyList();
                }
                return questions;
            }
        }
    }
}
