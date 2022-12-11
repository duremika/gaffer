package ru.duremika.core.engine;

import lombok.extern.slf4j.Slf4j;
import ru.duremika.core.action.Action;
import ru.duremika.core.classifier.Classifier;
import ru.duremika.core.dto.UserState;
import ru.duremika.core.filler.Filler;
import ru.duremika.core.message.Message;
import ru.duremika.core.message.impl.ErrorMessage;
import ru.duremika.core.message.impl.MessageFromUser;
import ru.duremika.core.message.impl.MultipartMessage;
import ru.duremika.core.requirement.Requirement;
import ru.duremika.core.scenario.Scenario;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class MessageHandler {
    private final Loader loader;
    private final Cache<String, UserState> cache;
    private final TemplateProcessor templateProcessor;
    private UserState userState;

    {
        this.loader = new Loader();
        this.cache = new Cache<>();
        templateProcessor = new TemplateProcessor();
    }


    public Message handle(MessageFromUser message) {
        userState = cache.getOrDefaultWithSave(message.getUserId(), new UserState());
        userState.setMessage(message);
        if (userState.getCurrentScenario() == null) {
            String scenarioName = classify();
            if (scenarioName == null) {
                return defaultAnswer();
            }
            Scenario currentScenario = loader.scenarios.get(scenarioName);
            if (currentScenario == null) {
                log.info("scenario: {} is not exists", scenarioName);
                return defaultAnswer();
            }
            log.info("current scenario: {}", scenarioName);
            userState.setCurrentScenario(scenarioName);
            userState.setCurrentNode(currentScenario.getStartNode());
            Map<String, Map<String, Object>> scenarioState = currentScenario.getViewState();
            userState.getScenarios().put(scenarioName, scenarioState);
        }
        String currentNodeName = userState.getCurrentNode();
        log.info("current node: {}", currentNodeName);
        fillByFiller();
        checkAvailable();
        Message reply = templateProcessor.process(getAnswer(), userState);
        reply.setUserId(message.getUserId());
        log.info("answer: \n{}", reply);
        return reply;
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
                Requirement requirement = availableNode.getRequirement();
                boolean successCheck = true;
                if (requirement != null) {
                    successCheck = requirement.check(userState);
                }
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
            log.info("ask questions for field: {}", fieldName);
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
        if (messageList.size() == 0) {
            return null;
        }
        userState.setCurrentScenario(null);
        userState.setCurrentNode(null);
        userState.setCurrentField(null);
        if (messageList.size() == 1) {
            return messageList.get(0);
        } else {
            return new MultipartMessage(messageList);
        }
    }

    private Message defaultAnswer() {
        log.info("defaultAnswer");
        String userId = userState.getMessage().getUserId();
        Scenario defaultScenario = loader.scenarios.get("default");
        if (defaultScenario == null) {
            log.info("default scenario is not exists");
            return new ErrorMessage(
                    userId,
                    1,
                    "scenario with name \"default\" should always be in the project in case something goes wrong");
        }
        userState.setCurrentScenario("default");
        userState.setCurrentNode(defaultScenario.getStartNode());
        Map<String, Map<String, Object>> scenarioState = defaultScenario.getViewState();
        userState.getScenarios().put("default", scenarioState);
        fillByFiller();
        checkAvailable();
        Message reply = getAnswer();
        reply.setUserId(userId);
        log.info("answer: \n{}", reply);
        return reply;
    }
}
