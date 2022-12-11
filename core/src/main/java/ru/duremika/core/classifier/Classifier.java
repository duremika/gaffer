package ru.duremika.core.classifier;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import ru.duremika.core.requirement.Requirement;

@Getter
public class Classifier {
    @JsonProperty
    private String scenario;
    @JsonProperty
    private boolean enabled;
    @JsonProperty(required = true)
    private Requirement requirement;
}
