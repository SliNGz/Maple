package com.maple.input.mouse;

import com.maple.graphics.GLFWHelper;
import com.maple.graphics.window.Window;
import com.maple.input.IInputModeCallback;
import com.maple.math.Vector2f;
import org.lwjgl.glfw.GLFWCursorPosCallbackI;

import java.util.HashSet;
import java.util.Set;

import static org.lwjgl.glfw.GLFW.*;

public class MousePositionCallbackDispatcher implements IMousePositionCallbackDispatcher, IInputModeCallback, GLFWCursorPosCallbackI {
    private Window mWindow;
    private int mCursorMode;

    private Set<IMousePositionCallback> mNormalCursorCallbacks;
    private Set<IMousePositionCallback> mDisabledCursorCallbacks;
    private Set<IMousePositionCallback> mCurrentCursorCallbacks;

    public MousePositionCallbackDispatcher(Window window) {
        mWindow = window;
        mCursorMode = GLFW_CURSOR_NORMAL;

        mNormalCursorCallbacks = new HashSet<>();
        mDisabledCursorCallbacks = new HashSet<>();
        mCurrentCursorCallbacks = mNormalCursorCallbacks;
    }

    public void addNormalCursorCallback(IMousePositionCallback mousePositionCallback) {
        mNormalCursorCallbacks.add(mousePositionCallback);
    }

    public void removeNormalCursorCallback(IMousePositionCallback mousePositionCallback) {
        mNormalCursorCallbacks.remove(mousePositionCallback);
    }

    public void addDisabledCursorCallback(IMousePositionCallback mousePositionCallback) {
        mDisabledCursorCallbacks.add(mousePositionCallback);
    }

    public void removeDisabledCursorCallback(IMousePositionCallback mousePositionCallback) {
        mDisabledCursorCallbacks.remove(mousePositionCallback);
    }

    @Override
    public void invoke(Window window, int mode, int value) {
        if (mode == GLFW_CURSOR) {
            mCursorMode = value;

            switch (mCursorMode) {
                case GLFW_CURSOR_NORMAL:
                case GLFW_CURSOR_HIDDEN:
                    mCurrentCursorCallbacks = mNormalCursorCallbacks;
                    break;
                case GLFW_CURSOR_DISABLED:
                    mCurrentCursorCallbacks = mDisabledCursorCallbacks;
                    break;
            }
        }
    }

    @Override
    public void invoke(long window, double xpos, double ypos) {
        for (IMousePositionCallback mousePositionCallback : mCurrentCursorCallbacks) {
            mousePositionCallback.invoke((float) xpos, (float) ypos);
        }

        if (mCursorMode == GLFW_CURSOR_DISABLED) {
            GLFWHelper.setCursorPosition(mWindow, new Vector2f(0, 0));
        }
    }
}
