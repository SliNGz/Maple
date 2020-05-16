package com.maple.graphics.shader;

import com.maple.graphics.shader.program.ShaderProgram;
import com.maple.math.Matrix4f;

public interface IShader {
    ShaderType getType();

    ShaderProgram getProgram();

    float getFloat(String uniformName);

    void setFloat(String uniformName, float value);

    Matrix4f getMatrix4f(String uniformName);

    void setMatrix4f(String uniformName, Matrix4f value);
}
