package com.maple.graphics.texture;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.GL_BGR;
import static org.lwjgl.opengl.GL12.GL_BGRA;
import static org.lwjgl.opengl.GL30.*;

public class PixelDataFormat {
    public static final PixelDataFormat RED = new PixelDataFormat(GL_RED);
    public static final PixelDataFormat RG = new PixelDataFormat(GL_RG);
    public static final PixelDataFormat RGB = new PixelDataFormat(GL_RGB);
    public static final PixelDataFormat BGR = new PixelDataFormat(GL_BGR);
    public static final PixelDataFormat RGBA = new PixelDataFormat(GL_RGBA);
    public static final PixelDataFormat BGRA = new PixelDataFormat(GL_BGRA);
    public static final PixelDataFormat RED_INTEGER = new PixelDataFormat(GL_RED_INTEGER);
    public static final PixelDataFormat RG_INTEGER = new PixelDataFormat(GL_RG_INTEGER);
    public static final PixelDataFormat RGB_INTEGER = new PixelDataFormat(GL_RGB_INTEGER);
    public static final PixelDataFormat BGR_INTEGER = new PixelDataFormat(GL_BGR_INTEGER);
    public static final PixelDataFormat RGBA_INTEGER = new PixelDataFormat(GL_RGBA_INTEGER);
    public static final PixelDataFormat BGRA_INTEGER = new PixelDataFormat(GL_BGRA_INTEGER);
    public static final PixelDataFormat STENCIL_INDEX = new PixelDataFormat(GL_STENCIL_INDEX);
    public static final PixelDataFormat DEPTH_COMPONENT = new PixelDataFormat(GL_DEPTH_COMPONENT);
    public static final PixelDataFormat DEPTH_STENCIL = new PixelDataFormat(GL_DEPTH_STENCIL);

    private int mValue;

    public PixelDataFormat(int value) {
        mValue = value;
    }

    public int getValue() {
        return mValue;
    }
}
