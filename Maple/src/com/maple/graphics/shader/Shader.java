package com.maple.graphics.shader;

import com.maple.graphics.shader.program.ShaderProgram;
import com.maple.graphics.shader.uniform.ShaderUniformController;

public class Shader {
    private final ShaderType mType;
    private final ShaderProgram mProgram;
    private final ShaderUniformController mUniformController;

    public Shader(ShaderType type, ShaderProgram program, ShaderUniformController uniformController) {
        mType = type;
        mProgram = program;
        mUniformController = uniformController;
    }

    public ShaderType getType() {
        return mType;
    }

    public ShaderProgram getProgram() {
        return mProgram;
    }

    public ShaderUniformController getUniformController() {
        return mUniformController;
    }
}
