package com.maple.graphics.shader.uniform.controllers;

import static org.lwjgl.opengl.GL20.glGetUniformi;
import static org.lwjgl.opengl.GL41.glProgramUniform1i;

public class IntegerUniformController implements IUniformController<Integer> {
    @Override
    public Integer get(int program, int location) {
        return glGetUniformi(program, location);
    }

    @Override
    public void set(int program, int location, Integer value) {
        glProgramUniform1i(program, location, value);
    }
}
