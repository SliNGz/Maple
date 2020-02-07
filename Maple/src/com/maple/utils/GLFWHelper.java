package com.maple.utils;

import com.maple.graphics.monitor.Monitor;
import com.maple.graphics.window.Window;
import com.maple.graphics.window.WindowProperties;
import com.maple.graphics.window.exceptions.WindowCreationFailedException;
import com.maple.utils.exceptions.GLFWInitializationFailedException;
import com.maple.utils.exceptions.MonitorRetrievalFailedException;
import com.maple.utils.exceptions.VideoModeRetrievalFailedException;
import org.lwjgl.glfw.GLFWVidMode;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.NULL;

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

    public static Monitor getPrimaryMonitor() throws MonitorRetrievalFailedException {
        long handle = glfwGetPrimaryMonitor();
        if (handle == NULL) {
            throw new MonitorRetrievalFailedException();
        }

        return new Monitor(handle);
    }

    public static GLFWVidMode getVideoMode(Monitor monitor) throws VideoModeRetrievalFailedException {
        GLFWVidMode videoMode = glfwGetVideoMode(monitor.getHandle());
        if (videoMode == null) {
            throw new VideoModeRetrievalFailedException();
        }

        return videoMode;
    }

    public static Window createWindow(WindowProperties properties) throws WindowCreationFailedException {
        long handle = glfwCreateWindow(properties.getWidth(), properties.getHeight(), properties.getTitle(), NULL, NULL);
        if (handle == NULL) {
            throw new WindowCreationFailedException();
        }

        return new Window(handle, properties);
    }

    public static void setWindowPosition(Window window, int x, int y) {
        glfwSetWindowPos(window.getHandle(), x, y);
    }

    public static void setWindowSize(Window window, int width, int height) {
        glfwSetWindowSize(window.getHandle(), width, height);
    }

    public static void setWindowWidth(Window window, int width) {
        setWindowSize(window, width, window.getHeight());
    }

    public static void setWindowHeight(Window window, int height) {
        setWindowSize(window, window.getWidth(), height);
    }

    public static boolean shouldCloseWindow(Window window) {
        return glfwWindowShouldClose(window.getHandle());
    }

    public static void setShouldCloseWindow(Window window, boolean value) {
        glfwSetWindowShouldClose(window.getHandle(), value);
    }

    public static void showWindow(Window window) {
        glfwShowWindow(window.getHandle());
    }

    public static void makeContextCurrent(Window window) {
        glfwMakeContextCurrent(window.getHandle());
    }

    public static void swapBuffers(Window window) {
        glfwSwapBuffers(window.getHandle());
    }

    public static void destroyWindow(Window window) {
        glfwDestroyWindow(window.getHandle());
    }
}
