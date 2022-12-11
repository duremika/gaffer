package ru.duremika.gaffer.filler.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import ru.duremika.gaffer.dto.UserState;
import ru.duremika.gaffer.filler.Filler;
import ru.duremika.gaffer.message.impl.MessageFromUser;

import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Setter
@JsonTypeName("intersection")
public class Intersection implements Filler {

    @JsonProperty
    private Map<String, List<String>> cases;

    @Override
    public Object fill(UserState userState) {
        String originalText = ((MessageFromUser) userState.getMessage()).getText();
        List<String> messageTokens = normalize(originalText);
        log.info("message tokens: {}", messageTokens);
        for (Map.Entry<String, List<String>> entry : cases.entrySet()) {
            log.info("trying to find intersections for case: {}", entry.getKey());
            loop: for (String phraseFromCases : entry.getValue()) {
                log.info("phrase from cases: {}", phraseFromCases);
                List<String> casesWords = normalize(phraseFromCases);
                for (String word : casesWords) {
                    if ( ! messageTokens.contains(word)) {
                        continue loop;
                    }
                }
                return entry.getKey();
            }
        }
        return null;
    }

    private List<String> normalize(String raw) {
        List<String> res = new ArrayList<>();
        StringTokenizer stringTokenizer = new StringTokenizer(raw, " \t\n\r:,.!?\"'");
        while (stringTokenizer.hasMoreTokens()) {
            String token = stringTokenizer.nextToken();
            token = token.toLowerCase();
            res.add(token);
        }
        return res;
    }

    @Override
    public String toString() {
        return "Intersection{" +
                "cases=" + cases.keySet() +
                '}';
    }
}
