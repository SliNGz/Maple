package com.maple.entity;

import com.maple.entity.component.IComponent;
import com.maple.entity.component.system.ComponentSystemsManager;
import com.maple.entity.exceptions.EntityNotFoundException;
import com.maple.game.runner.GameTime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EntityManager implements IEntityManager {
    private int mNextEntityId;
    private List<IEntity> mEntities;

    private ComponentSystemsManager mComponentSystemsManager;

    public EntityManager(ComponentSystemsManager componentSystemsManager) {
        mNextEntityId = 0;
        mEntities = new ArrayList<>();
        mComponentSystemsManager = componentSystemsManager;
    }

    public final IEntity createEntity(IComponent... components) {
        Map<Class<? extends IComponent>, IComponent> componentsMap = new HashMap<>();
        for (IComponent component : components) {
            Class<? extends IComponent> componentType = component.getClass();
            componentsMap.put(componentType, component);
        }

        IEntity entity = new Entity(mNextEntityId++, componentsMap);
        mEntities.add(entity);
        mComponentSystemsManager.addEntity(entity);

        return entity;
    }

    public IEntity getEntity(int id) {
        IEntity entity = mEntities.get(id);
        if (entity == null) {
            throw new EntityNotFoundException();
        }

        return entity;
    }

    public void destroyEntity(IEntity entity) {
        mEntities.remove(entity);
        mComponentSystemsManager.removeEntity(entity);
    }

    public void update(GameTime gameTime) {
        mComponentSystemsManager.update(gameTime);
    }

    public void cleanup() {
        List<IEntity> entitiesClone = new ArrayList<>(mEntities);
        for (IEntity entity : entitiesClone) {
            destroyEntity(entity);
        }
    }
}
