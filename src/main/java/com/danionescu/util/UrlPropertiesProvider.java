package com.danionescu.util;

import com.danionescu.model.UrlProperties;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

@Service
public class UrlPropertiesProvider {
    public ArrayList<UrlProperties> get(String filePath) {
        ArrayList<UrlProperties> list = new ArrayList<>();
        try (Stream<String> stream = Files.lines(Paths.get(filePath))) {
            stream.forEach(line -> list.add(new UrlProperties(line, 1000)));
        } catch (IOException e) {
            throw new RuntimeException("Could not read input file");
        }

        return list;
    }
}