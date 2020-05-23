package com.maple.graphics.buffer.vertex.specification;

import com.maple.graphics.buffer.vertex.format.VertexFormat;
import com.maple.graphics.buffer.vertex.format.element.VertexFormatElementFloat;
import com.maple.math.Vector2f;
import com.maple.math.Vector3f;
import com.maple.utils.ByteBufferUtils;

import java.nio.ByteBuffer;

public class VertexPositionTexture implements IVertex {
    private static final VertexFormat sVertexFormat = new VertexFormat(new VertexFormatElementFloat(3),
                                                                       new VertexFormatElementFloat(2));

    private Vector3f mPosition;
    private Vector2f mTextureCoordinate;

    public VertexPositionTexture(Vector3f position, Vector2f textureCoordinate) {
        mPosition = position;
        mTextureCoordinate = textureCoordinate;
    }

    @Override
    public ByteBuffer[] getDataBuffers() {
        return new ByteBuffer[]{
                ByteBufferUtils.extract(mPosition),
                ByteBufferUtils.extract(mTextureCoordinate)
        };
    }

    @Override
    public VertexFormat getVertexFormat() {
        return sVertexFormat;
    }
}
