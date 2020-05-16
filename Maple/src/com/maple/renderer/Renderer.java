package com.maple.renderer;

import com.maple.graphics.OpenGLType;
import com.maple.graphics.buffer.BufferBinder;
import com.maple.graphics.buffer.exceptions.NoBoundIndexBufferException;
import com.maple.graphics.buffer.exceptions.NoBoundVertexArrayException;
import com.maple.graphics.buffer.index.IndexBuffer;
import com.maple.graphics.buffer.vertex.VertexArray;
import com.maple.log.Logger;
import com.maple.renderer.cull.CullingController;
import com.maple.renderer.exceptions.NoBoundRenderOptionsException;
import com.maple.renderer.exceptions.RenderingFailedException;
import com.maple.renderer.mesh.Mesh;
import com.maple.renderer.options.RenderOptions;
import com.maple.renderer.options.RenderOptionsBinder;
import com.maple.utils.Color;

import static org.lwjgl.opengl.GL11.*;

public class Renderer {
    private DepthTestController mDepthTestController;
    private CullingController mCullingController;
    private RendererBufferClearer mBufferClearer;
    private BufferBinder mBufferBinder;
    private RenderOptionsBinder mRenderOptionsBinder;

    public Renderer(DepthTestController depthTestController, CullingController cullingController,
                    RendererBufferClearer bufferClearer, BufferBinder bufferBinder,
                    RenderOptionsBinder renderOptionsBinder) {
        mDepthTestController = depthTestController;
        mCullingController = cullingController;
        mBufferClearer = bufferClearer;
        mBufferBinder = bufferBinder;
        mRenderOptionsBinder = renderOptionsBinder;
    }

    public DepthTestController getDepthTestController() {
        return mDepthTestController;
    }

    public CullingController getCullingController() {
        return mCullingController;
    }

    public RenderOptionsBinder getRenderOptionsBinder() {
        return mRenderOptionsBinder;
    }

    public void enableClearColorBuffer() {
        mBufferClearer.enableColorBufferBit();
    }

    public void disableClearColorBuffer() {
        mBufferClearer.disableColorBufferBit();
    }

    public void enableClearDepthBuffer() {
        mBufferClearer.enableDepthBufferBit();
    }

    public void disableClearDepthBuffer() {
        mBufferClearer.disableColorBufferBit();
    }

    public void setClearColor(Color color) {
        mBufferClearer.setColor(color);
    }

    public void clear() {
        mBufferClearer.clear();
    }

    public void clear(Color color) {
        mBufferClearer.clear(color);
    }

    public void bindVertexArray(VertexArray vertexArray) {
        mBufferBinder.bindVertexArray(vertexArray);
    }

    public void unbindVertexArray() {
        mBufferBinder.unbindVertexArray();
    }

    public void bindIndexBuffer(IndexBuffer indexBuffer) {
        mBufferBinder.bindIndexBuffer(indexBuffer);
    }

    public void unbindIndexBuffer() {
        mBufferBinder.unbindIndexBuffer();
    }

    public void bindMesh(Mesh mesh) {
        mBufferBinder.bindVertexArray(mesh.getVertexArray());
        mBufferBinder.bindIndexBuffer(mesh.getIndexBuffer());
    }

    public void unbindMesh() {
        mBufferBinder.unbindVertexArray();
        mBufferBinder.unbindIndexBuffer();
    }

    public void bindRenderOptions(RenderOptions renderOptions) {
        mRenderOptionsBinder.bind(renderOptions);
    }

    public void unbindRenderOptions() {
        mRenderOptionsBinder.unbind();
    }

    public void render() {
        try {
            try {
                if (!mRenderOptionsBinder.renderOptionsBound()) {
                    Logger.errorCore("RENDERING_FAILED_NO_BOUND_RENDER_OPTIONS");
                    throw new NoBoundRenderOptionsException();
                }

                VertexArray vertexArray = mBufferBinder.getBoundVertexArray();
                try {
                    IndexBuffer indexBuffer = mBufferBinder.getBoundIndexBuffer();
                    OpenGLType indexBufferType = indexBuffer.getType();
                    glDrawElements(GL_TRIANGLES, indexBuffer.getCount(), indexBufferType.getValue(), 0);
                } catch (NoBoundIndexBufferException e) {
                    glDrawArrays(GL_TRIANGLES, 0, vertexArray.getVerticesCount());
                }
            } catch (NoBoundVertexArrayException e) {
                Logger.errorCore("RENDERING_FAILED_NO_BOUND_VERTEX_ARRAY");
                throw e;
            }
        } catch (Throwable throwable) {
            throw new RenderingFailedException(throwable);
        }
    }
}
