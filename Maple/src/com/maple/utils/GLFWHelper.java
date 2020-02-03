package com.maple.utils;

import com.maple.utils.exceptions.GLFWInitializationFailedException;

import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwTerminate;

public class GLFWHelper {
    public static void initialize() throws GLFWInitializationFailedException {
        boolean initialized = glfwInit();
        if (!initialized) {
            throw new GLFWInitializationFailedException();
        }
    }

    public static void terminate() {
        glfwTerminate();
    }
}
