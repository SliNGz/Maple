package com.maple.graphics.texture.parameters;

import org.lwjgl.BufferUtils;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class TextureParameterValue {
    private TextureParameterType mType;
    private ByteBuffer mData;

    public TextureParameterValue(int[] values) {
        mType = TextureParameterType.INTEGER;
        mData = BufferUtils.createByteBuffer(Integer.BYTES * values.length);
        for (int value : values) {
            mData.putInt(value);
        }
        mData.flip();
    }

    public TextureParameterValue(int value) {
        this(new int[]{value});
    }

    public TextureParameterValue(float[] values) {
        mType = TextureParameterType.FLOAT;
        mData = BufferUtils.createByteBuffer(Float.BYTES * values.length);
        for (float value : values) {
            mData.putFloat(value);
        }
        mData.flip();
    }

    public TextureParameterValue(float value) {
        this(new float[]{value});
    }

    public TextureParameterType getType() {
        return mType;
    }

    public IntBuffer getValueIntBuffer() {
        return mData.asIntBuffer();
    }

    public FloatBuffer getValueFloatBuffer() {
        return mData.asFloatBuffer();
    }
}
