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

    @Override
    public int getInt(String name) {
        return mUniformController.getInt(name);
    }

    @Override
    public void setInt(String name, int value) {
        mUniformController.setInt(name, value);
    }

    @Override
    public float getFloat(String name) {
        return mUniformController.getFloat(name);
    }

    @Override
    public void setFloat(String name, float value) {
        mUniformController.setFloat(name, value);
    }

    @Override
    public Matrix4f getMatrix4f(String name) {
        return mUniformController.getMatrix4f(name);
    }

    @Override
    public void setMatrix4f(String name, Matrix4f value) {
        mUniformController.setMatrix4f(name, value);
    }
}
