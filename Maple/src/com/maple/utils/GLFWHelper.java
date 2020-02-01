package com.maple.utils;

import com.maple.utils.exceptions.GLFWInitializationFailedException;

import static org.lwjgl.glfw.GLFW.glfwInit;

public class GLFWHelper {
    public static void initialize() throws GLFWInitializationFailedException {
        boolean initialized = glfwInit();
        if (!initialized) {
            throw new GLFWInitializationFailedException();
        }
    }
}
