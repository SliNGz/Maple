package com.maple.renderer.options;

import com.maple.graphics.shader.VertexShader;
import com.maple.graphics.shader.effect.Effect;
import com.maple.graphics.shader.effect.EffectBinder;
import com.maple.graphics.shader.exceptions.ShaderNotSetException;
import com.maple.renderer.camera.ICamera;

public class RenderOptionsBinder {
    private EffectBinder mEffectBinder;

    private boolean mRenderOptionsBound;

    public RenderOptionsBinder(EffectBinder effectBinder) {
        mEffectBinder = effectBinder;
        mRenderOptionsBound = false;
    }

    public void bind(RenderOptions renderOptions) {
        mEffectBinder.bind(renderOptions.getEffect());
        bindCamera(renderOptions);
        mRenderOptionsBound = true;
    }

    public void unbind() {
        mEffectBinder.unbind();
        mRenderOptionsBound = false;
    }

    public boolean renderOptionsBound() {
        return mRenderOptionsBound;
    }

    private void bindCamera(RenderOptions renderOptions) {
        try {
            ICamera camera = renderOptions.getCamera();
            Effect effect = renderOptions.getEffect();
            VertexShader vertexShader = effect.getVertexShader();

            vertexShader.setMVP(camera.getViewProjectionMatrix());
            vertexShader.setTransform(renderOptions.getTransform());
        } catch (ShaderNotSetException ignored) {
        }
    }

    public EffectBinder getEffectBinder() {
        return mEffectBinder;
    }
}
