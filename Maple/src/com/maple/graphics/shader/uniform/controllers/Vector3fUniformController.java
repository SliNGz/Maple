package com.maple.graphics.shader.uniform.controllers;

import com.maple.math.Vector3f;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL20.glGetUniformfv;
import static org.lwjgl.opengl.GL41.glProgramUniform3f;

public class Vector3fUniformController implements IUniformController<Vector3f> {
    @Override
    public Vector3f get(int program, int location) {
        FloatBuffer floatBuffer = BufferUtils.createFloatBuffer(3);
        glGetUniformfv(program, location, floatBuffer);

        return new Vector3f(floatBuffer.get(0), floatBuffer.get(1), floatBuffer.get(2));
    }

    @Override
    public void set(int program, int location, Vector3f value) {
        glProgramUniform3f(program, location, value.X, value.Y, value.Z);
    }
}
