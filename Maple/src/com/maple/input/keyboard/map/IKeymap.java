package com.maple.input.keyboard.map;

import com.maple.input.keyboard.IKeyAction;
import com.maple.input.keyboard.Key;

public interface IKeymap {
    void add(Key key, IKeyAction keyAction);
}
