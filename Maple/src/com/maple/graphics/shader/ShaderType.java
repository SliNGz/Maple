package com.maple.graphics.shader;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL41.*;

public class ShaderType {
    public static final List<ShaderType> SHADER_TYPE_LIST = new ArrayList<>();

    public static final ShaderType VERTEX_SHADER = new ShaderType(GL_VERTEX_SHADER, GL_VERTEX_SHADER_BIT);
    public static final ShaderType FRAGMENT_SHADER = new ShaderType(GL_FRAGMENT_SHADER, GL_FRAGMENT_SHADER_BIT);
    public static final ShaderType GEOMETRY_SHADER = new ShaderType(GL_GEOMETRY_SHADER, GL_GEOMETRY_SHADER_BIT);

    private final int mValue;
    private final int mStageBit;

    private ShaderType(int value, int stageBit) {
        mValue = value;
        mStageBit = stageBit;
        SHADER_TYPE_LIST.add(this);
    }

    public int getValue() {
        return mValue;
    }

    public int getStageBit() {
        return mStageBit;
    }
}
