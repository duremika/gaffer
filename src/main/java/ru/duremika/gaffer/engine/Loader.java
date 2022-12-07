package ru.duremika.gaffer.engine;

import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import org.springframework.stereotype.Component;
import ru.duremika.gaffer.classifier.Classifier;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class Loader {
    private final YAMLMapper yamlMapper;
    public List<Classifier> classifiers;

    public Loader(YAMLMapper yamlMapper) {
        this.yamlMapper = yamlMapper;
        classifiers = loadClassifiers().stream()
                .filter(Classifier::isEnabled)
                .collect(Collectors.toList());
    }

    public List<Classifier> loadClassifiers() {
        try (
                InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("static/classifiers/classifiers.yml")
        ) {
            return yamlMapper.readValue(
                    inputStream,
                    yamlMapper.getTypeFactory().constructCollectionType(List.class, Classifier.class)
            );
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
