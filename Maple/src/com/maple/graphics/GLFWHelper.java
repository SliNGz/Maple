package com.maple.graphics;

import com.maple.graphics.exceptions.GLFWInitializationFailedException;
import com.maple.graphics.exceptions.MonitorRetrievalFailedException;
import com.maple.graphics.exceptions.VideoModeRetrievalFailedException;
import com.maple.graphics.exceptions.WindowCreationFailedException;
import com.maple.graphics.monitor.Monitor;
import com.maple.graphics.window.Window;
import com.maple.graphics.window.WindowCreationProperties;
import com.maple.input.InputModeCallbackDispatcher;
import com.maple.math.Vector2f;
import org.lwjgl.glfw.*;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
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

    public static void freeErrorCallback() {
        GLFWErrorCallback glfwErrorCallback = glfwSetErrorCallback(null);
        if (glfwErrorCallback != null) {
            glfwErrorCallback.free();
        }
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

    public static Window createWindow(WindowCreationProperties creationProperties) throws WindowCreationFailedException {
        int x = 0;
        int y = 0;
        int width = creationProperties.getWidth();
        int height = creationProperties.getHeight();
        String title = creationProperties.getTitle();

        long handle = glfwCreateWindow(width, height, title, NULL, NULL);
        if (handle == NULL) {
            throw new WindowCreationFailedException();
        }
        glfwSetWindowPos(handle, x, y);

        return new Window(handle, x, y, width, height, title);
    }

    public static void destroyWindow(Window window) {
        glfwDestroyWindow(window.getHandle());
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

    public static void setWindowKeyCallback(Window window, GLFWKeyCallbackI keyCallback) {
        glfwSetKeyCallback(window.getHandle(), keyCallback);
    }

    public static void setWindowCursorPositionCallback(Window window, GLFWCursorPosCallbackI cursorPosCallback) {
        glfwSetCursorPosCallback(window.getHandle(), cursorPosCallback);
    }

    public static void setWindowMouseButtonCallback(Window window, GLFWMouseButtonCallbackI mouseButtonCallback) {
        glfwSetMouseButtonCallback(window.getHandle(), mouseButtonCallback);
    }

    public static Vector2f getCursorPosition(Window window) {
        double[] xBuffer = new double[1];
        double[] yBuffer = new double[1];
        glfwGetCursorPos(window.getHandle(), xBuffer, yBuffer);

        return new Vector2f((float) xBuffer[0], (float) yBuffer[0]);
    }

    public static void setCursorPosition(Window window, Vector2f position) {
        glfwSetCursorPos(window.getHandle(), position.X, position.Y);
    }

    public static int getWindowInputMode(Window window, int mode) {
        return glfwGetInputMode(window.getHandle(), mode);
    }

    public static void setWindowInputMode(Window window, int mode, int value) {
        InputModeCallbackDispatcher.dispatch(window, mode, value);
        glfwSetInputMode(window.getHandle(), mode, value);
    }

    public static void freeCallbacks(Window window) {
        glfwFreeCallbacks(window.getHandle());
    }
}
