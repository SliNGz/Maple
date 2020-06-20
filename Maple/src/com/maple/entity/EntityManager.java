package com.maple.entity;

import com.maple.entity.component.EntityComponentManagers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EntityManager implements IEntityManager {
    private int mNextEntityId;
    private List<IEntity> mEntities;

    private EntityComponentManagers mEntityComponentManagers;

    public EntityManager(EntityComponentManagers entityComponentManagers) {
        mNextEntityId = 0;
        mEntities = new ArrayList<>();
        mEntityComponentManagers = entityComponentManagers;
    }

    @SafeVarargs
    public final <T> IEntity createEntity(T... components) {
        Map<Class<?>, T> componentsMap = new HashMap<>();
        for (T component : components) {
            Class<?> componentType = component.getClass();
            componentsMap.put(componentType, component);
        }

        IEntity entity = new Entity(mNextEntityId++, componentsMap);
        mEntities.add(entity);
        mEntityComponentManagers.addEntity(entity);

        return entity;
    }

    public void destroyEntity(IEntity entity) {
        mEntities.remove(entity);
        mEntityComponentManagers.removeEntity(entity);
    }

    public void update() {
        mEntityComponentManagers.update();
    }

    public void cleanup() {
        List<IEntity> entitiesClone = new ArrayList<>(mEntities);
        for (IEntity entity : entitiesClone) {
            destroyEntity(entity);
        }
    }
}
