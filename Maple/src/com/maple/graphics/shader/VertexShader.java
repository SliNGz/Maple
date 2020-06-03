package com.maple.graphics.shader;

import com.maple.graphics.shader.program.ShaderProgram;
import com.maple.graphics.shader.uniform.ShaderUniformController;
import com.maple.math.Matrix4f;

public class VertexShader implements IVertexShader {
    private static final String UNIFORM_MVP = "u_MVP";

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
    public ShaderUniformController getUniformController() {
        return mShader.getUniformController();
    }

    public void setMVP(Matrix4f mvp) {
        if (!mvp.equals(mMVP)) {
            getUniformController().setMatrix4f(UNIFORM_MVP, mvp);
            mMVP = new Matrix4f(mvp);
        }
    }
}
