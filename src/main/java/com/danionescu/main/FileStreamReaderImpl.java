package com.danionescu.main;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

@Service
public class FileStreamReaderImpl implements FileStreamReader {
    @Override
    public Stream<String> read(String filePath) throws IOException {
        return Files.lines(Paths.get(filePath));
    }
}