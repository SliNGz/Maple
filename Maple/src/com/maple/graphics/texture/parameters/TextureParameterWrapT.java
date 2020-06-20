package com.maple.graphics.texture.parameters;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_T;

public class TextureParameterWrapT implements ITextureParameter {
    private TextureParameterWrap mParameter;

    public TextureParameterWrapT(TextureParameterWrap.Values value) {
        mParameter = new TextureParameterWrap(GL_TEXTURE_WRAP_T, value);
    }

    @Override
    public int getName() {
        return mParameter.getName();
    }

    @Override
    public TextureParameterValue getValue() {
        return mParameter.getValue();
    }
}
