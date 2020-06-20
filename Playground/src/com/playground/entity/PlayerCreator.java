package com.playground.entity;

import com.maple.entity.IEntityManager;
import com.maple.entity.component.PositionComponent;
import com.maple.entity.component.RotationComponent;

public class PlayerCreator {
    private IEntityManager mEntityManager;

    public PlayerCreator(IEntityManager entityManager) {
        mEntityManager = entityManager;
    }

    public Player create() {
        return new Player(mEntityManager.createEntity(new PositionComponent(), new RotationComponent()));
    }
}
