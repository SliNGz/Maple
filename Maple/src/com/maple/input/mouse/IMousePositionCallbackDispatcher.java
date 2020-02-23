package com.maple.input.mouse;

public interface IMousePositionCallbackDispatcher {
    void addNormalCursorCallback(IMousePositionCallback mousePositionCallback);

    void removeNormalCursorCallback(IMousePositionCallback mousePositionCallback);

    void addDisabledCursorCallback(IMousePositionCallback mousePositionCallback);

    void removeDisabledCursorCallback(IMousePositionCallback mousePositionCallback);
}
