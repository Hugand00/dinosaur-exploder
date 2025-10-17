package com.dinosaur.components;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import com.dinosaur.dinosaurexploder.components.OrangeDinoComponent;
import com.dinosaur.dinosaurexploder.components.PlayerComponent;
import com.dinosaur.dinosaurexploder.utils.GameTimer;

public class OrangeDinoComponentTest {
    OrangeDinoComponent orangeDinoComponent;

    @Mock
    PlayerComponent playerComponent;

    @Mock
    GameTimer gameTimer;

    @BeforeEach
    void setup(){
        orangeDinoComponent = new OrangeDinoComponent(gameTimer, playerComponent);
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
}
