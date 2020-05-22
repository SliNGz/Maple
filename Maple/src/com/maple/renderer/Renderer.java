package com.maple.renderer;

import com.maple.graphics.OpenGLType;
import com.maple.graphics.buffer.BufferBinder;
import com.maple.graphics.buffer.exceptions.NoBoundIndexBufferException;
import com.maple.graphics.buffer.exceptions.NoBoundVertexArrayException;
import com.maple.graphics.buffer.index.IndexBuffer;
import com.maple.graphics.buffer.vertex.VertexArray;
import com.maple.graphics.shader.VertexShader;
import com.maple.graphics.shader.effect.Effect;
import com.maple.graphics.shader.effect.EffectBinder;
import com.maple.log.Logger;
import com.maple.math.Matrix4f;
import com.maple.renderer.cull.CullingController;
import com.maple.renderer.exceptions.RenderingFailedException;
import com.maple.renderer.mesh.Mesh;
import com.maple.utils.Color;

import static org.lwjgl.opengl.GL11.*;

public class Renderer {
    private DepthTestController mDepthTestController;
    private CullingController mCullingController;
    private RendererBufferClearer mBufferClearer;
    private EffectBinder mEffectBinder;
    private BufferBinder mBufferBinder;

    private Effect mEffect;
    private Matrix4f mMVP;

    public Renderer(DepthTestController depthTestController, CullingController cullingController,
                    RendererBufferClearer bufferClearer, EffectBinder effectBinder,
                    BufferBinder bufferBinder) {
        mDepthTestController = depthTestController;
        mCullingController = cullingController;
        mBufferClearer = bufferClearer;
        mEffectBinder = effectBinder;
        mBufferBinder = bufferBinder;

        mEffect = new Effect();
        mMVP = Matrix4f.createIdentity();
    }

    public DepthTestController getDepthTestController() {
        return mDepthTestController;
    }

    public CullingController getCullingController() {
        return mCullingController;
    }

    public EffectBinder getEffectBinder() {
        return mEffectBinder;
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

    public void setEffect(Effect effect) {
        mEffect = effect;
    }

    public void setMVP(Matrix4f mvp) {
        mMVP = mvp;
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

    public void render() {
        try {
            try {
                mEffectBinder.bind(mEffect);
                VertexShader vertexShader = mEffect.getVertexShader();
                vertexShader.setMVP(mMVP);

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
