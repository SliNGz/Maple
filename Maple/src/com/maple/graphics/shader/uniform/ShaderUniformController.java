package com.maple.graphics.shader.uniform;

import com.maple.graphics.shader.program.ShaderProgram;
import com.maple.graphics.shader.uniform.controllers.*;
import com.maple.math.Matrix4f;
import com.maple.math.Vector2f;
import com.maple.math.Vector3f;
import com.maple.math.Vector4f;
import com.maple.utils.Color;

import java.util.HashMap;
import java.util.Map;

public class ShaderUniformController {
    private static final IntegerUniformController sIntegerUniformController = new IntegerUniformController();
    private static final FloatUniformController sFloatUniformController = new FloatUniformController();
    private static final Vector2fUniformController sVector2fUniformController = new Vector2fUniformController();
    private static final Vector3fUniformController sVector3fUniformController = new Vector3fUniformController();
    private static final Vector4fUniformController sVector4fUniformController = new Vector4fUniformController();
    private static final Matrix4fUniformController sMatrix4fUniformController = new Matrix4fUniformController();

    private ShaderProgram mProgram;
    private ShaderUniformLocationMap mUniformLocationMap;
    private Map<String, Object> mCache;

    public ShaderUniformController(ShaderProgram program) {
        mProgram = program;
        mUniformLocationMap = new ShaderUniformLocationMap(program);
        mCache = new HashMap<>();
    }

    public int getInt(String name) {
        return getUniformValue(name, sIntegerUniformController);
    }

    public void setInt(String name, int value) {
        setUniformValue(name, value, sIntegerUniformController);
    }

    public float getFloat(String name) {
        return getUniformValue(name, sFloatUniformController);
    }

    public void setFloat(String name, float value) {
        setUniformValue(name, value, sFloatUniformController);
    }

    public Vector2f getVector2f(String name) {
        return getUniformValue(name, sVector2fUniformController);
    }

    public void setVector2f(String name, Vector2f value) {
        setUniformValue(name, new Vector2f(value), sVector2fUniformController);
    }

    public Vector3f getVector3f(String name) {
        return getUniformValue(name, sVector3fUniformController);
    }

    public void setVector3f(String name, Vector3f value) {
        setUniformValue(name, new Vector3f(value), sVector3fUniformController);
    }

    public Vector4f getVector4f(String name) {
        return getUniformValue(name, sVector4fUniformController);
    }

    public void setVector4f(String name, Vector4f value) {
        setUniformValue(name, new Vector4f(value), sVector4fUniformController);
    }

    public Color getColor(String name) {
        Vector4f vector4f = getVector4f(name);

        return new Color(vector4f.X, vector4f.Y, vector4f.Z, vector4f.W);
    }

    public void setColor(String name, Color color) {
        setVector4f(name, color.toVector4f());
    }

    public Matrix4f getMatrix4f(String name) {
        return getUniformValue(name, sMatrix4fUniformController);
    }

    public void setMatrix4f(String name, Matrix4f value) {
        setUniformValue(name, new Matrix4f(value), sMatrix4fUniformController);
    }

    private <T> T getUniformValue(String name, IUniformController<T> uniformController) {
        Object cachedUniformValue = mCache.get(name);
        if (cachedUniformValue == null) {
            T value = uniformController.get(mProgram.getHandle(), mUniformLocationMap.get(name));
            mCache.put(name, value);

            return value;
        }

        return (T) cachedUniformValue;
    }

    private <T> void setUniformValue(String name, T value, IUniformController<T> uniformController) {
        Object cachedUniformValue = mCache.get(name);
        if (!value.equals(cachedUniformValue)) {
            uniformController.set(mProgram.getHandle(), mUniformLocationMap.get(name), value);
            mCache.put(name, value);
        }
    }
}