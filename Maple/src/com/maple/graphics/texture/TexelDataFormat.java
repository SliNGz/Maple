package com.maple.graphics.texture;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.GL_BGR;
import static org.lwjgl.opengl.GL12.GL_BGRA;
import static org.lwjgl.opengl.GL30.*;

public class TexelDataFormat {
    public static final TexelDataFormat RED = new TexelDataFormat(GL_RED);
    public static final TexelDataFormat RG = new TexelDataFormat(GL_RG);
    public static final TexelDataFormat RGB = new TexelDataFormat(GL_RGB);
    public static final TexelDataFormat BGR = new TexelDataFormat(GL_BGR);
    public static final TexelDataFormat RGBA = new TexelDataFormat(GL_RGBA);
    public static final TexelDataFormat BGRA = new TexelDataFormat(GL_BGRA);
    public static final TexelDataFormat RED_INTEGER = new TexelDataFormat(GL_RED_INTEGER);
    public static final TexelDataFormat RG_INTEGER = new TexelDataFormat(GL_RG_INTEGER);
    public static final TexelDataFormat RGB_INTEGER = new TexelDataFormat(GL_RGB_INTEGER);
    public static final TexelDataFormat BGR_INTEGER = new TexelDataFormat(GL_BGR_INTEGER);
    public static final TexelDataFormat RGBA_INTEGER = new TexelDataFormat(GL_RGBA_INTEGER);
    public static final TexelDataFormat BGRA_INTEGER = new TexelDataFormat(GL_BGRA_INTEGER);
    public static final TexelDataFormat STENCIL_INDEX = new TexelDataFormat(GL_STENCIL_INDEX);
    public static final TexelDataFormat DEPTH_COMPONENT = new TexelDataFormat(GL_DEPTH_COMPONENT);
    public static final TexelDataFormat DEPTH_STENCIL = new TexelDataFormat(GL_DEPTH_STENCIL);

    private int mValue;

    public TexelDataFormat(int value) {
        mValue = value;
    }

    public int getValue() {
        return mValue;
    }
}
