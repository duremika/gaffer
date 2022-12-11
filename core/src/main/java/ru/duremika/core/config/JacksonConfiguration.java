package ru.duremika.core.config;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import org.reflections.Reflections;
import ru.duremika.core.action.Action;
import ru.duremika.core.filler.Filler;
import ru.duremika.core.requirement.Requirement;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class JacksonConfiguration {

    public static YAMLMapper yamlMapper() {
        return YAMLMapper.builder()
                .registerSubtypes(getAll(Requirement.class, "ru.duremika.core.requirement"))
                .registerSubtypes(getAll(Filler.class, "ru.duremika.core.filler"))
                .registerSubtypes(getAll(Action.class, "ru.duremika.core.action"))
                .disable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS)
                .build();
    }

    private static <T> Collection<Class<?>> getAll(Class<T> tClass, String path) {
        Reflections reflections = new Reflections(path);
        final Set<Class<? extends T>> subTypesOfRequirement = reflections.getSubTypesOf(tClass);
        return new HashSet<>(subTypesOfRequirement);
    }
}
