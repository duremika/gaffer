package ru.duremika.gaffer.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import org.reflections.Reflections;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.duremika.gaffer.message.Message;
import ru.duremika.gaffer.requirement.Requirement;

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

    @Bean
    public YAMLMapper yamlMapper() {
        YAMLMapper yamlMapper = new YAMLMapper();
        yamlMapper.registerSubtypes(getAllRequirements());
        return yamlMapper;
    }

    private Collection<Class<?>> getAllMessages() {
        Reflections reflections = new Reflections("ru.duremika.gaffer.message");
        final Set<Class<? extends Message>> subTypesOfMessage = reflections.getSubTypesOf(Message.class);
        return new HashSet<>(subTypesOfMessage);
    }
    
    private static Collection<Class<?>> getAllRequirements() {
        Reflections reflections = new Reflections("ru.duremika.gaffer.requirement");
        final Set<Class<? extends Requirement>> subTypesOfRequirement = reflections.getSubTypesOf(Requirement.class);
        return new HashSet<>(subTypesOfRequirement);
    }
}
