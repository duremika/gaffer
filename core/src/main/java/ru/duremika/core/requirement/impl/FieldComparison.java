package ru.duremika.core.requirement.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.RequiredArgsConstructor;
import ru.duremika.core.dto.UserState;
import ru.duremika.core.requirement.Operator;
import ru.duremika.core.requirement.Requirement;

import java.util.Arrays;

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
    public boolean check(UserState userState) {
        String value = (String) userState.getScenarios().get(scenario).get(node).get(field);
        if (
                !Arrays.asList(Operator.Type.EXISTS, Operator.Type.NOT_EXISTS).contains(operator.getType()) &&
                value == null) {
            return false;
        }
        boolean result;
        switch (operator.getType()) {
            case EXISTS:
                result = value != null;
                break;
            case NOT_EXISTS:
                result = value == null;
                break;
            case EQUAL:
                result = operator.getValue().compareToIgnoreCase(value) == 0;
                break;
            case NOT_EQUAL:
                result = operator.getValue().compareToIgnoreCase(value) != 0;
                break;
            case MORE:
                result = operator.getValue().compareToIgnoreCase(value) < 0;
                break;
            case MORE_OR_EQUAL:
                result = operator.getValue().compareToIgnoreCase(value) <= 0;
                break;
            case LESS:
                result = operator.getValue().compareToIgnoreCase(value) > 0;
                break;
            case LESS_OR_EQUAL:
                result = operator.getValue().compareToIgnoreCase(value) >= 0;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + operator.getType());
        }
        return result;
    }
}
