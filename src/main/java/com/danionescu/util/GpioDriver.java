package com.danionescu.util;

public interface GpioDriver {
    void set(String pin, boolean state);
}