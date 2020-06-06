package com.maple.renderer;

import com.maple.graphics.buffer.BufferBinder;
import com.maple.graphics.shader.binder.ShaderBinder;
import com.maple.graphics.shader.binder.ShaderBinderCreator;
import com.maple.graphics.shader.effect.EffectBinder;
import com.maple.graphics.texture.Texture2DBinder;
import com.maple.renderer.blending.BlendingController;
import com.maple.renderer.cull.CullingController;
import com.maple.renderer.cull.CullingFace;
import com.maple.renderer.cull.CullingMode;

public class RendererCreator {
    private ShaderBinderCreator mShaderBinderCreator;

    public RendererCreator(ShaderBinderCreator shaderBinderCreator) {
        mShaderBinderCreator = shaderBinderCreator;
    }

    public Renderer create() {
        DepthTestController depthTestController = new DepthTestController();
        CullingController cullingController = new CullingController();
        RendererBufferClearer bufferClearer = new RendererBufferClearer();

        ShaderBinderCreator shaderBinderCreator = new ShaderBinderCreator();
        ShaderBinder shaderBinder = shaderBinderCreator.create();
        EffectBinder effectBinder = new EffectBinder(shaderBinder);

        BufferBinder bufferBinder = new BufferBinder();

        Texture2DBinder textureBinder = new Texture2DBinder();

        BlendingController blendingController = new BlendingController();

        depthTestController.enable();
        cullingController.enable();
        cullingController.setFaceCulling(CullingFace.BACK);
        cullingController.setMode(CullingMode.COUNTER_CLOCKWISE);

        bufferClearer.enableColorBufferBit();
        bufferClearer.enableDepthBufferBit();

        return new Renderer(depthTestController, cullingController, bufferClearer, effectBinder, bufferBinder,
                            textureBinder, blendingController);
    }

    public void destroy(Renderer renderer) {
        EffectBinder effectBinder = renderer.getEffectBinder();
        ShaderBinder shaderBinder = effectBinder.getShaderBinder();
        mShaderBinderCreator.destroy(shaderBinder);
    }
}
