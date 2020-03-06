package com.maple.renderer;

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
import com.maple.renderer.camera.CameraStub;
import com.maple.renderer.camera.ICamera;

import static org.lwjgl.opengl.GL11.*;

public class Renderer {
    private ShaderBinder mShaderBinder;
    private BufferBinder mBufferBinder;
    private ICamera mCamera;

    public Renderer(ShaderBinder shaderBinder, BufferBinder bufferBinder) {
        mShaderBinder = shaderBinder;
        mBufferBinder = bufferBinder;
        mCamera = new CameraStub();
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

    private void updateViewProjectionUniform(Shader shader) {
        ShaderUniformController shaderUniformController = shader.getUniformController();
        shaderUniformController.setMatrix4f(Shader.MVP_UNIFORM_NAME, mCamera.getViewProjectionMatrix());
    }

    public void render() {
        try {
            VertexArray vertexArray = mBufferBinder.getBoundVertexArray();

            try {
                IndexBuffer indexBuffer = mBufferBinder.getBoundIndexBuffer();
                glDrawElements(GL_TRIANGLES, indexBuffer.getCount(), indexBuffer.getType(), 0);
            } catch (NoBoundIndexBufferException e) {
                glDrawArrays(GL_TRIANGLES, 0, vertexArray.getCount());
            }
        } catch (NoBoundVertexArrayException e) {
            Logger.warnCore("RENDERING_FAILED_NO_BOUND_VERTEX_ARRAY");
        }
    }
}
