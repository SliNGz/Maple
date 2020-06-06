package com.maple.graphics.shader.uniform.controllers;

public interface IUniformController<T> {
    T get(int program, int location);

    void set(int program, int location, T value);
}
