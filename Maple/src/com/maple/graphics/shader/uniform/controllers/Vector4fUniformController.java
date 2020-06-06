package com.maple.graphics.shader.uniform.controllers;

import com.maple.math.Vector4f;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL20.glGetUniformfv;
import static org.lwjgl.opengl.GL41.glProgramUniform4f;

public class Vector4fUniformController implements IUniformController<Vector4f> {
    @Override
    public Vector4f get(int program, int location) {
        FloatBuffer floatBuffer = BufferUtils.createFloatBuffer(4);
        glGetUniformfv(program, location, floatBuffer);

        return new Vector4f(floatBuffer.get(0), floatBuffer.get(1), floatBuffer.get(2), floatBuffer.get(3));
    }

    @Override
    public void set(int program, int location, Vector4f value) {
        glProgramUniform4f(program, location, value.X, value.Y, value.Z, value.W);
    }
}
