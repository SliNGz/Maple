package com.maple.graphics.texture;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.*;
import static org.lwjgl.opengl.GL30.GL_HALF_FLOAT;

public class TexelDataType {
    public static TexelDataType UNSIGNED_BYTE = new TexelDataType(GL_UNSIGNED_BYTE);
    public static TexelDataType BYTE = new TexelDataType(GL_BYTE);
    public static TexelDataType UNSIGNED_SHORT = new TexelDataType(GL_UNSIGNED_SHORT);
    public static TexelDataType SHORT = new TexelDataType(GL_SHORT);
    public static TexelDataType UNSIGNED_INT = new TexelDataType(GL_UNSIGNED_INT);
    public static TexelDataType INT = new TexelDataType(GL_INT);
    public static TexelDataType HALF_FLOAT = new TexelDataType(GL_HALF_FLOAT);
    public static TexelDataType FLOAT = new TexelDataType(GL_FLOAT);
    public static TexelDataType UNSIGNED_BYTE_3_3_2 = new TexelDataType(GL_UNSIGNED_BYTE_3_3_2);
    public static TexelDataType UNSIGNED_BYTE_2_3_3_REV = new TexelDataType(GL_UNSIGNED_BYTE_2_3_3_REV);
    public static TexelDataType UNSIGNED_SHORT_5_6_5 = new TexelDataType(GL_UNSIGNED_SHORT_5_6_5);
    public static TexelDataType UNSIGNED_SHORT_5_6_5_REV = new TexelDataType(GL_UNSIGNED_SHORT_5_6_5_REV);
    public static TexelDataType UNSIGNED_SHORT_4_4_4_4 = new TexelDataType(GL_UNSIGNED_SHORT_4_4_4_4);
    public static TexelDataType UNSIGNED_SHORT_4_4_4_4_REV = new TexelDataType(GL_UNSIGNED_SHORT_4_4_4_4_REV);
    public static TexelDataType UNSIGNED_SHORT_5_5_5_1 = new TexelDataType(GL_UNSIGNED_SHORT_5_5_5_1);
    public static TexelDataType UNSIGNED_SHORT_1_5_5_5_REV = new TexelDataType(GL_UNSIGNED_SHORT_1_5_5_5_REV);
    public static TexelDataType UNSIGNED_INT_8_8_8_8 = new TexelDataType(GL_UNSIGNED_INT_8_8_8_8);
    public static TexelDataType UNSIGNED_INT_8_8_8_8_REV = new TexelDataType(GL_UNSIGNED_INT_8_8_8_8_REV);
    public static TexelDataType UNSIGNED_INT_10_10_10_2 = new TexelDataType(GL_UNSIGNED_INT_10_10_10_2);
    public static TexelDataType UNSIGNED_INT_2_10_10_10_REV = new TexelDataType(GL_UNSIGNED_INT_2_10_10_10_REV);

    private int mValue;

    public TexelDataType(int value) {
        mValue = value;
    }

    public int getValue() {
        return mValue;
    }
}
