package com.danionescu.util;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

@Service
public class UrlProvider {
    public ArrayList<String> get(String filePath) {
        ArrayList<String> list = new ArrayList<>();
        try (Stream<String> stream = Files.lines(Paths.get(filePath))) {
            stream.forEach(line -> list.add(line));
        } catch (IOException e) {
            throw new RuntimeException("Could not read input file");
        }

        return list;
    }
}