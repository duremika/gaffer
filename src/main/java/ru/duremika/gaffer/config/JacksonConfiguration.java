package ru.duremika.gaffer.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.reflections.Reflections;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.duremika.gaffer.message.Message;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Configuration
public class JacksonConfiguration {
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerSubtypes(getAllMessages());
        return objectMapper;
    }

    private Collection<Class<?>> getAllMessages() {
        Reflections reflections = new Reflections("ru.duremika.gaffer.message");
        final Set<Class<? extends Message>> subTypesOfMessage = reflections.getSubTypesOf(Message.class);
        return new HashSet<>(subTypesOfMessage);
    }
}
