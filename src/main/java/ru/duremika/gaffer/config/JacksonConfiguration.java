package ru.duremika.gaffer.config;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import org.reflections.Reflections;
import ru.duremika.gaffer.action.Action;
import ru.duremika.gaffer.filler.Filler;
import ru.duremika.gaffer.requirement.Requirement;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class JacksonConfiguration {

    public static YAMLMapper yamlMapper() {
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
