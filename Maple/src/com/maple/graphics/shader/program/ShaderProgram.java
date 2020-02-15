package com.maple.graphics.shader.program;

public class ShaderProgram {
    public static final ShaderProgram NULL = new ShaderProgram(0);

    private final int mHandle;

    public ShaderProgram(int handle) {
        mHandle = handle;
    }

    public int getHandle() {
        return mHandle;
    }
}
