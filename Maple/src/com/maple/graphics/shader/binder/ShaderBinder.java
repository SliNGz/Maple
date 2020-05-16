package com.maple.graphics.shader.binder;

import com.maple.graphics.shader.IShader;
import com.maple.graphics.shader.ShaderType;
import com.maple.graphics.shader.program.ProgramPipeline;
import com.maple.graphics.shader.program.ShaderProgram;

import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL41.GL_ALL_SHADER_BITS;
import static org.lwjgl.opengl.GL41.glUseProgramStages;

public class ShaderBinder {
    private final ProgramPipeline mPipeline;
    private Map<ShaderType, IShader> mBoundShaders;

    public ShaderBinder(ProgramPipeline pipeline) {
        mPipeline = pipeline;
        mBoundShaders = new HashMap<>();
    }

    public void bind(IShader shader) {
        ShaderType shaderType = shader.getType();

        IShader boundShader = mBoundShaders.get(shaderType);
        if (shader != boundShader) {
            ShaderProgram shaderProgram = shader.getProgram();
            glUseProgramStages(mPipeline.getHandle(), shaderType.getStageBit(), shaderProgram.getHandle());
            mBoundShaders.put(shaderType, shader);
        }
    }

    public void unbind(ShaderType shaderType) {
        IShader boundShader = mBoundShaders.get(shaderType);
        if (boundShader != null) {
            glUseProgramStages(mPipeline.getHandle(), shaderType.getStageBit(), 0);
            mBoundShaders.put(shaderType, null);
        }
    }

    public void unbindAll() {
        glUseProgramStages(mPipeline.getHandle(), GL_ALL_SHADER_BITS, 0);
    }

    public ProgramPipeline getPipeline() {
        return mPipeline;
    }
}
