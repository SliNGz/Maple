package com.maple.graphics.buffer.vertex.specification;

import com.maple.graphics.buffer.vertex.format.VertexFormat;

import java.nio.ByteBuffer;

public interface IVertex {
    ByteBuffer[] getDataBuffers();

    VertexFormat getVertexFormat();
}
