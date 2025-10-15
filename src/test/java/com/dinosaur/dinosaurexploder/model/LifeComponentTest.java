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
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;

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
            for (int lives = 0; lives <= 3; lives++) {
                lifeComponent.setLifeForTest(lives);
                
                when(languageManagerMock.getTranslation("lives")).thenReturn("Liv");
                
                lifeComponent.onUpdate(0);
                
                assertEquals("Liv:" + lives, lifeComponent.getLifeText().getText(),
                "Text should show correct language and correct amount of lives");
            }
        });
    }
    @Test
    void testUpdateLifeDisplay_ShouldSetCorrectHeartImages() {
        Platform.runLater(() -> {
            when(languageManagerMock.getTranslation("lives")).thenReturn("Liv");

            for (int lives = 0; lives <= 3; lives++) {
                lifeComponent.setLifeForTest(lives);
                lifeComponent.onAdded(); 

                assertEquals("Liv: " + lives, lifeComponent.getLifeText().getText());

                Image fullHeart = lifeComponent.getHeart();
                Image lostHeart = lifeComponent.getLostHeart();

                
                assertSame(lives >= 1 ? fullHeart : lostHeart, lifeComponent.getHeart1().getImage(),
                        "Heart1 mismatch for life=" + lives);
            
                assertSame(lives >= 2 ? fullHeart : lostHeart, lifeComponent.getHeart2().getImage(),
                        "Heart2 mismatch for life=" + lives);
         
                assertSame(lives >= 3 ? fullHeart : lostHeart, lifeComponent.getHeart3().getImage(),
                        "Heart3 mismatch for life=" + lives);
            }
        });
    }
  @Test
    void testLanguageChange_ShouldUpdateText() {
        Platform.runLater(() -> {
            lifeComponent.onAdded();
            lifeComponent.setLifeForTest(2);

            when(languageManagerMock.getTranslation("lives")).thenReturn("Liv");

            StringProperty langProp = languageManagerMock.selectedLanguageProperty();
            langProp.set("sv"); 

            assertEquals("Liv: 2", lifeComponent.getLifeText().getText());
        });
    }
}
