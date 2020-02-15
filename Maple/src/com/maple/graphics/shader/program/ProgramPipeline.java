package com.maple.graphics.shader.program;

public class ProgramPipeline {
    private final int mHandle;

    public ProgramPipeline(int handle) {
        mHandle = handle;
    }

    public int getHandle() {
        return mHandle;
    }
}
