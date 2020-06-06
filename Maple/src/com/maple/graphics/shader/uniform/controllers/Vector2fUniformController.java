package com.maple.graphics.shader.uniform.controllers;

import com.maple.math.Vector2f;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL20.glGetUniformfv;
import static org.lwjgl.opengl.GL41.glProgramUniform2f;

public class Vector2fUniformController implements IUniformController<Vector2f> {
    @Override
    public Vector2f get(int program, int location) {
        FloatBuffer floatBuffer = BufferUtils.createFloatBuffer(2);
        glGetUniformfv(program, location, floatBuffer);

        return new Vector2f(floatBuffer.get(0), floatBuffer.get(1));
    }

    @Override
    public void set(int program, int location, Vector2f value) {
        glProgramUniform2f(program, location, value.X, value.Y);
    }
}
