package ru.duremika.gaffer.engine;

import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import org.springframework.stereotype.Component;
import ru.duremika.gaffer.classifier.Classifier;
import ru.duremika.gaffer.scenario.Scenario;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class Loader {
    private final YAMLMapper yamlMapper;
    public List<Classifier> classifiers;
    public Map<String, Scenario> scenarios;

    public Loader(YAMLMapper yamlMapper) {
        this.yamlMapper = yamlMapper;
        classifiers = loadClassifiers().stream()
                .filter(Classifier::isEnabled)
                .collect(Collectors.toList());
        scenarios = loadScenarios();

    }

    public List<Classifier> loadClassifiers() {
        try (InputStream inputStream = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream("static/classifiers/classifiers.yml")) {
            return yamlMapper.readValue(
                    inputStream,
                    yamlMapper.getTypeFactory().constructCollectionType(List.class, Classifier.class)
            );
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public Map<String, Scenario> loadScenarios() {
        File folderScenarios = new File(Objects.requireNonNull(Thread.currentThread().getContextClassLoader()
                .getResource("static/scenarios")).getFile());
        File[] files = Objects.requireNonNull(folderScenarios.listFiles());
        Map<String, Scenario> result = new HashMap<>();
        for (File file : files) {
            try (InputStream inputStream = Files.newInputStream(Paths.get(file.getPath()))) {
                Map<String, Scenario> scenario = yamlMapper.readValue(
                        inputStream,
                        yamlMapper.getTypeFactory().constructMapType(HashMap.class, String.class, Scenario.class)
                );
                result.putAll(scenario);
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
        result.entrySet().removeIf(s -> !s.getValue().isEnabled());
        return result;
    }
}
