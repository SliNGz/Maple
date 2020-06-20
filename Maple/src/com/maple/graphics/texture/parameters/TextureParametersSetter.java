package com.maple.graphics.texture.parameters;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL45.*;

public class TextureParametersSetter {
    public void set(int textureHandle, ITextureParameter... parameters) {
        for (ITextureParameter parameter : parameters) {
            int parameterName = parameter.getName();
            TextureParameterValue value = parameter.getValue();

            switch (value.getType()) {
                case INTEGER:
                    IntBuffer valueIntBuffer = value.getValueIntBuffer();

                    if (valueIntBuffer.capacity() == 1) {
                        glTextureParameteri(textureHandle, parameterName, valueIntBuffer.get());
                    } else {
                        glTextureParameteriv(textureHandle, parameterName, valueIntBuffer);
                    }
                    break;
                case FLOAT:
                    FloatBuffer valueFloatBuffer = value.getValueFloatBuffer();

                    if (valueFloatBuffer.capacity() == 1) {
                        glTextureParameterf(textureHandle, parameterName, valueFloatBuffer.get());
                    } else {
                        glTextureParameterfv(textureHandle, parameterName, valueFloatBuffer);
                    }
                    break;
            }
        }
    }
}
