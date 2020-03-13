package com.maple.graphics;

import static org.lwjgl.opengl.GL11.*;

public class OpenGLType {
    public static final OpenGLType BYTE = new OpenGLType(GL_BYTE, 1);
    public static final OpenGLType UNSIGNED_BYTE = new OpenGLType(GL_UNSIGNED_BYTE, 1);
    public static final OpenGLType SHORT = new OpenGLType(GL_SHORT, 2);
    public static final OpenGLType UNSIGNED_SHORT = new OpenGLType(GL_UNSIGNED_SHORT, 2);
    public static final OpenGLType INT = new OpenGLType(GL_INT, 4);
    public static final OpenGLType UNSIGNED_INT = new OpenGLType(GL_UNSIGNED_INT, 4);
    public static final OpenGLType FLOAT = new OpenGLType(GL_FLOAT, 4);
    public static final OpenGLType DOUBLE = new OpenGLType(GL_DOUBLE, 8);

    private int mValue;
    private int mSize;

    private OpenGLType(int value, int size) {
        mValue = value;
        mSize = size;
    }

    public int getValue() {
        return mValue;
    }

    public int getSize() {
        return mSize;
    }
}
