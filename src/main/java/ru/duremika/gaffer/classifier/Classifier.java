package ru.duremika.gaffer.classifier;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import ru.duremika.gaffer.requirement.Requirement;

@Getter
public class Classifier {
    @JsonProperty
    private String scenario;
    @JsonProperty
    private boolean enabled;
    @JsonProperty
    private Requirement requirement;
}
