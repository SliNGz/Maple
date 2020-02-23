package com.maple.input;

import com.maple.graphics.window.Window;

import java.util.HashSet;
import java.util.Set;

public class InputModeCallbackDispatcher {
    private static Set<IInputModeCallback> mCallbacks = new HashSet<>();

    public static void addCallback(IInputModeCallback callback) {
        mCallbacks.add(callback);
    }

    public static void removeCallback(IInputModeCallback callback) {
        mCallbacks.remove(callback);
    }

    public static void dispatch(Window window, int mode, int value) {
        for (IInputModeCallback callback : mCallbacks) {
            callback.invoke(window, mode, value);
        }
    }
}
