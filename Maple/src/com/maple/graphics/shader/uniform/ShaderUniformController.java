package com.maple.graphics.shader.uniform;

import com.maple.graphics.shader.program.ShaderProgram;
import com.maple.math.Matrix4f;
import com.maple.math.Vector2f;
import com.maple.math.Vector4f;
import com.maple.utils.Color;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL41.*;

public class ShaderUniformController {
    private final ShaderProgram mProgram;
    private final ShaderUniformLocationMap mUniformLocationMap;

    public ShaderUniformController(ShaderProgram program) {
        mProgram = program;
        mUniformLocationMap = new ShaderUniformLocationMap(program);
    }

    public int getInt(String name) {
        return glGetUniformi(mProgram.getHandle(), mUniformLocationMap.get(name));
    }

    public void setInt(String name, int value) {
        glProgramUniform1i(mProgram.getHandle(), mUniformLocationMap.get(name), value);
    }

    public float getFloat(String name) {
        return glGetUniformf(mProgram.getHandle(), mUniformLocationMap.get(name));
    }

    public void setFloat(String name, float value) {
        glProgramUniform1f(mProgram.getHandle(), mUniformLocationMap.get(name), value);
    }

    public Matrix4f getMatrix4f(String name) {
        Matrix4f matrix = new Matrix4f();
        FloatBuffer elements = matrix.getElements();
        glGetUniformfv(mProgram.getHandle(), mUniformLocationMap.get(name), elements);
        elements.rewind();

        return matrix;
    }

    public void setMatrix4f(String name, Matrix4f value) {
        FloatBuffer elements = value.getElements();
        elements.rewind();
        glProgramUniformMatrix4fv(mProgram.getHandle(), mUniformLocationMap.get(name), false, elements);
        elements.rewind();
    }

    public Vector2f getVector2f(String name) {
        FloatBuffer floatBuffer = BufferUtils.createFloatBuffer(2);
        glGetUniformfv(mProgram.getHandle(), mUniformLocationMap.get(name), floatBuffer);

        return new Vector2f(floatBuffer.get(0), floatBuffer.get(1));
    }

    public void setVector2f(String name, Vector2f value) {
        glProgramUniform2f(mProgram.getHandle(), mUniformLocationMap.get(name), value.X, value.Y);
    }

    public Vector4f getVector4f(String name) {
        FloatBuffer floatBuffer = BufferUtils.createFloatBuffer(4);
        glGetUniformfv(mProgram.getHandle(), mUniformLocationMap.get(name), floatBuffer);

        return new Vector4f(floatBuffer.get(0), floatBuffer.get(1), floatBuffer.get(2), floatBuffer.get(3));
    }

    public void setVector4f(String name, Vector4f value) {
        glProgramUniform4f(mProgram.getHandle(), mUniformLocationMap.get(name), value.X, value.Y, value.Z, value.W);
    }

    public Color getColor(String name) {
        Vector4f vector4f = getVector4f(name);

        return new Color(vector4f.X, vector4f.Y, vector4f.Z, vector4f.W);
    }

    public void setColor(String name, Color color) {
        setVector4f(name, color.toVector4f());
    }
}
