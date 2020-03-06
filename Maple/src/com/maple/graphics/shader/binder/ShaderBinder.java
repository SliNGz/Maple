package com.maple.graphics.shader.binder;

import com.maple.graphics.shader.Shader;
import com.maple.graphics.shader.ShaderType;
import com.maple.graphics.shader.exceptions.NoBoundShaderException;
import com.maple.graphics.shader.program.ProgramPipeline;
import com.maple.graphics.shader.program.ShaderProgram;

import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL41.glUseProgramStages;

public class ShaderBinder implements IShaderBinder {
    private final ProgramPipeline mPipeline;
    private Map<ShaderType, Shader> mBoundShaders;

    public ShaderBinder(ProgramPipeline pipeline) {
        mPipeline = pipeline;
        mBoundShaders = new HashMap<>();
    }

    public void bind(Shader shader) {
        ShaderType shaderType = shader.getType();
        ShaderProgram shaderProgram = shader.getProgram();
        glUseProgramStages(mPipeline.getHandle(), shaderType.getStageBit(), shaderProgram.getHandle());
        mBoundShaders.put(shaderType, shader);
    }

    public void unbind(ShaderType shaderType) {
        glUseProgramStages(mPipeline.getHandle(), shaderType.getStageBit(), 0);
        mBoundShaders.put(shaderType, null);
    }

    public ProgramPipeline getPipeline() {
        return mPipeline;
    }

    public Shader getBoundShader(ShaderType shaderType) throws NoBoundShaderException {
        Shader shader = mBoundShaders.get(shaderType);
        if (shader == null) {
            throw new NoBoundShaderException();
        }

        return shader;
    }
}
