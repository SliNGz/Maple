package com.maple.graphics.window;

import com.maple.graphics.window.exceptions.WindowCreationFailedException;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class WindowHelper {
    public static Window create(WindowProperties properties) throws WindowCreationFailedException {
        long handle = glfwCreateWindow(properties.getWidth(), properties.getHeight(), properties.getTitle(), NULL, NULL);
        if (handle == NULL) {
            throw new WindowCreationFailedException();
        }

        return new Window(handle, properties);
    }

    public static void makeContextCurrent(Window window) {
        glfwMakeContextCurrent(window.getHandle());
    }

    public static void destroy(Window window) {
        glfwDestroyWindow(window.getHandle());
    }
}
