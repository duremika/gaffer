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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class Loader {
    private final YAMLMapper yamlMapper;
    public List<Classifier> classifiers;
    public List<Scenario> scenarios;

    public Loader(YAMLMapper yamlMapper) {
        this.yamlMapper = yamlMapper;
        classifiers = loadClassifiers().stream()
                .filter(Classifier::isEnabled)
                .collect(Collectors.toList());
        scenarios = loadScenarios().stream()
                .filter(Scenario::isEnabled)
                .collect(Collectors.toList());

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

    public List<Scenario> loadScenarios() {
        File folderScenarios = new File(Objects.requireNonNull(Thread.currentThread().getContextClassLoader()
                .getResource("static/scenarios")).getFile());
        File[] files = Objects.requireNonNull(folderScenarios.listFiles());
        List<Scenario> result = new ArrayList<>();
        for (File file : files) {
            try (InputStream inputStream = Files.newInputStream(Paths.get(file.getPath()))) {
                Scenario scenario = yamlMapper.readValue(inputStream, Scenario.class);
                result.add(scenario);
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
        return result;
    }
}
