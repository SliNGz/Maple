package com.maple.graphics.texture;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL31.*;
import static org.lwjgl.opengl.GL33.GL_RGB10_A2UI;
import static org.lwjgl.opengl.GL42.*;

public class TextureInternalFormat {
    public static final TextureInternalFormat DEPTH_COMPONENT = new TextureInternalFormat(GL_DEPTH_COMPONENT);
    public static final TextureInternalFormat DEPTH_STENCIL = new TextureInternalFormat(GL_DEPTH_STENCIL);
    public static final TextureInternalFormat RED = new TextureInternalFormat(GL_RED);
    public static final TextureInternalFormat RG = new TextureInternalFormat(GL_RG);
    public static final TextureInternalFormat RGB = new TextureInternalFormat(GL_RGB);
    public static final TextureInternalFormat RGBA = new TextureInternalFormat(GL_RGBA);

    public static final TextureInternalFormat R8 = new TextureInternalFormat(GL_R8);
    public static final TextureInternalFormat R8_SNORM = new TextureInternalFormat(GL_R8_SNORM);
    public static final TextureInternalFormat R16 = new TextureInternalFormat(GL_R16);
    public static final TextureInternalFormat R16_SNORM = new TextureInternalFormat(GL_R16_SNORM);
    public static final TextureInternalFormat RG8 = new TextureInternalFormat(GL_RG8);
    public static final TextureInternalFormat RG8_SNORM = new TextureInternalFormat(GL_RG8_SNORM);
    public static final TextureInternalFormat RG16 = new TextureInternalFormat(GL_RG16);
    public static final TextureInternalFormat RG16_SNORM = new TextureInternalFormat(GL_RG16_SNORM);
    public static final TextureInternalFormat R3_G3_B2 = new TextureInternalFormat(GL_R3_G3_B2);
    public static final TextureInternalFormat RGB4 = new TextureInternalFormat(GL_RGB4);
    public static final TextureInternalFormat RGB5 = new TextureInternalFormat(GL_RGB5);
    public static final TextureInternalFormat RGB8 = new TextureInternalFormat(GL_RGB8);
    public static final TextureInternalFormat RGB8_SNORM = new TextureInternalFormat(GL_RGB8_SNORM);
    public static final TextureInternalFormat RGB10 = new TextureInternalFormat(GL_RGB10);
    public static final TextureInternalFormat RGB12 = new TextureInternalFormat(GL_RGB12);
    public static final TextureInternalFormat RGB16_SNORM = new TextureInternalFormat(GL_RGB16_SNORM);
    public static final TextureInternalFormat RGBA2 = new TextureInternalFormat(GL_RGBA2);
    public static final TextureInternalFormat RGBA4 = new TextureInternalFormat(GL_RGBA4);
    public static final TextureInternalFormat RGB5_A1 = new TextureInternalFormat(GL_RGB5_A1);
    public static final TextureInternalFormat RGBA8 = new TextureInternalFormat(GL_RGBA8);
    public static final TextureInternalFormat RGBA8_SNORM = new TextureInternalFormat(GL_RGBA8_SNORM);
    public static final TextureInternalFormat RGB10_A2 = new TextureInternalFormat(GL_RGB10_A2);
    public static final TextureInternalFormat RGB10_A2UI = new TextureInternalFormat(GL_RGB10_A2UI);
    public static final TextureInternalFormat RGBA12 = new TextureInternalFormat(GL_RGBA12);
    public static final TextureInternalFormat RGBA16 = new TextureInternalFormat(GL_RGBA16);
    public static final TextureInternalFormat SRGB8 = new TextureInternalFormat(GL_SRGB8);
    public static final TextureInternalFormat SRGB8_ALPHA8 = new TextureInternalFormat(GL_SRGB8_ALPHA8);
    public static final TextureInternalFormat R16F = new TextureInternalFormat(GL_R16F);
    public static final TextureInternalFormat RG16F = new TextureInternalFormat(GL_RG16F);
    public static final TextureInternalFormat RGB16F = new TextureInternalFormat(GL_RGB16F);
    public static final TextureInternalFormat RGBA16F = new TextureInternalFormat(GL_RGBA16F);
    public static final TextureInternalFormat R32F = new TextureInternalFormat(GL_R32F);
    public static final TextureInternalFormat RG32F = new TextureInternalFormat(GL_RG32F);
    public static final TextureInternalFormat RGB32F = new TextureInternalFormat(GL_RGB32F);
    public static final TextureInternalFormat RGBA32F = new TextureInternalFormat(GL_RGBA32F);
    public static final TextureInternalFormat R11F_G11F_B10F = new TextureInternalFormat(GL_R11F_G11F_B10F);
    public static final TextureInternalFormat RGB9_E5 = new TextureInternalFormat(GL_RGB9_E5);
    public static final TextureInternalFormat R8I = new TextureInternalFormat(GL_R8I);
    public static final TextureInternalFormat R8UI = new TextureInternalFormat(GL_R8UI);
    public static final TextureInternalFormat R16I = new TextureInternalFormat(GL_R16I);
    public static final TextureInternalFormat R16UI = new TextureInternalFormat(GL_R16UI);
    public static final TextureInternalFormat R32I = new TextureInternalFormat(GL_R32I);
    public static final TextureInternalFormat R32UI = new TextureInternalFormat(GL_R32UI);
    public static final TextureInternalFormat RG8I = new TextureInternalFormat(GL_RG8I);
    public static final TextureInternalFormat RG8UI = new TextureInternalFormat(GL_RG8UI);
    public static final TextureInternalFormat RG16I = new TextureInternalFormat(GL_RG16I);
    public static final TextureInternalFormat RG16UI = new TextureInternalFormat(GL_RG16UI);
    public static final TextureInternalFormat RG32I = new TextureInternalFormat(GL_RG32I);
    public static final TextureInternalFormat RG32UI = new TextureInternalFormat(GL_RG32UI);
    public static final TextureInternalFormat RGB8I = new TextureInternalFormat(GL_RGB8I);
    public static final TextureInternalFormat RGB8UI = new TextureInternalFormat(GL_RGB8UI);
    public static final TextureInternalFormat RGB16I = new TextureInternalFormat(GL_RGB16I);
    public static final TextureInternalFormat RGB16UI = new TextureInternalFormat(GL_RGB16UI);
    public static final TextureInternalFormat RGB32I = new TextureInternalFormat(GL_RGB32I);
    public static final TextureInternalFormat RGB32UI = new TextureInternalFormat(GL_RGB32UI);
    public static final TextureInternalFormat RGBA8I = new TextureInternalFormat(GL_RGBA8I);
    public static final TextureInternalFormat RGBA8UI = new TextureInternalFormat(GL_RGBA8UI);
    public static final TextureInternalFormat RGBA16I = new TextureInternalFormat(GL_RGBA16I);
    public static final TextureInternalFormat RGBA16UI = new TextureInternalFormat(GL_RGBA16UI);
    public static final TextureInternalFormat RGBA32I = new TextureInternalFormat(GL_RGBA32I);
    public static final TextureInternalFormat RGBA32UI = new TextureInternalFormat(GL_RGBA32UI);

    public static final TextureInternalFormat COMPRESSED_RED = new TextureInternalFormat(GL_COMPRESSED_RED);
    public static final TextureInternalFormat COMPRESSED_RG = new TextureInternalFormat(GL_COMPRESSED_RG);
    public static final TextureInternalFormat COMPRESSED_RGB = new TextureInternalFormat(GL_COMPRESSED_RGB);
    public static final TextureInternalFormat COMPRESSED_RGBA = new TextureInternalFormat(GL_COMPRESSED_RGBA);
    public static final TextureInternalFormat COMPRESSED_SRGB = new TextureInternalFormat(GL_COMPRESSED_SRGB);
    public static final TextureInternalFormat COMPRESSED_SRGB_ALPHA = new TextureInternalFormat(GL_COMPRESSED_SRGB_ALPHA);
    public static final TextureInternalFormat COMPRESSED_RED_RGTC1 = new TextureInternalFormat(GL_COMPRESSED_RED_RGTC1);
    public static final TextureInternalFormat COMPRESSED_SIGNED_RED_RGTC1 = new TextureInternalFormat(GL_COMPRESSED_SIGNED_RED_RGTC1);
    public static final TextureInternalFormat COMPRESSED_RG_RGTC2 = new TextureInternalFormat(GL_COMPRESSED_RG_RGTC2);
    public static final TextureInternalFormat COMPRESSED_SIGNED_RG_RGTC2 = new TextureInternalFormat(GL_COMPRESSED_SIGNED_RG_RGTC2);
    public static final TextureInternalFormat COMPRESSED_RGBA_BPTC_UNORM = new TextureInternalFormat(GL_COMPRESSED_RGBA_BPTC_UNORM);
    public static final TextureInternalFormat COMPRESSED_SRGB_ALPHA_BPTC_UNORM = new TextureInternalFormat(GL_COMPRESSED_SRGB_ALPHA_BPTC_UNORM);
    public static final TextureInternalFormat COMPRESSED_RGB_BPTC_SIGNED_FLOAT = new TextureInternalFormat(GL_COMPRESSED_RGB_BPTC_SIGNED_FLOAT);
    public static final TextureInternalFormat COMPRESSED_RGB_BPTC_UNSIGNED_FLOAT = new TextureInternalFormat(GL_COMPRESSED_RGB_BPTC_UNSIGNED_FLOAT);

    private int mValue;

    private TextureInternalFormat(int value) {
        mValue = value;
    }

    public int getValue() {
        return mValue;
    }
}
