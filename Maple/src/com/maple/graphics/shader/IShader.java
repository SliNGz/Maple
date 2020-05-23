package com.maple.graphics.shader;

import com.maple.graphics.shader.program.ShaderProgram;
import com.maple.math.Matrix4f;

public interface IShader {
    ShaderType getType();

    ShaderProgram getProgram();

    int getInt(String name);

    void setInt(String name, int value);

    float getFloat(String name);

    void setFloat(String name, float value);

    Matrix4f getMatrix4f(String name);

    void setMatrix4f(String name, Matrix4f value);
}
