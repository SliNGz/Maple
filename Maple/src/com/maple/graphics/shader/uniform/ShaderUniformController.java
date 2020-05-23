package com.maple.graphics.shader.uniform;

import com.maple.graphics.shader.program.ShaderProgram;
import com.maple.math.Matrix4f;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL41.*;

public class ShaderUniformController {
    private final ShaderProgram mProgram;
    private final ShaderUniformLocationMap mUniformLocationMap;

    public ShaderUniformController(ShaderProgram program) {
        mProgram = program;
        mUniformLocationMap = new ShaderUniformLocationMap(program);
    }

    public int getInt(String name) {
        return glGetUniformi(mProgram.getHandle(), mUniformLocationMap.get(name));
    }

    public void setInt(String name, int value) {
        glProgramUniform1i(mProgram.getHandle(), mUniformLocationMap.get(name), value);
    }

    public float getFloat(String name) {
        return glGetUniformf(mProgram.getHandle(), mUniformLocationMap.get(name));
    }

    public void setFloat(String name, float value) {
        glProgramUniform1f(mProgram.getHandle(), mUniformLocationMap.get(name), value);
    }

    public Matrix4f getMatrix4f(String name) {
        Matrix4f matrix = new Matrix4f();
        FloatBuffer elements = matrix.getElements();
        glGetUniformfv(mProgram.getHandle(), mUniformLocationMap.get(name), elements);
        elements.rewind();

        return matrix;
    }

    public void setMatrix4f(String name, Matrix4f value) {
        FloatBuffer elements = value.getElements();
        elements.rewind();
        glProgramUniformMatrix4fv(mProgram.getHandle(), mUniformLocationMap.get(name), false, elements);
        elements.rewind();
    }
}
