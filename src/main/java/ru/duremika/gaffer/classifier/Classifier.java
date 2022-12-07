package ru.duremika.gaffer.classifier;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import org.reflections.Reflections;
import ru.duremika.gaffer.requirement.Requirement;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Classifier {
    public static List<Classifier> classifiers;
    static {
        classifiers = loadAll();
    }
    @JsonProperty
    private String scenario;
    @JsonProperty
    private boolean enabled;
    @JsonProperty
    private Requirement requirement;

    private static List<Classifier> loadAll() {
        try (
                InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("static/classifiers/classifiers.yml")
        ) {
            ObjectMapper objectMapper = new YAMLMapper();
            objectMapper.registerSubtypes(getAllRequirements());
            return objectMapper.readValue(
                    inputStream,
                    objectMapper.getTypeFactory().constructCollectionType(List.class, Classifier.class));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Collection<Class<?>> getAllRequirements() {
        Reflections reflections = new Reflections("ru.duremika.gaffer.requirement");
        final Set<Class<? extends Requirement>> subTypesOfRequirement = reflections.getSubTypesOf(Requirement.class);
        return new HashSet<>(subTypesOfRequirement);
    }

}
