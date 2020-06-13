package com.maple.graphics.buffer.vertex.specification;

import com.maple.graphics.buffer.vertex.format.VertexFormat;
import com.maple.graphics.buffer.vertex.format.element.VertexFormatElementByte;
import com.maple.graphics.buffer.vertex.format.element.VertexFormatElementFloat;
import com.maple.math.Vector3f;
import com.maple.utils.ByteBufferUtils;
import com.maple.utils.Color;

import java.nio.ByteBuffer;

public class VertexPositionColorNormal implements IVertex {
    private VertexFormat sVertexFormat = new VertexFormat(new VertexFormatElementFloat(3),
                                                          new VertexFormatElementByte(4, false, true),
                                                          new VertexFormatElementFloat(3));

    private Vector3f mPosition;
    private Color mColor;
    private Vector3f mNormal;

    public VertexPositionColorNormal(Vector3f position, Color color, Vector3f normal) {
        mPosition = position;
        mColor = color;
        mNormal = normal;
    }

    public Vector3f getPosition() {
        return mPosition;
    }

    public Color getColor() {
        return mColor;
    }

    public Vector3f getNormal() {
        return mNormal;
    }

    @Override
    public ByteBuffer[] getDataBuffers() {
        return new ByteBuffer[]{
                ByteBufferUtils.extract(mPosition),
                ByteBufferUtils.extract(mColor),
                ByteBufferUtils.extract(mNormal)
        };
    }

    @Override
    public VertexFormat getVertexFormat() {
        return sVertexFormat;
    }
}
