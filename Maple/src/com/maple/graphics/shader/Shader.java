package com.maple.graphics.shader;

import com.maple.graphics.shader.program.ShaderProgram;
import com.maple.graphics.shader.uniform.ShaderUniformController;

public class Shader implements IShader {
    private final ShaderType mType;
    private final ShaderProgram mProgram;
    private final ShaderUniformController mUniformController;

    public Shader(ShaderType type, ShaderProgram program, ShaderUniformController uniformController) {
        mType = type;
        mProgram = program;
        mUniformController = uniformController;
    }

    @Override
    public ShaderType getType() {
        return mType;
    }

    @Override
    public ShaderProgram getProgram() {
        return mProgram;
    }

    @Override
    public ShaderUniformController getUniformController() {
        return mUniformController;
    }
}
