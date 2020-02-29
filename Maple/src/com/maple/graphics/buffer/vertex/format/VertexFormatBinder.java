package com.maple.graphics.buffer.vertex.format;

import com.maple.graphics.buffer.vertex.format.element.IVertexFormatElement;

import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;

public class VertexFormatBinder {
    public static void bind(VertexFormat vertexFormat) {
        IVertexFormatElement[] elements = vertexFormat.getElements();

        int index = 0;
        long pointer = 0;
        for (IVertexFormatElement element : elements) {
            glVertexAttribPointer(index, element.getCount(), element.getType(), element.isNormalized(),
                                  vertexFormat.getVertexSize(), pointer);
            glEnableVertexAttribArray(index);

            ++index;
            pointer += element.getSize();
        }
    }
}
