package com.dinosaur.dinosaurexploder.model;

import com.almasb.fxgl.entity.components.ViewComponent;
import com.dinosaur.dinosaurexploder.components.ScoreComponent;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.*;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;





class ScoreComponentTest {
    
    private ScoreComponent scoreComponent = new ScoreComponent();

    @Test
    void checkGetSetScore() {
        var scoreComponent = new ScoreComponent();

        scoreComponent.setScore(5);

        assertEquals(5, scoreComponent.getScore());
    }


    @Test
    void checkIncrementProperly() {
        scoreComponent.setScore(4);
        scoreComponent.incrementScore(1);
        assertEquals(5, scoreComponent.getScore());
    }

    //Min kod
    @Test
    void shouldLoadExistingHighScoreFile() throws Exception {
        // Skapa en fil med samma namn som GameConstants.HIGH_SCORE_FILE
        File f = new File("highscore.dat");
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(f))) {
            out.writeObject(new HighScore(99));
        }

        // Anropa loadHighScore() via reflection
        Method m = ScoreComponent.class.getDeclaredMethod("loadHighScore");
        m.setAccessible(true);
        m.invoke(scoreComponent);

        assertTrue(f.exists());
        f.delete(); // städa upp
    }

    @Test
    void shouldCreateNewHighScoreWhenFileMissing() throws Exception {
        // Ta bort eventuell gammal highscore-fil
        File f = new File("highscore.dat");
        if (f.exists()) f.delete();

        Method m = ScoreComponent.class.getDeclaredMethod("loadHighScore");
        m.setAccessible(true);
        m.invoke(scoreComponent);

        // Inget undantag ska kastas trots att filen saknas
        assertFalse(f.exists());
    }

    @Test
    void shouldHandleIOExceptionWhenSavingHighScore() throws Exception {
        // Skapa en undermapp som inte går att skriva till (för att trigga IOException)
        File folder = new File("forbidden");
        folder.mkdir();
        folder.setReadOnly();

        // Temporärt skapa en låst filväg i samma namn som GameConstants.HIGH_SCORE_FILE
        File lockedFile = new File(folder, "highscore.dat");
        lockedFile.createNewFile();
        lockedFile.setReadOnly();

        Method m = ScoreComponent.class.getDeclaredMethod("saveHighScore");
        m.setAccessible(true);

        assertDoesNotThrow(() -> m.invoke(scoreComponent));

        // Städa
        lockedFile.setWritable(true);
        lockedFile.delete();
        folder.setWritable(true);
        folder.delete();
    }
  
}
    


