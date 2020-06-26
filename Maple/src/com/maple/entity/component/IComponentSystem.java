package com.maple.entity.component;

import com.maple.entity.IEntity;

public interface IComponentSystem<T extends IComponent> {
    void add(IEntity entity, T component);

    void remove(IEntity entity);
}
