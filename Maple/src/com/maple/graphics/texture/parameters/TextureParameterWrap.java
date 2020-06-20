package com.maple.graphics.texture.parameters;

import static org.lwjgl.opengl.GL11.GL_REPEAT;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;
import static org.lwjgl.opengl.GL13.GL_CLAMP_TO_BORDER;
import static org.lwjgl.opengl.GL14.GL_MIRRORED_REPEAT;
import static org.lwjgl.opengl.GL44.GL_MIRROR_CLAMP_TO_EDGE;

public class TextureParameterWrap implements ITextureParameter {
    private int mName;
    private TextureParameterValue mValue;

    public TextureParameterWrap(int name, Values value) {
        mName = name;
        mValue = new TextureParameterValue(value.getValue());
    }

    @Override
    public int getName() {
        return mName;
    }

    @Override
    public TextureParameterValue getValue() {
        return mValue;
    }

    public static class Values {
        public static final Values CLAMP_TO_EDGE = new Values(GL_CLAMP_TO_EDGE);
        public static final Values CLAMP_TO_BORDER = new Values(GL_CLAMP_TO_BORDER);
        public static final Values MIRRORED_REPEAT = new Values(GL_MIRRORED_REPEAT);
        public static final Values REPEAT = new Values(GL_REPEAT);
        public static final Values MIRROR_CLAMP_TO_EDGE = new Values(GL_MIRROR_CLAMP_TO_EDGE);

        private int mValue;

        private Values(int value) {
            mValue = value;
        }

        public int getValue() {
            return mValue;
        }
    }
}
