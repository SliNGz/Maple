package com.maple.graphics.buffer.vertex.format;

import com.maple.graphics.buffer.vertex.format.element.IVertexFormatElement;
import com.maple.graphics.buffer.vertex.format.element.VertexFormatElementByte;
import com.maple.graphics.buffer.vertex.format.element.VertexFormatElementFloat;

public class VertexFormat {
    public static final VertexFormat POSITION_BUFFER = new VertexFormat(new VertexFormatElementFloat(3));
    public static final VertexFormat COLOR_BUFFER = new VertexFormat(new VertexFormatElementByte(4, false, true));

    private IVertexFormatElement[] mElements;
    private int mStride;

    public VertexFormat(IVertexFormatElement... elements) {
        mElements = elements;
        for (IVertexFormatElement element : mElements) {
            mStride += element.getSize();
        }
    }

    public IVertexFormatElement[] getElements() {
        return mElements;
    }

    public int getStride() {
        return mStride;
    }
}
