package ru.duremika.tests;

import ru.duremika.core.engine.Cache;
import ru.duremika.core.engine.MessageHandler;
import ru.duremika.core.message.Message;
import ru.duremika.core.message.impl.MessageFromUser;
import ru.duremika.core.message.impl.MessageToUser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

public class LocalTesting {
    MessageHandler messageHandler = new MessageHandler();
    String userId = "local_testing";


    public static void main(String[] args) {
        new LocalTesting().cliLoop();
    }

    private void cliLoop() {

        try (
                InputStreamReader inputStreamReader = new InputStreamReader(System.in);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader)
        ) {
            while (true) {
                System.out.print(">>> ");
                String input = bufferedReader.readLine();
                if (input == null) {
                    return;
                } else if (input.startsWith("/")) {
                    handleCommand(input);
                    continue;
                }
                Message message = messageHandler.handle(message(input));
                if (message instanceof MessageToUser) {
                    System.out.println(representationMessageFromUser((MessageToUser) message));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleCommand(String input) {
        switch (input){
            case "/reset":
                doReset();
                break;
        }
    }

    private void doReset() {
        Field messageHandlerCache;
        try {
            messageHandlerCache = messageHandler.getClass().getDeclaredField("cache");
            messageHandlerCache.setAccessible(true);
            messageHandlerCache.set(messageHandler, new Cache<>());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private MessageFromUser message(String messageText) {
        return new MessageFromUser(userId, messageText);
    }

    private String representationMessageFromUser(MessageToUser message) {
        String delimiter = "__________________________________________________";
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(delimiter);
        stringBuilder.append('\n');
        stringBuilder.append(message.getText());
        final List<List<String>> buttons = message.getButtons();
        stringBuilder.append((buttons == null || buttons.isEmpty() ?
                "" :
                "\n\n" + buttons.stream()
                        .map(buttonsRow -> buttonsRow.stream()
                                .map(button -> "[  " + button + "  ]")
                                .collect(Collectors.joining("   ")))
                        .collect(Collectors.joining("\n"))));
        stringBuilder.append('\n');
        stringBuilder.append(delimiter);
        return stringBuilder.toString();
    }
}
