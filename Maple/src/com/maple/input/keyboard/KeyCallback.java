package com.maple.input.keyboard;

import com.maple.log.Logger;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallbackI;

public class KeyCallback implements GLFWKeyCallbackI {
    private KeyboardState mKeyboardState;

    public KeyCallback(KeyboardState keyboardState) {
        mKeyboardState = keyboardState;
    }

    @Override
    public void invoke(long window, int key, int scancode, int action, int mods) {
        mKeyboardState.setKeyModifiersMask(mods);

        if (key != GLFW.GLFW_KEY_UNKNOWN) {
            KeyState keyState = getKeyState(action);
            mKeyboardState.setKeyState(key, keyState);
        }
    }

    private KeyState getKeyState(int action) {
        if (action == GLFW.GLFW_RELEASE) {
            return KeyState.UP;
        }

        return KeyState.DOWN;
    }
}
