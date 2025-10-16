package com.dinosaur.dinosaurexploder.controller;

import com.almasb.fxgl.dsl.FXGL;
import com.dinosaur.dinosaurexploder.controller.core.CollisionRegistry;
import com.dinosaur.dinosaurexploder.controller.core.GameActions;
import com.dinosaur.dinosaurexploder.controller.core.GameInitializer;
import com.dinosaur.dinosaurexploder.controller.core.collisions.*;
import com.dinosaur.dinosaurexploder.utils.LevelManager;

/**
 * Summary :
 * The Factory handles the Dinosaur , player controls and collision detection of all entities in the game
 */
public class DinosaurController {
    private final GameInitializer gameInitializer;
    private final CollisionRegistry collisionRegistry;
    private GameActions gameActions;

    public DinosaurController() {
        gameInitializer = new GameInitializer();
        collisionRegistry = new CollisionRegistry();
    }
    protected GameActions createGameActions(GameInitializer init) {
    return new GameActions(init);
    }
    public void initGame() {
        gameInitializer.initGame();
        gameActions = createGameActions(gameInitializer);
    }

    public void initInput() {
        gameInitializer.initInput();
    }

    public void initPhysics() {
        if(gameActions == null) {
            throw new IllegalStateException("GameActions must be initialized before initializing physics.");
        }

        // Add all collisions
        collisionRegistry.addCollision(new EnemyProjectilePlayerCollision(gameActions));
        collisionRegistry.addCollision(new PlayerCoinCollision(gameInitializer));
        collisionRegistry.addCollision(new PlayerGreenDinoCollision(gameActions));
        collisionRegistry.addCollision(new PlayerHeartCollision(gameInitializer));
        collisionRegistry.addCollision(new PlayerOrangeDinoCollision(gameActions));
        collisionRegistry.addCollision(new PlayerRedDinoCollision(gameActions));
        collisionRegistry.addCollision(new ProjectileEnemyProjectileCollision());
        collisionRegistry.addCollision(new ProjectileGreenDinoCollision(gameInitializer, gameActions));
        collisionRegistry.addCollision(new ProjectileOrangeDinoCollision(gameInitializer, gameActions));
        collisionRegistry.addCollision(new ProjectileRedDinoCollision(gameInitializer, gameActions));

        collisionRegistry.registerAll();
    }
}