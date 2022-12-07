package ru.duremika.gaffer.config;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import org.reflections.Reflections;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.duremika.gaffer.action.Action;
import ru.duremika.gaffer.filler.Filler;
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
        objectMapper.registerSubtypes(getAll(Message.class, "ru.duremika.gaffer.message"));
        return objectMapper;
    }

    @Bean
    public YAMLMapper yamlMapper() {
        return YAMLMapper.builder()
                .registerSubtypes(getAll(Requirement.class, "ru.duremika.gaffer.requirement"))
                .registerSubtypes(getAll(Filler.class, "ru.duremika.gaffer.filler"))
                .registerSubtypes(getAll(Action.class, "ru.duremika.gaffer.action"))
                .disable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS)
                .build();
    }

    private static <T> Collection<Class<?>> getAll(Class<T> tClass, String path) {
        Reflections reflections = new Reflections(path);
        final Set<Class<? extends T>> subTypesOfRequirement = reflections.getSubTypesOf(tClass);
        return new HashSet<>(subTypesOfRequirement);
    }
}
