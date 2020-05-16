package com.maple.renderer.options;

import com.maple.graphics.shader.binder.ShaderBinder;
import com.maple.graphics.shader.binder.ShaderBinderCreator;
import com.maple.graphics.shader.effect.EffectBinder;

public class RenderOptionsBinderCreator {
    private ShaderBinderCreator mShaderBinderCreator;

    public RenderOptionsBinderCreator(ShaderBinderCreator shaderBinderCreator) {
        mShaderBinderCreator = shaderBinderCreator;
    }

    public RenderOptionsBinder create() {
        ShaderBinder shaderBinder = mShaderBinderCreator.create();
        EffectBinder effectBinder = new EffectBinder(shaderBinder);

        return new RenderOptionsBinder(effectBinder);
    }

    public void destroy(RenderOptionsBinder renderOptionsBinder) {
        EffectBinder effectBinder = renderOptionsBinder.getEffectBinder();
        mShaderBinderCreator.destroy(effectBinder.getShaderBinder());
    }
}
