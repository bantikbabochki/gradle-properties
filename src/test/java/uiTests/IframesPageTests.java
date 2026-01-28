package uiTests;

import base.BaseUITest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class IframesPageTests extends BaseUITest {
    @BeforeEach
    void start() {
        driver.get(config.getIframesUrl());
    }

    @Test
    void textIframeIsDisplayedTest() {
        // Находим iframe и переключаемся внутрь
        WebElement iframe = driver.findElement(By.id("my-iframe"));
        assertTrue(iframe.isDisplayed(), "Iframe не отображается");
        // Проверяем текст внутри iframe
        driver.switchTo().frame(iframe);
        WebElement firstParagraph = driver.findElement(By.xpath("//b[text()='Lorem ipsum']"));
        assertEquals("Lorem ipsum",firstParagraph.getText(), "Текст внутри iframe не совпадает");
        WebElement lastParagraph = driver.findElement(By.xpath("//p[contains(text(),'Magnis feugiat natoque proin commodo')]"));
        new Actions(driver).scrollToElement(lastParagraph).perform();
        // Возвращаемся в основное окно
        driver.switchTo().defaultContent();
        // Проверяем заголовок 'IFrame' на основной странице
        WebElement header = driver.findElement(By.xpath("//h1[text()='IFrame']"));
        assertTrue(header.isDisplayed(), "Заголовок 'IFrame' не отображается");

    }
}
