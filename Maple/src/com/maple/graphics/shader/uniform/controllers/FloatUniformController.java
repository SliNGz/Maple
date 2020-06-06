package com.maple.graphics.shader.uniform.controllers;

import static org.lwjgl.opengl.GL20.glGetUniformf;
import static org.lwjgl.opengl.GL41.glProgramUniform1f;

public class FloatUniformController implements IUniformController<Float> {
    @Override
    public Float get(int program, int location) {
        return glGetUniformf(program, location);
    }

    @Override
    public void set(int program, int location, Float value) {
        glProgramUniform1f(program, location, value);
    }
}
