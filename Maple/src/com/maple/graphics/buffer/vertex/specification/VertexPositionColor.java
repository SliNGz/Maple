package com.maple.graphics.buffer.vertex.specification;

import com.maple.graphics.buffer.vertex.format.VertexFormat;
import com.maple.graphics.buffer.vertex.format.element.VertexFormatElementByte;
import com.maple.graphics.buffer.vertex.format.element.VertexFormatElementFloat;
import com.maple.math.Vector3f;
import com.maple.utils.ByteBufferUtils;
import com.maple.utils.Color;

import java.nio.ByteBuffer;

public class VertexPositionColor implements IVertex {
    private static final VertexFormat sVertexFormat = new VertexFormat(new VertexFormatElementFloat(3),
                                                                       new VertexFormatElementByte(4, false, true));

    private Vector3f mPosition;
    private Color mColor;

    public VertexPositionColor(Vector3f position, Color color) {
        mPosition = position;
        mColor = color;
    }

    public Vector3f getPosition() {
        return mPosition;
    }

    public Color getColor() {
        return mColor;
    }

    @Override
    public ByteBuffer[] getDataBuffers() {
        return new ByteBuffer[]{ByteBufferUtils.extract(mPosition), ByteBufferUtils.extract(mColor)};
    }

    @Override
    public VertexFormat getVertexFormat() {
        return sVertexFormat;
    }
}
