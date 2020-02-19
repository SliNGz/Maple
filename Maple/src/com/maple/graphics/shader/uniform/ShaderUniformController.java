package com.maple.graphics.shader.uniform;

import com.maple.graphics.shader.program.ShaderProgram;
import com.maple.math.Matrix4f;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL20.glGetUniformf;
import static org.lwjgl.opengl.GL20.glGetUniformfv;
import static org.lwjgl.opengl.GL41.glProgramUniform1f;
import static org.lwjgl.opengl.GL41.glProgramUniformMatrix4fv;

public class ShaderUniformController {
    private final ShaderProgram mProgram;
    private final ShaderUniformLocationMap mUniformLocationMap;

    public ShaderUniformController(ShaderProgram program) {
        mProgram = program;
        mUniformLocationMap = new ShaderUniformLocationMap(program);
    }

    public float getFloat(String uniformName) {
        return glGetUniformf(mProgram.getHandle(), mUniformLocationMap.get(uniformName));
    }

    public void setFloat(String uniformName, float value) {
        glProgramUniform1f(mProgram.getHandle(), mUniformLocationMap.get(uniformName), value);
    }

    public Matrix4f getMatrix4f(String uniformName) {
        Matrix4f matrix = new Matrix4f();
        FloatBuffer elements = matrix.getElements();
        glGetUniformfv(mProgram.getHandle(), mUniformLocationMap.get(uniformName), elements);
        elements.rewind();

        return matrix;
    }

    public void setMatrix4f(String uniformName, Matrix4f value) {
        FloatBuffer elements = value.getElements();
        elements.rewind();
        glProgramUniformMatrix4fv(mProgram.getHandle(), mUniformLocationMap.get(uniformName), false, elements);
        elements.rewind();
    }
}
