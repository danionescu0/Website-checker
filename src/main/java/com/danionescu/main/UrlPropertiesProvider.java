package com.danionescu.main;

import com.danionescu.model.UrlProperties;

import java.util.ArrayList;

/**
 * Created by ionescu on 10.03.2018.
 */
public interface UrlPropertiesProvider {
    ArrayList<UrlProperties> get(String filePath);
}
