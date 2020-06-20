package com.maple.entity.component;

import com.maple.entity.IEntity;

public interface IEntityComponentManager<T> {
    void add(IEntity entity, T component);

    T get(IEntity entity);

    void remove(IEntity entity);

    void update();
}
