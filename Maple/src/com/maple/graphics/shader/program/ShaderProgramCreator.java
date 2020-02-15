package com.maple.graphics.shader.program;

import com.maple.graphics.shader.exceptions.ShaderProgramCreationFailedException;
import com.maple.graphics.shader.exceptions.ShaderProgramLinkageFailedException;
import com.maple.log.Logger;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL41.GL_PROGRAM_SEPARABLE;
import static org.lwjgl.opengl.GL41.glProgramParameteri;

public class ShaderProgramCreator {
    private static final int PROGRAM_CREATION_ERROR = 0;

    public static ShaderProgram create(int shader) throws ShaderProgramCreationFailedException {
        int program = createProgram();

        try {
            glProgramParameteri(program, GL_PROGRAM_SEPARABLE, GL_TRUE);
            glAttachShader(program, shader);
            linkProgram(program);
            glDetachShader(program, shader);

            return new ShaderProgram(program);
        } catch (ShaderProgramLinkageFailedException e) {
            glDeleteProgram(program);
            throw new ShaderProgramCreationFailedException();
        }
    }

    private static int createProgram() throws ShaderProgramCreationFailedException {
        int program = glCreateProgram();
        if (program == PROGRAM_CREATION_ERROR) {
            throw new ShaderProgramCreationFailedException();
        }

        return program;
    }

    private static void linkProgram(int program) throws ShaderProgramLinkageFailedException {
        glLinkProgram(program);

        int[] linkStatusBuffer = new int[1];
        glGetProgramiv(program, GL_LINK_STATUS, linkStatusBuffer);

        int linkStatus = linkStatusBuffer[0];
        if (linkStatus == GL_FALSE) {
            int[] maxLengthBuffer = new int[1];
            glGetProgramiv(program, GL_INFO_LOG_LENGTH, maxLengthBuffer);

            int maxLength = maxLengthBuffer[0];
            String log = glGetProgramInfoLog(program, maxLength);
            Logger.errorCore("SHADER_PROGRAM_LINKAGE_FAILED\n" + log);

            throw new ShaderProgramLinkageFailedException();
        }
    }
}
