package com.maple.graphics.shader.uniform;

import com.maple.graphics.shader.program.ShaderProgram;

import static org.lwjgl.opengl.GL20.glGetUniformf;
import static org.lwjgl.opengl.GL41.glProgramUniform1f;

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
}
