package com.maple.renderer.effect;

import com.maple.graphics.shader.Shader;

public class Effect {
    private Shader[] mShaders;

    public Effect(Shader... shaders) {
        mShaders = shaders;
    }
}
