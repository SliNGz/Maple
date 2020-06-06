package com.maple.renderer;

import com.maple.graphics.OpenGLType;
import com.maple.graphics.buffer.BufferBinder;
import com.maple.graphics.buffer.exceptions.NoBoundIndexBufferException;
import com.maple.graphics.buffer.exceptions.NoBoundVertexArrayException;
import com.maple.graphics.buffer.index.IndexBuffer;
import com.maple.graphics.buffer.vertex.VertexArray;
import com.maple.graphics.shader.IShader;
import com.maple.graphics.shader.effect.Effect;
import com.maple.graphics.shader.effect.EffectBinder;
import com.maple.graphics.shader.uniform.ShaderUniformController;
import com.maple.graphics.texture.Texture2D;
import com.maple.graphics.texture.Texture2DBinder;
import com.maple.graphics.texture.exceptions.NoBoundTextureException;
import com.maple.log.Logger;
import com.maple.math.Matrix4f;
import com.maple.renderer.blending.BlendingController;
import com.maple.renderer.cull.CullingController;
import com.maple.renderer.exceptions.RenderingFailedException;
import com.maple.renderer.mesh.Mesh;
import com.maple.utils.Color;

import static org.lwjgl.opengl.GL11.*;

public class Renderer {
    private static final String UNIFORM_MVP = "u_MVP";

    private DepthTestController mDepthTestController;
    private CullingController mCullingController;
    private RendererBufferClearer mBufferClearer;
    private EffectBinder mEffectBinder;
    private BufferBinder mBufferBinder;
    private Texture2DBinder mTextureBinder;
    private BlendingController mBlendingController;

    private Effect mEffect;
    private Matrix4f mMVP;

    public Renderer(DepthTestController depthTestController, CullingController cullingController,
                    RendererBufferClearer bufferClearer, EffectBinder effectBinder,
                    BufferBinder bufferBinder, Texture2DBinder textureBinder,
                    BlendingController blendingController) {
        mDepthTestController = depthTestController;
        mCullingController = cullingController;
        mBufferClearer = bufferClearer;
        mEffectBinder = effectBinder;
        mBufferBinder = bufferBinder;
        mTextureBinder = textureBinder;
        mBlendingController = blendingController;

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

    public BlendingController getBlendingController() {
        return mBlendingController;
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

    public void bindTexture(Texture2D texture) {
        mTextureBinder.bind(texture);
    }

    public void unbindTexture() {
        mTextureBinder.unbind();
    }

    public Texture2D getBoundTexture() throws NoBoundTextureException {
        return mTextureBinder.getBoundTexture();
    }

    public void render() {
        try {
            try {
                mEffectBinder.bind(mEffect);
                IShader vertexShader = mEffect.getVertexShader();
                ShaderUniformController shaderUniformController = vertexShader.getUniformController();
                shaderUniformController.setMatrix4f(UNIFORM_MVP, mMVP);

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
