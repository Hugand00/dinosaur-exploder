package com.dinosaur.dinosaurexploder.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.concurrent.CountDownLatch;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.components.ViewComponent;
import com.dinosaur.dinosaurexploder.components.CollectedCoinsComponent;
import com.dinosaur.dinosaurexploder.components.LifeComponent;
import com.dinosaur.dinosaurexploder.utils.LanguageManager;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class CollectedCoinsComponentTest {

    private CollectedCoinsComponent coinsComponent;
    private LanguageManager languageManagerMock;

      @BeforeAll
    static void initFX() {
        try {
            Platform.startup(() -> {});
        } catch (IllegalStateException e) {
            // JavaFX already initialized
        }
    }

    @BeforeEach
    void setUp() throws Exception {
    languageManagerMock = mock(LanguageManager.class);
    when(languageManagerMock.getTranslation("coin")).thenReturn("Coins");

    coinsComponent = new CollectedCoinsComponent();

    var langField = CollectedCoinsComponent.class.getDeclaredField("languageManager");
    langField.setAccessible(true);
    langField.set(coinsComponent, languageManagerMock);

    var entityMock = mock(Entity.class);
    var viewMock = mock(ViewComponent.class);
    when(entityMock.getViewComponent()).thenReturn(viewMock);

    coinsComponent.setEntityForTest(entityMock);
}

     @Test
    void testOnAdded_ShouldInitializeUIElementsCorrectly() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            try {
                coinsComponent.onAdded();

                Text coinText = coinsComponent.getCoinTextForTest();
                Node coinUI = coinsComponent.getCoinUIForTest();
                assertNotNull(coinText, "coinText should be initialized");
                assertTrue(coinText.getText().contains("Coins"), "coinText should contain translation");
                assertNotNull(coinUI, "coinUI should be initialized");
                assertTrue(coinUI instanceof HBox, "coinUI should be an HBox");
            } finally {
                latch.countDown();
            }
        });
        latch.await();
    }
    @Test
    void testIncrementCoin_ShouldIncreaseCoinCountAndUpdateText() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            try {
                coinsComponent.onAdded();

                int before = coinsComponent.getCoin();
                coinsComponent.incrementCoin();

                int after = coinsComponent.getCoin();
                assertEquals(before + 1, after, "Coin count should increment by 1");

                Text coinText = coinsComponent.getCoinTextForTest();
                assertTrue(coinText.getText().contains(String.valueOf(after)),
                        "Coin text should reflect new coin count");
            } finally {
                latch.countDown();
            }
        });
        latch.await();
    }
    @Test
    void testOnUpdate_ShouldCallUpdateText() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            try {
                coinsComponent.onAdded();
                coinsComponent.incrementCoin();

                Text coinText = coinsComponent.getCoinTextForTest();
                String before = coinText.getText();

                coinsComponent.onUpdate(0.016);

                String after = coinText.getText();
                assertNotNull(after);
                assertEquals(before, after, "onUpdate should refresh the same coin text safely");
            } finally {
                latch.countDown();
            }
        });
        latch.await();
    }

}
