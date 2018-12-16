package com.danionescu.main;

import com.danionescu.model.UrlProperties;

import java.util.ArrayList;

public interface UrlPropertiesProvider {
    ArrayList<UrlProperties> get(String filePath);
}
