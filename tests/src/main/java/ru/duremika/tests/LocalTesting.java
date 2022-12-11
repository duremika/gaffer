package ru.duremika.tests;

import ru.duremika.core.engine.Cache;
import ru.duremika.core.engine.MessageHandler;
import ru.duremika.core.message.impl.MessageFromUser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;

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
                messageHandler.handle(message(input));
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
}
