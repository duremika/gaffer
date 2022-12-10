package ru.duremika.gaffer.dto;

import lombok.Getter;
import lombok.Setter;
import ru.duremika.gaffer.message.Message;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class UserState {
    private Message message;
    private String currentScenario;
    private String currentNode;
    private String currentField;
    private Map<String, Map<String, Map<String, Object>>> scenarios = new HashMap<>();

}
