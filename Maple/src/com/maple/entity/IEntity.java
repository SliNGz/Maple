package com.maple.entity;

import com.maple.entity.component.IComponent;

import java.util.Map;

public interface IEntity {
    int getId();

    Map<Class<? extends IComponent>, IComponent> getComponents();

    <T extends IComponent> T getComponent(Class<T> componentType);
}
