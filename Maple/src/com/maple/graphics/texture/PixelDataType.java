package com.maple.graphics.texture;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.*;
import static org.lwjgl.opengl.GL30.GL_HALF_FLOAT;

public class PixelDataType {
    public static PixelDataType UNSIGNED_BYTE = new PixelDataType(GL_UNSIGNED_BYTE);
    public static PixelDataType BYTE = new PixelDataType(GL_BYTE);
    public static PixelDataType UNSIGNED_SHORT = new PixelDataType(GL_UNSIGNED_SHORT);
    public static PixelDataType SHORT = new PixelDataType(GL_SHORT);
    public static PixelDataType UNSIGNED_INT = new PixelDataType(GL_UNSIGNED_INT);
    public static PixelDataType INT = new PixelDataType(GL_INT);
    public static PixelDataType HALF_FLOAT = new PixelDataType(GL_HALF_FLOAT);
    public static PixelDataType FLOAT = new PixelDataType(GL_FLOAT);
    public static PixelDataType UNSIGNED_BYTE_3_3_2 = new PixelDataType(GL_UNSIGNED_BYTE_3_3_2);
    public static PixelDataType UNSIGNED_BYTE_2_3_3_REV = new PixelDataType(GL_UNSIGNED_BYTE_2_3_3_REV);
    public static PixelDataType UNSIGNED_SHORT_5_6_5 = new PixelDataType(GL_UNSIGNED_SHORT_5_6_5);
    public static PixelDataType UNSIGNED_SHORT_5_6_5_REV = new PixelDataType(GL_UNSIGNED_SHORT_5_6_5_REV);
    public static PixelDataType UNSIGNED_SHORT_4_4_4_4 = new PixelDataType(GL_UNSIGNED_SHORT_4_4_4_4);
    public static PixelDataType UNSIGNED_SHORT_4_4_4_4_REV = new PixelDataType(GL_UNSIGNED_SHORT_4_4_4_4_REV);
    public static PixelDataType UNSIGNED_SHORT_5_5_5_1 = new PixelDataType(GL_UNSIGNED_SHORT_5_5_5_1);
    public static PixelDataType UNSIGNED_SHORT_1_5_5_5_REV = new PixelDataType(GL_UNSIGNED_SHORT_1_5_5_5_REV);
    public static PixelDataType UNSIGNED_INT_8_8_8_8 = new PixelDataType(GL_UNSIGNED_INT_8_8_8_8);
    public static PixelDataType UNSIGNED_INT_8_8_8_8_REV = new PixelDataType(GL_UNSIGNED_INT_8_8_8_8_REV);
    public static PixelDataType UNSIGNED_INT_10_10_10_2 = new PixelDataType(GL_UNSIGNED_INT_10_10_10_2);
    public static PixelDataType UNSIGNED_INT_2_10_10_10_REV = new PixelDataType(GL_UNSIGNED_INT_2_10_10_10_REV);

    private int mValue;

    private PixelDataType(int value) {
        mValue = value;
    }

    public int getValue() {
        return mValue;
    }
}
