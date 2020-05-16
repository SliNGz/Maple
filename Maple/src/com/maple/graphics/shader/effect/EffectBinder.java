package com.maple.graphics.shader.effect;

import com.maple.graphics.shader.IShader;
import com.maple.graphics.shader.ShaderType;
import com.maple.graphics.shader.binder.ShaderBinder;
import com.maple.graphics.shader.exceptions.ShaderNotSetException;

public class EffectBinder {
    private ShaderBinder mShaderBinder;

    public EffectBinder(ShaderBinder shaderBinder) {
        mShaderBinder = shaderBinder;
    }

    public void bind(Effect effect) {
        for (ShaderType shaderType : ShaderType.SHADER_TYPE_LIST) {
            try {
                IShader shader = effect.get(shaderType);
                mShaderBinder.bind(shader);
            } catch (ShaderNotSetException e) {
                mShaderBinder.unbind(shaderType);
            }
        }
    }

    public void unbind() {
        mShaderBinder.unbindAll();
    }

    public ShaderBinder getShaderBinder() {
        return mShaderBinder;
    }
}
