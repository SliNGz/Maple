package com.maple.entity;

public interface IEntityManager {
    <T> IEntity createEntity(T... components);

    void destroyEntity(IEntity entity);
}
