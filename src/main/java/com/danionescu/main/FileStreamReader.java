package com.danionescu.main;


import java.io.IOException;
import java.util.stream.Stream;

public interface FileStreamReader {
    Stream<String> read(String filePath) throws IOException;
}