package ru.duremika.gaffer.engine;

import lombok.extern.slf4j.Slf4j;
import ru.duremika.gaffer.action.Action;
import ru.duremika.gaffer.classifier.Classifier;
import ru.duremika.gaffer.dto.UserState;
import ru.duremika.gaffer.filler.Filler;
import ru.duremika.gaffer.message.Message;
import ru.duremika.gaffer.message.impl.MessageFromUser;
import ru.duremika.gaffer.message.impl.MessageToUser;
import ru.duremika.gaffer.message.impl.MultipartMessage;
import ru.duremika.gaffer.scenario.Scenario;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class MessageHandler {
    private final Loader loader;
    private final Cache<String, UserState> cache;
    private UserState userState;

    {
        this.loader = new Loader();
        this.cache = new Cache<>();
    }


    public Message handle(MessageFromUser message) {
        userState = cache.getOrDefaultWithSave(message.getUserId(), new UserState());
        userState.setMessage(message);
        if (userState.getCurrentScenario() == null) {
            String scenarioName = classify();
            if (scenarioName == null) {
                return defaultAnswer();
            }
            log.info("current scenario: {}", scenarioName);
            Scenario currentScenario = loader.scenarios.get(scenarioName);
            userState.setCurrentScenario(scenarioName);
            userState.setCurrentNode(currentScenario.getStartNode());
            Map<String, Map<String, Object>> scenarioState = currentScenario.getViewState();
            userState.getScenarios().put(scenarioName, scenarioState);
        }
        return continueScenario();
    }

    private String classify() {
        for (Map.Entry<String, Classifier> entry : loader.classifiers.entrySet()) {
            String classifierName = entry.getKey();
            Classifier classifier = entry.getValue();
            try {
                if (classifier.getRequirement().check(userState)) {
                    log.info("classifier {} requirement: true, running scenario {}", classifierName, classifier.getScenario());
                    return classifier.getScenario();
                }
                log.info("classifier {} requirement: false", classifierName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private Message continueScenario() {
        String currentNodeName = userState.getCurrentNode();
        log.info("current node: {}", currentNodeName);
        fillByFiller();
        checkAvailable();
        Message message = getAnswer();
        log.info("answer: \n{}", message);
        return message;
    }

    private void fillByFiller() {
        String currentField = userState.getCurrentField();
        if (currentField == null) {
            return;
        }
        Scenario scenario = loader.scenarios.get(userState.getCurrentScenario());
        Scenario.Node node = scenario.getScenarioNodes().get(userState.getCurrentNode());
        Scenario.Node.Field field = node.getFields().get(currentField);
        Filler filler = field.getFiller();
        Map<String, Object> nodeState = userState.getScenarios().get(userState.getCurrentScenario())
                .get(userState.getCurrentNode());
        try {
            Object res = filler.fill(userState);
            nodeState.put(currentField, res);
            log.info("filler: {} filled field: {} with value: {}", filler, currentField, res);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("filler: {} threw {}, field: {} was not filled", filler, e, currentField);
        }
    }

    private void checkAvailable() {
        Scenario scenario = loader.scenarios.get(userState.getCurrentScenario());
        Scenario.Node node = scenario.getScenarioNodes().get(userState.getCurrentNode());
        for (String availableNodeName : node.getAvailableNodes()) {
            log.info("checking node: {}", availableNodeName);
            Scenario.Node availableNode = scenario.getScenarioNodes().get(availableNodeName);
            try {
                boolean successCheck = availableNode.getRequirement().check(userState);
                if (successCheck) {
                    log.info("chosen node: {}", availableNodeName);
                    userState.setCurrentField(null);
                    userState.setCurrentNode(availableNodeName);
                    checkAvailable();
                    return;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private Message getAnswer() {
        Map<String, Object> nodeState = userState.getScenarios().get(userState.getCurrentScenario())
                .get(userState.getCurrentNode());
        Scenario.Node node = loader.scenarios.get(userState.getCurrentScenario()).getScenarioNodes()
                .get(userState.getCurrentNode());
        List<String> unfilledFieldsNames = nodeState.entrySet().stream()
                .filter(entry -> entry.getValue() == null)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        List<Message> messageList = new ArrayList<>();
        Message answerFromField = getAnswerFromQuestions(node, unfilledFieldsNames);
        if (answerFromField != null) {
            return answerFromField;
        }
        Message answerFromActions = getAnswerFromActions(node);
        if (answerFromActions != null) {
            return answerFromActions;
        }
        return defaultAnswer();
    }

    private Message getAnswerFromQuestions(Scenario.Node node, List<String> unfilledFieldsNames) {
        List<Message> messageList = new ArrayList<>();
        for (String fieldName : unfilledFieldsNames) {
            userState.setCurrentField(fieldName);
            Scenario.Node.Field field = node.getFields().get(fieldName);
            for (Action action : field.getQuestions()) {
                try {
                    Message message = action.run();
                    if (message != null) {
                        messageList.add(message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (messageList.size() == 1) {
                return messageList.get(0);
            } else if (messageList.size() > 1) {
                return new MultipartMessage(messageList);
            }
        }
        return null;
    }

    private Message getAnswerFromActions(Scenario.Node node) {
        List<Message> messageList = new ArrayList<>();
        for (Action action : node.getActions()) {
            try {
                Message message = action.run();
                if (message != null) {
                    messageList.add(message);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (messageList.size() == 1) {
            return messageList.get(0);
        } else if (messageList.size() > 1) {
            return new MultipartMessage(messageList);
        }
        return null;
    }

    private Message defaultAnswer() {
        log.info("defaultAnswer");
        return new MessageToUser("I do not understand you", Collections.singletonList(Collections.emptyList()));
    }
}