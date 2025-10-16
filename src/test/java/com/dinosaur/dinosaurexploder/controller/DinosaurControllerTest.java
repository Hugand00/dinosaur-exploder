package com.dinosaur.dinosaurexploder.controller;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;



import com.dinosaur.dinosaurexploder.controller.core.CollisionRegistry;
import com.dinosaur.dinosaurexploder.controller.core.GameActions;
import com.dinosaur.dinosaurexploder.controller.core.GameInitializer;

@ExtendWith(MockitoExtension.class)
public class DinosaurControllerTest {

    @Mock
    private GameInitializer gameInitializer;

    @Mock
    private CollisionRegistry collisionRegistry;

    @Mock
    private GameActions gameActions;

  
    private DinosaurController dinosaurController;

     @BeforeEach
    void setUp() throws Exception {
        dinosaurController = spy(new DinosaurController());

        // Mocka så att createGameActions returnerar din mockade GameActions
        doReturn(gameActions).when(dinosaurController).createGameActions(any());

        // Använd reflection för att sätta mockade fält
        setPrivateField(dinosaurController, "gameInitializer", gameInitializer);
        setPrivateField(dinosaurController, "collisionRegistry", collisionRegistry);
    }
     private void setPrivateField(Object target, String fieldName, Object value) throws Exception {
        var field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }

@Test
    void verifyInitGameAndPhysics() {
        doNothing().when(gameInitializer).initGame();

        dinosaurController.initGame();
        dinosaurController.initPhysics();

        verify(gameInitializer).initGame();
        verify(collisionRegistry).registerAll();
    }

    @Test
    void verifyInitGameDoesNotCrash() {
        doNothing().when(gameInitializer).initGame();

        dinosaurController.initGame();

        verify(gameInitializer).initGame();
    }

    @Test
    void verifyInitPhysicsRunsWithoutFXGLEngine() {
        dinosaurController.initGame(); // Skapar mockade GameActions
        assertDoesNotThrow(() -> dinosaurController.initPhysics());
    }
}

