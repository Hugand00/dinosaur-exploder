package com.dinosaur.components;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import com.dinosaur.dinosaurexploder.components.OrangeDinoComponent;
import com.dinosaur.dinosaurexploder.components.PlayerComponent;
import com.dinosaur.dinosaurexploder.utils.GameTimer;
import com.dinosaur.dinosaurexploder.utils.LevelManager;

public class OrangeDinoComponentTest {
    OrangeDinoComponent orangeDinoComponent;

    @Mock
    PlayerComponent playerComponent;

    @Mock
    GameTimer gameTimer;
    
    @Mock
    LevelManager levelManager;

     @Mock
     AudioManager audioManager;

    @BeforeEach
    void setup(){
        orangeDinoComponent = new OrangeDinoComponent(gameTimer, playerComponent);
        levelManager = mock(LevelManager.class);

        
    }
    @SuppressWarnings("unchecked")
private <T> T getPrivateField(Object target, String fieldName, Class<T> fieldType) {
    try {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return (T) field.get(target);
    } catch (Exception e) {
        throw new RuntimeException(e);
    }
}
    
    @Test
    void TesGetLives(){
        int lives = orangeDinoComponent.getLives();

        assertEquals(10, lives);
    }
    @Test
    void TesSetLives(){
        orangeDinoComponent.setLives(20);

        assertEquals(20, orangeDinoComponent.getLives());
    }
    @Test
    void TestGetMoveSpeed(){
        double moveSpeed = orangeDinoComponent.getMovementSpeed();

        assertEquals(1.5, moveSpeed);
    }
    @Test
    void TestSetMoveSpeed(){
        orangeDinoComponent.setMovementSpeed(2.3);

        assertEquals(2.3, orangeDinoComponent.getMovementSpeed());
    }
    @Test
    void TestDamageDino_ThenGetLife(){
        int start = orangeDinoComponent.getLives();

        orangeDinoComponent.damage(1);

        int after = orangeDinoComponent.getLives();

        assertEquals(start-1, after);
    }
    @Test
    void TestIsPaused(){

        orangeDinoComponent.setPaused(true);

        boolean paused = orangeDinoComponent.getPaused();

        assertTrue(paused);
    }
    @Test
    void TestSetLevelManager(){
        orangeDinoComponent.setLevelManager(levelManager);
        LevelManager actual = getPrivateField(orangeDinoComponent, "levelManager", LevelManager.class);
        assertSame(levelManager, actual);
    }
    @Test
    void TestCtor(){
        boolean firstTime = false;
        orangeDinoComponent.onAdded();
        firstTime = getPrivateField(orangeDinoComponent, "firstTime", Boolean.class);

        assertTrue(firstTime);
    }
}
