package com.maple.graphics.shader.program;

public class ShaderProgram {
    private final int mHandle;

    public ShaderProgram(int handle) {
        mHandle = handle;
    }

    public int getHandle() {
        return mHandle;
    }
}
