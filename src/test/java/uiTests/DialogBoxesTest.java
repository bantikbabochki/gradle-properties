package uiTests;

import base.BaseUITest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.junit.jupiter.api.Assertions.*;

public class DialogBoxesTest extends BaseUITest {
    @BeforeEach
    void openPage() {
        driver.get(config.getDialogBoxesUrl());
    }

    @Test
    void launchAlertAcceptanceTest() {
        //find alert button
        WebElement lAlert = driver.findElement(By.id("my-alert"));
        //open alert
        lAlert.click();
        //waiting alert
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        //check text
        assertEquals("Hello world!", alert.getText(), "Текст алерта не совпадает");
        //agreement
        alert.accept();
    }

    @Test
    void colourLaunchConfirmTest() {
        WebElement launchConfirm = driver.findElement(By.id("my-confirm"));
        launchConfirm.getCssValue("color");
        String initialColor = launchConfirm.getCssValue("background-color");
        new Actions(driver).moveToElement(launchConfirm).perform();
        String changedColor = launchConfirm.getCssValue("background-color");
        assertNotEquals(initialColor, changedColor, "Цвет должен был измениться");
    }

    @Test
    void acceptLaunchConfirmTest() {
        WebElement launchConfirm = driver.findElement(By.id("my-confirm"));
        launchConfirm.click();
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        alert.accept();
        WebElement chose = driver.findElement(By.id("confirm-text"));
        assertEquals("You chose: true", chose.getText(), "Выбран неверный вариант");
    }

    @Test
    void dismissLaunchConfirmTest() {
        WebElement launchConfirm = driver.findElement(By.id("my-confirm"));
        launchConfirm.click();
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        alert.dismiss();
        WebElement chose = driver.findElement(By.id("confirm-text"));
        assertEquals("You chose: false", chose.getText(), "Выбран неверный вариант");
    }

    @Test
    void colourLaunchPromptTest() {
        WebElement launchPrompt = driver.findElement(By.id("my-prompt"));
        launchPrompt.getCssValue("color");
        String initialColor = launchPrompt.getCssValue("background-color");
        new Actions(driver).moveToElement(launchPrompt).perform();
        String changeColor = launchPrompt.getCssValue("background-color");
        assertNotEquals(initialColor, changeColor, "Цвет должен был измениться");
//        assertEquals("rgba(0, 0, 0, 0)", initialColor, "Цвет кнопки до открытия alert не совпадает");
//        assertEquals("rgba(220, 53, 69, 0.898)", changeColor, "Цвет кнопки после наведения не совпадает");
    }

    @Test
    void acceptLaunchPromptTest() {
        WebElement launchPromt = driver.findElement(By.id("my-prompt"));
        launchPromt.click();
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        String input = "Danger!";
        alert.sendKeys(input);
        alert.accept();
        WebElement chose = driver.findElement(By.id("prompt-text"));
        String choseS = chose.getText();
        assertTrue(choseS.contains(input), "Не отображается выбор");
    }

    @Test
    void dismissLaunchPromptTest() {
        WebElement launchPromt = driver.findElement(By.id("my-prompt"));
        launchPromt.click();
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        alert.dismiss();
        WebElement chose = driver.findElement(By.id("prompt-text"));
        String choseS = chose.getText();
        assertEquals("You typed: null", choseS, "При отмене должен быть null");
    }

    @Test
    void colourLaunchModalTest() {
        WebElement launchModal = driver.findElement(By.id("my-modal"));
        launchModal.getCssValue("color");
        String initialColor = launchModal.getCssValue("background-color");
        new Actions(driver).moveToElement(launchModal).perform();
        String changedColor = launchModal.getCssValue("background-color");
        assertNotEquals(initialColor, changedColor, "Цвет должен был измениться");
    }

    @Test
    void saveChangesLaunchModalTest() {
        WebElement launchModal = driver.findElement(By.id("my-modal"));
        launchModal.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("example-modal")));
        WebElement save = driver.findElement(By.cssSelector("button[data-bs-dismiss='modal']:nth-of-type(2)"));
        save.click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("example-modal")));
        WebElement chose = driver.findElement(By.id("modal-text"));
        assertEquals("You chose: Save changes", chose.getText(), "Должен был быть текст о выборе save");
    }

    @Test
    void closeLaunchModalTest() {
        WebElement launchModal = driver.findElement(By.id("my-modal"));
        launchModal.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("example-modal")));
        WebElement close = driver.findElement(By.cssSelector("button[data-bs-dismiss='modal']:nth-of-type(1)"));
        close.click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("example-modal")));
        WebElement chose = driver.findElement(By.id("modal-text"));
        assertEquals("You chose: Close", chose.getText(), "Должен был быть текст о выборе Close");
    }
}
