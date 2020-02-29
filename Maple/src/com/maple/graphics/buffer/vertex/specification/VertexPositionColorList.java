package com.maple.graphics.buffer.vertex.specification;

import com.maple.graphics.buffer.vertex.VertexList;
import com.maple.graphics.buffer.vertex.format.VertexFormat;
import com.maple.graphics.buffer.vertex.format.element.VertexFormatElementByte;
import com.maple.graphics.buffer.vertex.format.element.VertexFormatElementFloat;
import com.maple.utils.ByteBufferUtils;

import java.nio.ByteBuffer;

public class VertexPositionColorList extends VertexList<VertexPositionColor> {
    private static final VertexFormat sVertexFormat = new VertexFormat(
            new VertexFormatElementFloat(3),
            new VertexFormatElementByte(4, false, false)
    );

    @Override
    public VertexFormat getVertexFormat() {
        return sVertexFormat;
    }

    @Override
    protected ByteBuffer getVertexData(VertexPositionColor vertex) {
        ByteBuffer positionBuffer = ByteBufferUtils.extract(vertex.getPosition());
        ByteBuffer colorBuffer = ByteBufferUtils.extract(vertex.getColor());

        return ByteBufferUtils.merge(positionBuffer.remaining() + colorBuffer.remaining(),
                                     positionBuffer,
                                     colorBuffer);
    }
}
