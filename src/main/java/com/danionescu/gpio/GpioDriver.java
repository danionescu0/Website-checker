package com.danionescu.gpio;

public interface GpioDriver {
    void set(String pin, boolean state);
}