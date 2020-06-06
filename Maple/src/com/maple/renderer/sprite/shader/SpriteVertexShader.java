package com.maple.renderer.sprite.shader;

import com.maple.graphics.shader.IShader;
import com.maple.graphics.shader.ShaderType;
import com.maple.graphics.shader.program.ShaderProgram;
import com.maple.graphics.shader.uniform.ShaderUniformController;
import com.maple.math.Vector2f;

public class SpriteVertexShader implements IShader {
    private static final String UNIFORM_MASK_POSITION = "u_MaskPosition";
    private static final String UNIFORM_MASK_DIMENSIONS = "u_MaskDimensions";

    private IShader mShader;

    public SpriteVertexShader(IShader shader) {
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

    public void setMask(Vector2f position, Vector2f dimensions) {
        getUniformController().setVector2f(UNIFORM_MASK_POSITION, position);
        getUniformController().setVector2f(UNIFORM_MASK_DIMENSIONS, dimensions);
    }
}
