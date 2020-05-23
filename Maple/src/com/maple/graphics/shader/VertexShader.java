package com.maple.graphics.shader;

import com.maple.graphics.shader.program.ShaderProgram;
import com.maple.math.Matrix4f;

public class VertexShader implements IShader {
    private static final String MVP_UNIFORM_NAME = "u_MVP";

    private Shader mShader;

    private Matrix4f mMVP;

    public VertexShader(Shader shader) {
        mShader = shader;
        mMVP = null;
    }

    @Override
    public ShaderType getType() {
        return mShader.getType();
    }

    @Override
    public ShaderProgram getProgram() {
        return mShader.getProgram();
    }

    @Override
    public int getInt(String name) {
        return mShader.getInt(name);
    }

    @Override
    public void setInt(String name, int value) {
        mShader.setInt(name, value);
    }

    @Override
    public float getFloat(String name) {
        return mShader.getFloat(name);
    }

    @Override
    public void setFloat(String name, float value) {
        mShader.setFloat(name, value);
    }

    @Override
    public Matrix4f getMatrix4f(String name) {
        return mShader.getMatrix4f(name);
    }

    @Override
    public void setMatrix4f(String name, Matrix4f value) {
        mShader.setMatrix4f(name, value);
    }

    public void setMVP(Matrix4f mvp) {
        if (!mvp.equals(mMVP)) {
            setMatrix4f(MVP_UNIFORM_NAME, mvp);
            mMVP = new Matrix4f(mvp);
        }
    }
}
