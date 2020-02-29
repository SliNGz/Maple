package com.maple.graphics.buffer.vertex.format;

import com.maple.graphics.buffer.vertex.format.element.IVertexFormatElement;

public class VertexFormat {
    private IVertexFormatElement[] mElements;
    private int mVertexSize;

    public VertexFormat(IVertexFormatElement... elements) {
        mElements = elements;
        for (IVertexFormatElement element : mElements) {
            mVertexSize += element.getSize();
        }
    }

    public IVertexFormatElement[] getElements() {
        return mElements;
    }

    public int getVertexSize() {
        return mVertexSize;
    }
}
