package com.maple.graphics;

import com.maple.graphics.buffer.index.IndexBuffer;
import com.maple.graphics.buffer.vertex.VertexArray;
import com.maple.graphics.shader.Shader;
import com.maple.graphics.shader.ShaderCreator;
import com.maple.graphics.shader.ShaderType;
import com.maple.graphics.shader.exceptions.ShaderCreationFailedException;
import com.maple.graphics.shader.program.ProgramPipeline;
import com.maple.graphics.shader.program.ShaderProgram;

import static org.lwjgl.opengl.GL41.*;

public class GLHelper {
    public static ProgramPipeline createPipeline() {
        int[] pipelines = new int[1];
        glGenProgramPipelines(pipelines);

        return new ProgramPipeline(pipelines[0]);
    }

    public static void bindPipeline(ProgramPipeline pipeline) {
        glBindProgramPipeline(pipeline.getHandle());
    }

    public static void deletePipeline(ProgramPipeline pipeline) {
        glDeleteProgramPipelines(pipeline.getHandle());
    }

    public static Shader createShader(ShaderType shaderType, String shaderSource) throws ShaderCreationFailedException {
        return ShaderCreator.create(shaderType, shaderSource);
    }

    public static void destroyShader(Shader shader) {
        ShaderProgram shaderProgram = shader.getProgram();
        glDeleteProgram(shaderProgram.getHandle());
    }

    public static void useProgramStages(ProgramPipeline pipeline, int stages, ShaderProgram program) {
        glUseProgramStages(pipeline.getHandle(), stages, program.getHandle());
    }

    public static void bindVertexArray(VertexArray vertexArray) {
        glBindVertexArray(vertexArray.getHandle());
    }

    public static void unbindVertexArray() {
        glBindVertexArray(0);
    }

    public static void bindIndexBuffer(IndexBuffer indexBuffer) {
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indexBuffer.getHandle());
    }

    public static void unbindIndexBuffer() {
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
    }
}
