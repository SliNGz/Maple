package com.maple.renderer.sprite.shader;

import com.maple.graphics.shader.IShader;
import com.maple.graphics.shader.ShaderType;
import com.maple.graphics.shader.program.ShaderProgram;
import com.maple.graphics.shader.uniform.ShaderUniformController;
import com.maple.utils.Color;

public class SpriteFragmentShader implements IShader {
    private static final String UNIFORM_COLOR = "u_Color";

    private IShader mShader;

    public SpriteFragmentShader(IShader shader) {
        mShader = shader;
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

    public void setColor(Color color) {
        getUniformController().setColor(UNIFORM_COLOR, color);
    }
}
