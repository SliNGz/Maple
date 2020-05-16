package com.maple.renderer;

import com.maple.graphics.buffer.BufferBinder;
import com.maple.renderer.cull.CullingController;
import com.maple.renderer.cull.CullingFace;
import com.maple.renderer.cull.CullingMode;
import com.maple.renderer.options.RenderOptionsBinder;
import com.maple.renderer.options.RenderOptionsBinderCreator;

public class RendererCreator {
    private RenderOptionsBinderCreator mRenderOptionsBinderCreator;

    public RendererCreator(RenderOptionsBinderCreator renderOptionsBinderCreator) {
        mRenderOptionsBinderCreator = renderOptionsBinderCreator;
    }

    public Renderer create() {
        DepthTestController depthTestController = new DepthTestController();
        CullingController cullingController = new CullingController();
        RendererBufferClearer bufferClearer = new RendererBufferClearer();
        BufferBinder bufferBinder = new BufferBinder();
        RenderOptionsBinder renderOptionsBinder = mRenderOptionsBinderCreator.create();

        depthTestController.enable();
        cullingController.enable();
        cullingController.setFaceCulling(CullingFace.FRONT);
        cullingController.setMode(CullingMode.CLOCKWISE);

        bufferClearer.enableColorBufferBit();
        bufferClearer.enableDepthBufferBit();

        return new Renderer(depthTestController, cullingController, bufferClearer, bufferBinder, renderOptionsBinder);
    }

    public void destroy(Renderer renderer) {
        mRenderOptionsBinderCreator.destroy(renderer.getRenderOptionsBinder());
    }
}
