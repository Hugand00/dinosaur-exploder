package com.dinosaur.dinosaurexploder.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameDataSimpleTest {

    @Test
    void testSetAndGetSelectedShip() {
        // Standardlyckad path (vi antar att default ship 1 alltid är unlocked)
        GameData.setSelectedShip(1);
        assertEquals(1, GameData.getSelectedShip());
    }

    @Test
    void testSetAndGetSelectedWeapon() {
        // Standardlyckad path (vi antar att default weapon 1 alltid är unlocked)
        GameData.setSelectedWeapon(1);
        assertEquals(1, GameData.getSelectedWeapon());
    }

        @Test
    void testCheckUnlockedShip() {
        // Vi vet att default ship 1 är unlocked
        assertTrue(GameData.checkUnlockedShip(1));
    }

    @Test
    void testCheckUnlockedWeapon() {
        // Vi vet att default weapon 1 är unlocked
        assertTrue(GameData.checkUnlockedWeapon(1));
    }

     @Test
    void testGetHighScoreDefault() {
        // Vi vet att default highScore är 0 om filen inte finns
        int highScore = GameData.getHighScore();
        assertTrue(highScore >= 0); // minst 0
    }

    @Test
    void testGetTotalCoinsDefault() {
        // Vi vet att default totalCoins är 0 om filen inte finns
        int totalCoins = GameData.getTotalCoins();
        assertTrue(totalCoins >= 0); // minst 0
    }
}
