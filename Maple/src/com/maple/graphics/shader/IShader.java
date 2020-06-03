package com.maple.graphics.shader;

import com.maple.graphics.shader.program.ShaderProgram;
import com.maple.graphics.shader.uniform.ShaderUniformController;

public interface IShader {
    ShaderType getType();

    ShaderProgram getProgram();

    ShaderUniformController getUniformController();
}
