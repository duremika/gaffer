package ru.duremika.core.requirement.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import ru.duremika.core.dto.UserState;
import ru.duremika.core.requirement.Requirement;

@JsonTypeName("not")
public class Not implements Requirement {
    @JsonProperty
    Requirement requirement;

    @Override
    public boolean check(UserState userState) throws Exception {

        return !requirement.check(userState);
    }
}
