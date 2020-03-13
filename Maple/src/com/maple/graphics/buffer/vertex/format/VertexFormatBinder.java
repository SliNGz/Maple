package com.maple.graphics.buffer.vertex.format;

import com.maple.graphics.OpenGLType;
import com.maple.graphics.buffer.vertex.format.element.IVertexFormatElement;

import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;

public class VertexFormatBinder {
    public void bind(int index, VertexFormat vertexFormat) {
        IVertexFormatElement[] elements = vertexFormat.getElements();

        long offset = 0;
        for (IVertexFormatElement element : elements) {
            glEnableVertexAttribArray(index);
            OpenGLType type = element.getType();
            glVertexAttribPointer(index, element.getCount(), type.getValue(), element.isNormalized(),
                                  vertexFormat.getStride(), offset);

            ++index;
            offset += element.getSize();
        }
    }
}
