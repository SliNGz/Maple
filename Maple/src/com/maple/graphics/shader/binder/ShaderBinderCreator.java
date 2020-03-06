package com.maple.graphics.shader.binder;

import com.maple.graphics.shader.program.ProgramPipeline;

import static org.lwjgl.opengl.GL41.*;

public class ShaderBinderCreator {
    public ShaderBinder create() {
        int pipeline = glGenProgramPipelines();
        glBindProgramPipeline(pipeline);
        ProgramPipeline programPipeline = new ProgramPipeline(pipeline);

        return new ShaderBinder(programPipeline);
    }

    public void destroy(ShaderBinder shaderBinder) {
        ProgramPipeline pipeline = shaderBinder.getPipeline();
        glDeleteProgramPipelines(pipeline.getHandle());
    }
}
