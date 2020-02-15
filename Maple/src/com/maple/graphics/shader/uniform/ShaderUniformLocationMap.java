package com.maple.graphics.shader.uniform;

import com.maple.graphics.shader.program.ShaderProgram;
import com.maple.log.Logger;

import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL20.glGetUniformLocation;

public class ShaderUniformLocationMap {
    private static final int LOCATION_NOT_FOUND = -1;

    private ShaderProgram mProgram;
    private Map<String, Integer> mMap;

    public ShaderUniformLocationMap(ShaderProgram program) {
        mProgram = program;
        mMap = new HashMap<>();
    }

    public int get(String uniformName) {
        Integer location = mMap.get(uniformName);
        if (location == null) {
            location = glGetUniformLocation(mProgram.getHandle(), uniformName);
            if (location != LOCATION_NOT_FOUND) {
                mMap.put(uniformName, location);
            } else {
                Logger.warnCore(String.format("UNIFORM_LOCATION_NOT_FOUND PROGRAM:%d UNIFORM:%s",
                                              mProgram.getHandle(), uniformName));
            }
        }

        return location;
    }
}
