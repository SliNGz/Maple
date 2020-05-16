package com.maple.graphics.shader;

import com.maple.graphics.shader.program.ShaderProgram;
import com.maple.graphics.shader.uniform.ShaderUniformController;
import com.maple.math.Matrix4f;

public class Shader implements IShader {
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

    public float getFloat(String uniformName) {
        return mUniformController.getFloat(uniformName);
    }

    public void setFloat(String uniformName, float value) {
        mUniformController.setFloat(uniformName, value);
    }

    public Matrix4f getMatrix4f(String uniformName) {
        return mUniformController.getMatrix4f(uniformName);
    }

    public void setMatrix4f(String uniformName, Matrix4f value) {
        mUniformController.setMatrix4f(uniformName, value);
    }
}
