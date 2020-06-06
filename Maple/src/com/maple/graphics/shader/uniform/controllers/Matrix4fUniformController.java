package com.maple.graphics.shader.uniform.controllers;

import com.maple.math.Matrix4f;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL20.glGetUniformfv;
import static org.lwjgl.opengl.GL41.glProgramUniformMatrix4fv;

public class Matrix4fUniformController implements IUniformController<Matrix4f> {
    @Override
    public Matrix4f get(int program, int location) {
        Matrix4f matrix = new Matrix4f();
        FloatBuffer elements = matrix.getElements();
        glGetUniformfv(program, location, elements);
        elements.rewind();

        return matrix;
    }

    @Override
    public void set(int program, int location, Matrix4f value) {
        FloatBuffer elements = value.getElements();
        elements.rewind();
        glProgramUniformMatrix4fv(program, location, false, elements);
        elements.rewind();
    }
}
