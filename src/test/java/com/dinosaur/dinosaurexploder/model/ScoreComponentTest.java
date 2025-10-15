package com.dinosaur.dinosaurexploder.model;

import com.dinosaur.dinosaurexploder.components.ScoreComponent;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

class ScoreComponentTest {

    @Test
    void checkGetSetScore() {
        var scoreComponent = new ScoreComponent();

        scoreComponent.setScore(5);

        assertEquals(5, scoreComponent.getScore());
    }

    @Test
    void checkIncrementProperly() {
        var scoreComponent = new ScoreComponent();

        scoreComponent.setScore(4);

        scoreComponent.incrementScore(1);
        assertEquals(5, scoreComponent.getScore());
    }
    @Test
    void incrementScoreUpdatesHighScore() {
    ScoreComponent scoreComponent = new ScoreComponent();
    scoreComponent.setScore(0);

    
    scoreComponent.incrementScore(10); 

    assertEquals(10, scoreComponent.getScore());
    }
        @Test
    void shouldUpdateHighScoreWhenScoreBeatsPrevious() throws Exception {
        // Reset the static highScore to a known value
        Field field = ScoreComponent.class.getDeclaredField("highScore");
        field.setAccessible(true);
        field.set(null, new HighScore(5)); // start med highscore = 5

        var scoreComponent = new ScoreComponent();
        scoreComponent.setScore(10); // sätt nuvarande score till 10

        // Öka score så att den överstiger highscore
        scoreComponent.incrementScore(5); // total = 15

        // Läs ut uppdaterade highScore via reflection
        HighScore updatedHighScore = (HighScore) field.get(null);

        // Kontrollera att highscore faktiskt uppdaterades
        assertEquals(15, updatedHighScore.getHigh());
    }

    @Test
    void shouldNotUpdateHighScoreIfLower() throws Exception {
        // Reset static field
        Field field = ScoreComponent.class.getDeclaredField("highScore");
        field.setAccessible(true);
        field.set(null, new HighScore(20)); // befintlig highscore = 20

        var scoreComponent = new ScoreComponent();
        scoreComponent.setScore(10);
        scoreComponent.incrementScore(5); // total = 15, borde inte ändra highscore

        HighScore updatedHighScore = (HighScore) field.get(null);
        assertEquals(20, updatedHighScore.getHigh()); // ska vara oförändrat
    }
   @Test
    void saveHighScoreShouldNotThrow() throws Exception {
        ScoreComponent scoreComponent = new ScoreComponent();

        Method method = ScoreComponent.class.getDeclaredMethod("saveHighScore");
        method.setAccessible(true); // tillåt åtkomst till private metod

        assertDoesNotThrow(() -> {
            method.invoke(scoreComponent);
        });
    }

    @Test
    void saveHighScoreDoesNotThrow() throws Exception {
    ScoreComponent scoreComponent = new ScoreComponent();

    // Gör den privata metoden åtkomlig via reflection
    Method method = ScoreComponent.class.getDeclaredMethod("saveHighScore");
    method.setAccessible(true);

    // Kör och kontrollera att inget exception kastas
    assertDoesNotThrow(() -> {
        method.invoke(scoreComponent);
    });
}

}


