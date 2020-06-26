package com.maple.entity;

import com.maple.entity.component.IComponent;

public interface IEntityManager {
    <T extends IComponent> IEntity createEntity(IComponent... components);

    IEntity getEntity(int id);

    void destroyEntity(IEntity entity);
}
