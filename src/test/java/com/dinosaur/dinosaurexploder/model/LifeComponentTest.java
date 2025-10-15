package com.dinosaur.dinosaurexploder.model;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.components.ViewComponent;
import com.dinosaur.dinosaurexploder.components.LifeComponent;
import com.dinosaur.dinosaurexploder.utils.LanguageManager;

import javafx.application.Platform;

class LifeComponentTest {

    private LifeComponent lifeComponent;
    private LanguageManager languageManagerMock;

     @BeforeAll
    static void initFX() {
        Platform.startup(() -> {});
    }

     @BeforeEach
    void setUp() {
        languageManagerMock = mock(LanguageManager.class);
        when(languageManagerMock.getTranslation("lives")).thenReturn("Lives");
        when(languageManagerMock.selectedLanguageProperty())
                .thenReturn(new javafx.beans.property.SimpleStringProperty("en"));

        lifeComponent = new LifeComponent();

        try {
            var langField = LifeComponent.class.getDeclaredField("languageManager");
            langField.setAccessible(true);
            langField.set(lifeComponent, languageManagerMock);
        } catch (Exception e) {
            throw new RuntimeException("Could not inject languageManager", e);
        }

        var entityMock = mock(Entity.class);
        var viewMock = mock(ViewComponent.class);
        when(entityMock.getViewComponent()).thenReturn(viewMock);

        lifeComponent.setEntityForTest(entityMock);
    }

 @Test
    void testOnAdded_ShouldInitializesUIElementsCorrectly() {
        Platform.runLater(() -> {

            lifeComponent.onAdded();

            assertNotNull(lifeComponent.getLifeText(), "lifeText should be initialized");
            assertEquals("Lives", lifeComponent.getLifeText().getText(), "Text should be correctly translated");
            assertNotNull(lifeComponent.getHeart1(), "heart1 should be initialized");
            assertNotNull(lifeComponent.getHeart2(), "heart2 should be initialized");
            assertNotNull(lifeComponent.getHeart3(), "heart3 should be initialized");
        });
    }
 @Test
    void testUpdateTest_ShouldUpdateTextWithCurrentLives(){
        Platform.runLater(() -> {

            lifeComponent.onAdded();
            lifeComponent.setLifeForTest(2);

             when(languageManagerMock.getTranslation("lives")).thenReturn("Liv");

             lifeComponent.onUpdate(0);

            assertEquals("Liv: 2", lifeComponent.getLifeText().getText(),
                "Text should show correct language and correct amount of lives");
        });
    }

 @Test
    void testUpdateLifeDisplay_ShouldSetCorrectHeartImages() {
        Platform.runLater(() -> {

            when(languageManagerMock.getTranslation("lives")).thenReturn("Liv");
            lifeComponent.setLifeForTest(2);

            lifeComponent.onAdded();

            assertEquals("Liv: 2", lifeComponent.getLifeText().getText());

            assertSame(lifeComponent.getHeart1().getImage(), lifeComponent.getHeart(), "First heart should be whole");
            assertSame(lifeComponent.getHeart2().getImage(), lifeComponent.getHeart(), "Second heart should be whole");
            assertSame(lifeComponent.getHeart3().getImage(), lifeComponent.getLostHeart(), "Third heart should be broken");
        });
    }
}
