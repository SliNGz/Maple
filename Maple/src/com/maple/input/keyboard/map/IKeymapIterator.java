package com.maple.input.keyboard.map;

import com.maple.input.keyboard.IKeyAction;

public interface IKeymapIterator {
    void invoke(int key, IKeyAction keyAction);
}
