package com.dinosaur.dinosaurexploder.controller;

import com.dinosaur.dinosaurexploder.components.HealthbarComponent;
import com.dinosaur.dinosaurexploder.components.RedDinoComponent;
import com.dinosaur.dinosaurexploder.components.OrangeDinoComponent;
import com.dinosaur.dinosaurexploder.model.Settings;
import com.dinosaur.dinosaurexploder.utils.LevelManager;
import com.almasb.fxgl.entity.Entity;
import javafx.geometry.Point2D;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static com.almasb.fxgl.dsl.FXGL.spawn;
import static org.junit.jupiter.api.Assertions.*;

class BossSpawnerTest {

    private BossSpawner spawner;
    private Settings settings;
    private LevelManager levelManager;

    // Mockade entiteter och komponenter
    private Entity redDinoMock;
    private Entity orangeDinoMock;
    private Entity healthBarMock;
    private RedDinoComponent redCompMock;
    private OrangeDinoComponent orangeCompMock;
    private HealthbarComponent healthCompMock;

    @BeforeEach
    void setUp() {
        settings = new Settings();
        levelManager = new LevelManager();

        // Skapa BossSpawner som "spy" så vi kan mocka spawn-metoder
        spawner = spy(new BossSpawner(settings, levelManager));

        // Mocka entiteter
        redDinoMock = mock(Entity.class);
        orangeDinoMock = mock(Entity.class);
        healthBarMock = mock(Entity.class);

        // Mocka komponenter
        redCompMock = mock(RedDinoComponent.class);
        orangeCompMock = mock(OrangeDinoComponent.class);
        healthCompMock = mock(HealthbarComponent.class);

        // Mocka komponent-hämtning
        when(redDinoMock.getComponent(RedDinoComponent.class)).thenReturn(redCompMock);
        when(orangeDinoMock.getComponent(OrangeDinoComponent.class)).thenReturn(orangeCompMock);
        when(healthBarMock.getComponent(HealthbarComponent.class)).thenReturn(healthCompMock);

        // Mocka spawn-metoden så vi slipper FXGL
        doReturn(redDinoMock).when(spawner).spawn(eq("redDino"), anyDouble(), anyDouble());
        doReturn(orangeDinoMock).when(spawner).spawn(eq("orangeDino"), anyDouble(), anyDouble());
        doReturn(healthBarMock).when(spawner).spawn(eq("healthBar"), anyDouble(), anyDouble());

        // Mocka positionsmetoder
        doReturn(new Point2D(400, 300)).when(spawner).getAppCenter();
        doReturn(800.0).when(spawner).getAppWidth();
    }

    @Test
    void testSpawnRedBoss() {
        spawner.spawnNewBoss("red");

        // Verifiera att rätt entiteter spawnades
        verify(spawner).spawn("redDino", 355, 50); // 400 - 45
        verify(spawner).spawn("healthBar", 585, 15); // 800 - 215

        // Verifiera att komponenterna kopplades
        verify(redCompMock).setLevelManager(levelManager);
        verify(healthCompMock).setDinoComponent(redCompMock);
    }

    @Test
    void testSpawnOrangeBoss() {
        spawner.spawnNewBoss("orange");

        verify(spawner).spawn("orangeDino", 355, 50);
        verify(spawner).spawn("healthBar", 585, 15);

        verify(orangeCompMock).setLevelManager(levelManager);
        verify(healthCompMock).setDinoComponent(orangeCompMock);
    }
    @Test
    void testHealthbar(){
        spawner.spawnNewBoss("red");

        spawner.updateHealthBar();

        verify(healthCompMock).updateBar();
    }
    @Test
    void testRemoveBossEntities(){
        spawner.spawnNewBoss("red");
        spawner.spawnNewBoss("orange");

        spawner.removeBossEntities();

        verify(redDinoMock).removeFromWorld();
        verify(healthBarMock).removeFromWorld();
        verify(orangeDinoMock).removeFromWorld();
    }
    // @Test
    // void testRemoveNoBossEntities(){
           
    //     spawner.removeBossEntities();

   
    //     verifyNoInteractions(redDinoMock, orangeDinoMock, healthBarMock);
    // }
}