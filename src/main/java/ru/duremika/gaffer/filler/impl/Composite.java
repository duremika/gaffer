package ru.duremika.gaffer.filler.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.RequiredArgsConstructor;
import ru.duremika.gaffer.dto.UserState;
import ru.duremika.gaffer.filler.Filler;

import java.util.List;

@RequiredArgsConstructor
@JsonTypeName("composite")
public class Composite implements Filler {
    @JsonProperty
    private List<Filler> fillers;

    @Override
    public Object fill(UserState userState) throws Exception {
        for (Filler filler : fillers ) {
            Object res = filler.fill(userState);
            if (res != null) {
                return res;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "Composite{" +
                "fillers=" + fillers +
                '}';
    }
}
