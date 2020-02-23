package com.maple.input;

import com.maple.graphics.window.Window;

public interface IInputModeCallback {
    void invoke(Window window, int mode, int value);
}
