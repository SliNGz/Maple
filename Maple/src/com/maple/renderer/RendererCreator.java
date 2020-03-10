package com.maple.renderer;

import com.maple.graphics.buffer.BufferBinder;
import com.maple.graphics.shader.binder.ShaderBinder;
import com.maple.graphics.shader.binder.ShaderBinderCreator;
import com.maple.renderer.camera.CameraStub;
import com.maple.renderer.camera.ICamera;
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
        ShaderBinder shaderBinder = mShaderBinderCreator.create();
        BufferBinder bufferBinder = new BufferBinder();
        ICamera camera = new CameraStub();

        depthTestController.enable();
        cullingController.enable();
        cullingController.setFaceCulling(CullingFace.FRONT);
        cullingController.setMode(CullingMode.CLOCKWISE);

        bufferClearer.enableColorBufferBit();
        bufferClearer.enableDepthBufferBit();

        return new Renderer(depthTestController, cullingController, bufferClearer, shaderBinder, bufferBinder, camera);
    }

    public void destroy(Renderer renderer) {
        mShaderBinderCreator.destroy(renderer.getShaderBinder());
    }
}
