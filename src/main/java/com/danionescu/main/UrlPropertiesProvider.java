package com.danionescu.main;

import com.danionescu.model.UrlProperties;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@Service
public class UrlPropertiesProvider {
    public ArrayList<UrlProperties> get(String filePath) {
        ArrayList<UrlProperties> list = new ArrayList<>();
        try (Stream<String> stream = Files.lines(Paths.get(filePath))) {
            stream.forEach(line -> {
                String[] components = line.split(" ");
                if (components.length < 2) {
                    throw new RuntimeException("Error parsing url file");
                }
                try {
                    List<String> regexMatchers = Arrays.asList(components).subList(2, components.length);
                    list.add(new UrlProperties(new URI(components[0]), Integer.parseInt(components[1]), regexMatchers));
                } catch (URISyntaxException e) {
                    throw new RuntimeException("Could not read input file");
                }
            });
        } catch (IOException e) {
            throw new RuntimeException("Could not read input file");
        }

        return list;
    }
}