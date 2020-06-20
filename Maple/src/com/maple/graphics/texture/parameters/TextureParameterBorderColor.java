package com.maple.graphics.texture.parameters;

import com.maple.math.Vector4f;
import com.maple.utils.Color;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_BORDER_COLOR;

public class TextureParameterBorderColor implements ITextureParameter {
    private TextureParameterValue mValue;

    public TextureParameterBorderColor(Color color) {
        Vector4f vector4f = color.toVector4f();
        mValue = new TextureParameterValue(new float[]{vector4f.X, vector4f.Y, vector4f.Z, vector4f.W});
    }

    @Override
    public int getName() {
        return GL_TEXTURE_BORDER_COLOR;
    }

    @Override
    public TextureParameterValue getValue() {
        return mValue;
    }
}
