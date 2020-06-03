package com.maple.graphics.shader;

import com.maple.graphics.shader.program.ShaderProgram;
import com.maple.graphics.shader.uniform.ShaderUniformController;
import com.maple.math.Matrix4f;
import com.maple.math.Vector2f;
import com.maple.math.Vector4f;
import com.maple.utils.Color;

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

    public int getInt(String name) {
        return mUniformController.getInt(name);
    }

    public void setInt(String name, int value) {
        mUniformController.setInt(name, value);
    }

    public float getFloat(String name) {
        return mUniformController.getFloat(name);
    }

    public void setFloat(String name, float value) {
        mUniformController.setFloat(name, value);
    }

    public Vector2f getVector2f(String name) {
        return mUniformController.getVector2f(name);
    }

    public void setVector2f(String name, Vector2f value) {
        mUniformController.setVector2f(name, value);
    }

    public Vector4f getVector4f(String name) {
        return mUniformController.getVector4f(name);
    }

    public void setVector4f(String name, Vector4f value) {
        mUniformController.setVector4f(name, value);
    }

    public Color getColor(String name) {
        return mUniformController.getColor(name);
    }

    public void setColor(String name, Color color) {
        getUniformController().setColor(name, color);
    }

    public Matrix4f getMatrix4f(String name) {
        return mUniformController.getMatrix4f(name);
    }

    public void setMatrix4f(String name, Matrix4f value) {
        mUniformController.setMatrix4f(name, value);
    }
}
