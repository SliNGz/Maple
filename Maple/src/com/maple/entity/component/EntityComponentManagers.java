package com.maple.entity.component;

import com.maple.entity.IEntity;
import com.maple.entity.component.exceptions.MissingComponentManagerException;

import java.util.HashMap;
import java.util.Map;

public class EntityComponentManagers {
    private Map<Class<?>, IEntityComponentManager<?>> mComponentManagers;

    public EntityComponentManagers() {
        mComponentManagers = new HashMap<>();
        mComponentManagers.put(PositionComponent.class, new PositionComponentManager());
        mComponentManagers.put(RotationComponent.class, new RotationComponentManager());
    }

    public IEntityComponentManager<?> get(Class<?> componentType) {
        IEntityComponentManager<?> componentManager = mComponentManagers.get(componentType);
        if (componentManager == null) {
            throw new MissingComponentManagerException("COMPONENT_TYPE: " + componentType.getName());
        }

        return componentManager;
    }

    public <T> void addEntity(IEntity entity) {
        Map<Class<?>, ?> components = entity.getComponents();
        for (Map.Entry<Class<?>, ?> entry : components.entrySet()) {
            Class<?> componentType = entry.getKey();
            IEntityComponentManager<T> componentManager = (IEntityComponentManager<T>) get(componentType);

            componentManager.add(entity, (T) entry.getValue());
        }
    }

    public <T> void removeEntity(IEntity entity) {
        Map<Class<?>, ?> components = entity.getComponents();
        for (Map.Entry<Class<?>, ?> entry : components.entrySet()) {
            Class<?> componentType = entry.getKey();
            IEntityComponentManager<?> componentManager = get(componentType);

            componentManager.remove(entity);
        }
    }

    public void update() {
        for (IEntityComponentManager<?> componentManager : mComponentManagers.values()) {
            componentManager.update();
        }
    }
}
