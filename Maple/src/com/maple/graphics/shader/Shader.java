package com.maple.graphics.shader;

import com.maple.graphics.shader.program.ShaderProgram;

public class Shader {
    private final ShaderType mType;
    private final ShaderProgram mProgram;

    public Shader(ShaderType type, ShaderProgram program) {
        mType = type;
        mProgram = program;
    }

    public ShaderType getType() {
        return mType;
    }

    public ShaderProgram getProgram() {
        return mProgram;
    }
}
