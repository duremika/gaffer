package ru.duremika.core.engine;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.IContext;
import ru.duremika.core.dto.UserState;
import ru.duremika.core.message.Message;
import ru.duremika.core.message.impl.MessageToUser;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class TemplateProcessor {
    private final TemplateEngine templateEngine;

    public TemplateProcessor() {
        this.templateEngine = new TemplateEngine();
        initialize();
    }

    private void initialize() {
        templateEngine.getConfiguration();
    }

    public String process(String template, UserState userState) {
        return templateEngine.process(template, new Context(userState));
    }

    public MessageToUser process(MessageToUser message, UserState userState) {
        List<List<String>> buttons = message.getButtons();
        if (buttons != null) {
            for (List<String> row : buttons) {
                row.replaceAll(button -> process(button, userState));
            }
        }
        return new MessageToUser(process(message.getText(), userState), buttons);
    }

    public Message process(Message message, UserState userState) {
        if (message instanceof MessageToUser) {
            return process((MessageToUser) message, userState);
        }
        return message;
    }

    private static class Context implements IContext {
        private final Map<String, Map<String, Map<String, Object>>> variables;

        public Context(UserState userState) {
            this.variables = userState.getScenarios();
        }

        @Override
        public Locale getLocale() {
            return Locale.ROOT;
        }

        @Override
        public boolean containsVariable(String name) {
            return variables.containsKey(name);
        }

        @Override
        public Set<String> getVariableNames() {
            return variables.keySet();
        }

        @Override
        public Object getVariable(String name) {
            return variables.get(name);
        }
    }
}
