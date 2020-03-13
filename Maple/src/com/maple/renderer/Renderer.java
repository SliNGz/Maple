package com.maple.renderer;

import com.maple.graphics.OpenGLType;
import com.maple.graphics.buffer.BufferBinder;
import com.maple.graphics.buffer.exceptions.NoBoundIndexBufferException;
import com.maple.graphics.buffer.exceptions.NoBoundVertexArrayException;
import com.maple.graphics.buffer.index.IndexBuffer;
import com.maple.graphics.buffer.vertex.VertexArray;
import com.maple.graphics.shader.Shader;
import com.maple.graphics.shader.ShaderType;
import com.maple.graphics.shader.binder.ShaderBinder;
import com.maple.graphics.shader.exceptions.NoBoundShaderException;
import com.maple.graphics.shader.uniform.ShaderUniformController;
import com.maple.log.Logger;
import com.maple.renderer.camera.ICamera;
import com.maple.renderer.cull.CullingController;
import com.maple.renderer.mesh.Mesh;
import com.maple.utils.Color;

import static org.lwjgl.opengl.GL11.*;

public class Renderer {
    private DepthTestController mDepthTestController;
    private CullingController mCullingController;
    private RendererBufferClearer mBufferClearer;
    private ShaderBinder mShaderBinder;
    private BufferBinder mBufferBinder;
    private ICamera mCamera;

    public Renderer(DepthTestController depthTestController, CullingController cullingController,
                    RendererBufferClearer bufferClearer,
                    ShaderBinder shaderBinder, BufferBinder bufferBinder,
                    ICamera camera) {
        mDepthTestController = depthTestController;
        mCullingController = cullingController;
        mBufferClearer = bufferClearer;
        mShaderBinder = shaderBinder;
        mBufferBinder = bufferBinder;
        mCamera = camera;
    }

    public void render() {
        try {
            VertexArray vertexArray = mBufferBinder.getBoundVertexArray();

            try {
                IndexBuffer indexBuffer = mBufferBinder.getBoundIndexBuffer();
                OpenGLType indexBufferType = indexBuffer.getType();
                glDrawElements(GL_TRIANGLES, indexBuffer.getCount(), indexBufferType.getValue(), 0);
            } catch (NoBoundIndexBufferException e) {
                glDrawArrays(GL_TRIANGLES, 0, vertexArray.getVerticesCount());
            }
        } catch (NoBoundVertexArrayException e) {
            Logger.warnCore("RENDERING_FAILED_NO_BOUND_VERTEX_ARRAY");
        }
    }

    public DepthTestController getDepthTestController() {
        return mDepthTestController;
    }

    public CullingController getCullingController() {
        return mCullingController;
    }

    public ShaderBinder getShaderBinder() {
        return mShaderBinder;
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

    private void updateViewProjectionUniform(Shader shader) {
        ShaderUniformController shaderUniformController = shader.getUniformController();
        shaderUniformController.setMatrix4f(Shader.MVP_UNIFORM_NAME, mCamera.getViewProjectionMatrix());
    }

    public void bindShader(Shader shader) {
        mShaderBinder.bind(shader);
        if (shader.getType() == ShaderType.VERTEX_SHADER) {
            updateViewProjectionUniform(shader);
        }
    }

    public void unbindShader(ShaderType type) {
        mShaderBinder.unbind(type);
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
        bindVertexArray(mesh.getVertexArray());
        bindIndexBuffer(mesh.getIndexBuffer());
    }

    public void unbindMesh() {
        unbindVertexArray();
        unbindIndexBuffer();
    }

    public ICamera getCamera() {
        return mCamera;
    }

    public void setCamera(ICamera camera) {
        mCamera = camera;
        try {
            updateViewProjectionUniform(mShaderBinder.getBoundShader(ShaderType.VERTEX_SHADER));
        } catch (NoBoundShaderException ignored) {
        }
    }
}
