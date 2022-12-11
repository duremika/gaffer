package ru.duremika.core.engine;

import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import ru.duremika.core.classifier.Classifier;
import ru.duremika.core.config.JacksonConfiguration;
import ru.duremika.core.scenario.Scenario;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Loader {
    private final YAMLMapper yamlMapper;
    public Map<String, Classifier> classifiers;
    public Map<String, Scenario> scenarios;

    public Loader() {
        this.yamlMapper = JacksonConfiguration.yamlMapper();
        classifiers = loadClassifiers();
        scenarios = loadScenarios();

    }

    public Map<String, Classifier> loadClassifiers() {
        try (InputStream inputStream = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream("static/classifiers/classifiers.yml")) {
            Map<String, Classifier> classifierMap = yamlMapper.readValue(
                    inputStream,
                    yamlMapper.getTypeFactory().constructMapType(HashMap.class, String.class, Classifier.class)
            );
            classifierMap.entrySet().removeIf(s -> !s.getValue().isEnabled());
            return classifierMap;
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
