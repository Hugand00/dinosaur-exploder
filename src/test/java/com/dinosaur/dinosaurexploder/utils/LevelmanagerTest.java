package com.dinosaur.dinosaurexploder.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

// import java.util.logging.Level;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import net.bytebuddy.implementation.Implementation;

public class LevelmanagerTest {
    private LevelManager levelManager;

    @BeforeEach
    void setUp(){
        levelManager = new LevelManager();
    }

    @Test
    @DisplayName("should return correct level, starting level 1")
    void shouldReturnCorrectLevel(){
        int correctLevel = 1;

        int currentLevel = levelManager.getCurrentLevel();

        assertEquals(correctLevel, currentLevel);
    }
    
    @Test
    @DisplayName("should return correct enemy spawn rate, rate 0.75")
    void shouldReturnCorrectEnemySpawnRate(){
        double correctSpanRate = 0.75;

        double currentSpanrate = levelManager.getEnemySpawnRate();

        assertEquals(correctSpanRate, currentSpanrate);
    }
    @Test
    @DisplayName("should return correct enemy speed, speed 1.5")
    void shouldReturnCorrectEnemySpeed(){
        double correctSpeed = 1.5;

        double currentSpeed = levelManager.getEnemySpeed();

        assertEquals(correctSpeed, currentSpeed);
    }
    @Test
    @DisplayName("should return correct level progress")
    void shouldReturnCorrectLevelProgress(){
        int enemiesToDefeat = 5;
        int defeatedEnemies = 0;
        float progressLevel = defeatedEnemies/enemiesToDefeat;

        double currentProgressLevel = levelManager.getLevelProgress();

        assertEquals(progressLevel, currentProgressLevel);
    }

    @Test
    @DisplayName("should increment defeated enemies correctly")
    void shouldIncrementDefeatedEnemiesCorrectly(){
        levelManager.incrementDefeatedEnemies();

        assertEquals(1, levelManager.getLevelProgress() * levelManager.getEnemiesToDefeat());
    }
     @Test
    @DisplayName("should return true that level should advance")
    void ShouldReturnTrueThatLevelShouldAdvance(){
        for(int i = 0; i < 5; i++){
             levelManager.incrementDefeatedEnemies();
        }
       
        assertTrue(levelManager.shouldAdvanceLevel());
    }
      @Test
    @DisplayName("should return false that level should advance")
    void ShouldReturnFalsehatLevelShouldAdvance(){
        for(int i = 0; i < 3; i++){
             levelManager.incrementDefeatedEnemies();
        }
       
        assertFalse(levelManager.shouldAdvanceLevel());
    }
      @Test
    @DisplayName("should return false that level should advance")
    void ShouldAdvanceToNextLevel(){
        int start = levelManager.getCurrentLevel();
       
        levelManager.nextLevel();
        int after = levelManager.getCurrentLevel();

        assertEquals(start+1, after);
    }
      @Test
    @DisplayName("should return false that level should advance")
    void ShouldReturnEnemiesToDefeat(){
        int correctEnemiesToDefeat = 5;
        
        int enemiesToDefeat = levelManager.getEnemiesToDefeat();

        assertEquals(correctEnemiesToDefeat, enemiesToDefeat);
    }
    
   

}
