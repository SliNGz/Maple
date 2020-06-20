package com.maple.entity;

import java.util.Map;

public interface IEntity {
    int getId();

    Map<Class<?>, ?> getComponents();

    <T> T getComponent(Class<T> componentType);
}
