package com.maple.renderer;

import com.maple.utils.Color;

import static org.lwjgl.opengl.GL11.*;

public class RendererBufferClearer {
    private static final double DEPTH_NOT_SPECIFIED = -1;

    private int mBufferBitmask;

    public RendererBufferClearer() {
        mBufferBitmask = 0;
    }

    private void enableBufferBit(int bufferBit) {
        mBufferBitmask |= bufferBit;
    }

    private void disableBufferBit(int bufferBit) {
        mBufferBitmask &= ~bufferBit;
    }

    public void enableColorBufferBit() {
        enableBufferBit(GL_COLOR_BUFFER_BIT);
    }

    public void disableColorBufferBit() {
        disableBufferBit(GL_COLOR_BUFFER_BIT);
    }

    public void enableDepthBufferBit() {
        enableBufferBit(GL_DEPTH_BUFFER_BIT);
    }

    public void disableDepthBufferBit() {
        disableBufferBit(GL_DEPTH_BUFFER_BIT);
    }

    public void setColor(Color color) {
        glClearColor(color.getR() / 255.0F, color.getG() / 255.0F, color.getB() / 255.0F, color.getA() / 255.0F);
    }

    public void setDepth(double depth) {
        glClearDepth(depth);
    }

    public void clear(Color color, double depth) {
        if (color != null) {
            setColor(color);
        }

        if (depth != DEPTH_NOT_SPECIFIED) {
            setDepth(depth);
        }

        glClear(mBufferBitmask);
    }

    public void clear() {
        clear(null, DEPTH_NOT_SPECIFIED);
    }

    public void clear(Color color) {
        clear(color, DEPTH_NOT_SPECIFIED);
    }
}
