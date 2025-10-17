package com.dinosaur.dinosaurexploder.model;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.concurrent.CountDownLatch;

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
import javafx.scene.text.Text;

class LifeComponentTest {

    private LifeComponent lifeComponent;
    private LanguageManager languageManagerMock;

     @BeforeAll
    static void initFX() {
       try {
            Platform.startup(() -> {});
        } catch (IllegalStateException e) {
            // FX already started
        }
    }

     @BeforeEach
    void setUp() throws Exception{
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
    private static void runAndWait(Runnable action) {
    if (Platform.isFxApplicationThread()) {
        action.run();
    } else {
        final CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            try {
                action.run();
            } finally {
                latch.countDown();
            }
        });
        try {
            latch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }
}


 @Test
    void testOnAdded_ShouldInitializesUIElementsCorrectly() throws Exception {
       
        runAndWait(() -> {

                lifeComponent.onAdded();
    
                assertNotNull(lifeComponent.getLifeText(), "lifeText should be initialized");
                assertEquals("Lives", lifeComponent.getLifeText().getText(), "Text should be correctly translated");
                assertNotNull(lifeComponent.getHeart1(), "heart1 should be initialized");
                assertNotNull(lifeComponent.getHeart2(), "heart2 should be initialized");
                assertNotNull(lifeComponent.getHeart3(), "heart3 should be initialized");
        });
     
    }
 @Test
    void testUpdateTest_ShouldUpdateTextWithCurrentLives() throws Exception{
        runAndWait(() -> {

                lifeComponent.onAdded();
                for (int lives = 0; lives <= 3; lives++) {
                    lifeComponent.setLifeForTest(lives);
                    
                    when(languageManagerMock.getTranslation("lives")).thenReturn("Liv");
                    
                    lifeComponent.onUpdate(0);
                    
                   assertEquals("Liv: " + lives, lifeComponent.getLifeText().getText(),
                    "Text should show correct language and correct amount of lives");
                }
        });
    }
    @Test
    void testUpdateLifeDisplay_OnInitialization_ShouldSetCorrectHeartImages() throws Exception{
        runAndWait(() -> {
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
    void testUpdateLifeDisplay_ShouldReflectLifeChangesDynamically() throws Exception{
          runAndWait(() ->{
                when(languageManagerMock.getTranslation("lives")).thenReturn("Liv");
                lifeComponent.onAdded();

                Image fullHeart = lifeComponent.getHeart();
                Image lostHeart = lifeComponent.getLostHeart();

                lifeComponent.setLifeForTest(3);
                lifeComponent.updateLifeDisplayForTest();
                assertEquals("Liv: 3", lifeComponent.getLifeText().getText());
                assertSame(fullHeart, lifeComponent.getHeart1().getImage());
                assertSame(fullHeart, lifeComponent.getHeart2().getImage());
                assertSame(fullHeart, lifeComponent.getHeart3().getImage());

                lifeComponent.setLifeForTest(1);
                lifeComponent.updateLifeDisplayForTest();
                assertEquals("Liv: 1", lifeComponent.getLifeText().getText());
                assertSame(fullHeart, lifeComponent.getHeart1().getImage());
                assertSame(lostHeart, lifeComponent.getHeart2().getImage());
                assertSame(lostHeart, lifeComponent.getHeart3().getImage());

                lifeComponent.setLifeForTest(0);
                lifeComponent.updateLifeDisplayForTest();
                assertEquals("Liv: 0", lifeComponent.getLifeText().getText());
                assertSame(lostHeart, lifeComponent.getHeart1().getImage());
                assertSame(lostHeart, lifeComponent.getHeart2().getImage());
                assertSame(lostHeart, lifeComponent.getHeart3().getImage());

          });
    }
  @Test
    void testLanguageChange_ShouldUpdateText() throws Exception{
        runAndWait(() -> {
                lifeComponent.onAdded();
                lifeComponent.setLifeForTest(2);
    
                when(languageManagerMock.getTranslation("lives")).thenReturn("Liv");
    
                StringProperty langProp = languageManagerMock.selectedLanguageProperty();
                langProp.set("sv"); 
    
                assertEquals("Liv: 2", lifeComponent.getLifeText().getText());
            
        });
    }

  @Test
    void testClearEntity_ShouldRemoveAllChildrenFromView() throws Exception {
        runAndWait(() -> {
                var entityMock = mock(Entity.class);
                var viewMock = mock(ViewComponent.class);

                when(entityMock.getViewComponent()).thenReturn(viewMock);
                lifeComponent.setEntityForTest(entityMock);

                lifeComponent.clearEntity();

                verify(viewMock, times(1)).clearChildren();
            
        });

    }
  @Test
    void testGetters_ShouldNeverReturnNullAfterOnAdded() throws Exception {
         runAndWait(() -> {
        
                lifeComponent.onAdded();

                Text lifeText = lifeComponent.getLifeText();
                assertNotNull(lifeText, "lifeText should not be null");
                assertEquals("Liv", lifeText.getText(), "lifeText should have correct initial text");

                Image fullHeart = lifeComponent.getHeart();
                Image lostHeart = lifeComponent.getLostHeart();

                assertSame(fullHeart, lifeComponent.getHeart1().getImage(), "Heart1 image mismatch");
                assertSame(fullHeart, lifeComponent.getHeart2().getImage(), "Heart2 image mismatch");
                assertSame(fullHeart, lifeComponent.getHeart3().getImage(), "Heart3 image mismatch");

                assertNotNull(fullHeart, "Heart image should not be null");
                assertNotNull(lostHeart, "LostHeart image should not be null");
          
        });
    }


}
