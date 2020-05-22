package com.maple.graphics.shader;

import com.maple.graphics.shader.program.ShaderProgram;
import com.maple.math.Matrix4f;

public class VertexShader implements IShader {
    private static final String MVP_UNIFORM_NAME = "u_mvp";
    private static final String TRANSFORM_UNIFORM_NAME = "u_transform";

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
    public float getFloat(String uniformName) {
        return mShader.getFloat(uniformName);
    }

    @Override
    public void setFloat(String uniformName, float value) {
        mShader.setFloat(uniformName, value);
    }

    @Override
    public Matrix4f getMatrix4f(String uniformName) {
        return mShader.getMatrix4f(uniformName);
    }

    @Override
    public void setMatrix4f(String uniformName, Matrix4f value) {
        mShader.setMatrix4f(uniformName, value);
    }

    public void setMVP(Matrix4f mvp) {
        if (!mvp.equals(mMVP)) {
            setMatrix4f(MVP_UNIFORM_NAME, mvp);
            mMVP = new Matrix4f(mvp);
        }
    }
}
