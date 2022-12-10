package ru.duremika.gaffer.filler.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.RequiredArgsConstructor;
import ru.duremika.gaffer.dto.UserState;
import ru.duremika.gaffer.filler.Filler;
import ru.duremika.gaffer.message.impl.MessageFromUser;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@JsonTypeName("intersection")
public class Intersection implements Filler {

    @JsonProperty
    private Map<String, List<String>> cases;

    @Override
    public Object fill(UserState userState) {
        List<String> messageWords = Arrays.asList( ((MessageFromUser) userState.getMessage()).getText().toLowerCase().split(" "));

        for (Map.Entry<String, List<String>> entry : cases.entrySet()) {
            loop: for (String phraseFromCasses : entry.getValue()) {
                String[] cassesWords = phraseFromCasses.split(" ");
                for (String word : cassesWords) {
                    if ( ! messageWords.contains(word.toLowerCase())) {
                        continue loop;
                    }
                }
                return entry.getKey();
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "Intersection{" +
                "cases=" + cases.keySet() +
                '}';
    }
}
