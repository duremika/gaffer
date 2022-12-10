package ru.duremika.gaffer.requirement.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import ru.duremika.gaffer.dto.UserState;
import ru.duremika.gaffer.requirement.Requirement;

@JsonTypeName("not")
public class Not implements Requirement {
    @JsonProperty
    Requirement requirement;

    @Override
    public boolean check(UserState userState) throws Exception {

        return !requirement.check(userState);
    }
}
