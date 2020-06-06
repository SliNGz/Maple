package com.maple.graphics.shader.effect;

import com.maple.graphics.shader.IShader;
import com.maple.graphics.shader.ShaderType;
import com.maple.graphics.shader.exceptions.ShaderNotSetException;

import java.util.HashMap;
import java.util.Map;

public class Effect {
    private Map<ShaderType, IShader> mShaders;
    private IShader mVertexShader;
    private IShader mFragmentShader;
    private IShader mGeometryShader;

    public Effect(IShader... shaders) {
        mShaders = new HashMap<>();

        for (IShader shader : shaders) {
            put(shader);
        }
    }

    public void put(IShader shader) throws UnsupportedOperationException {
        mShaders.put(shader.getType(), shader);
        updateShaderCache(shader.getType(), shader);
    }

    public void remove(ShaderType shaderType) {
        mShaders.remove(shaderType);
        updateShaderCache(shaderType, null);
    }

    private void updateShaderCache(ShaderType shaderType, IShader shader) {
        if (shaderType.equals(ShaderType.VERTEX_SHADER)) {
            mVertexShader = shader;
        } else if (shaderType.equals(ShaderType.FRAGMENT_SHADER)) {
            mFragmentShader = shader;
        } else if (shaderType.equals(ShaderType.GEOMETRY_SHADER)) {
            mGeometryShader = shader;
        } else {
            throw new UnsupportedOperationException();
        }
    }

    public IShader get(ShaderType shaderType) throws ShaderNotSetException {
        IShader shader = mShaders.get(shaderType);
        if (shader == null) {
            throw new ShaderNotSetException();
        }

        return shader;
    }

    public IShader getVertexShader() throws ShaderNotSetException {
        if (mVertexShader == null) {
            throw new ShaderNotSetException();
        }

        return mVertexShader;
    }

    public IShader getFragmentShader() throws ShaderNotSetException {
        if (mFragmentShader == null) {
            throw new ShaderNotSetException();
        }

        return mFragmentShader;
    }

    public IShader getGeometryShader() throws ShaderNotSetException {
        if (mGeometryShader == null) {
            throw new ShaderNotSetException();
        }

        return mGeometryShader;
    }
}
