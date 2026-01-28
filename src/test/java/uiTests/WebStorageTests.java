package uiTests;

import base.BaseUITest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class WebStorageTests extends BaseUITest {

    @BeforeEach
    void openPage() {
        driver.get(config.getWebStorageUrl());
    }

    @Test
    void localStorageColorTest() {
        WebElement local = driver.findElement(By.id("display-local"));
        //получение цвета до наведения клика
        local.getCssValue("color");
        String initialColor = local.getCssValue("background-color");
        //наведение мыши на элемент
        new Actions(driver).moveToElement(local).perform();
        //получение изменившегося цвета
        String changedColor = local.getCssValue("background-color");
        //проверка изменения
        assertNotEquals(initialColor, changedColor, "Цвет должен был измениться");
        assertEquals("rgba(0, 0, 0, 0)", initialColor, "Должен быть цвет rgba(0, 0, 0, 0) ");
        //assertEquals("rgba(13, 110, 253, 0.898)", changedColor, "Должен быть цвет rgba(13, 110, 253, 0.898)");
    }

    @Test
    void localStorageTest() {
        WebElement local = driver.findElement(By.id("display-local"));
        local.click();
        WebElement localText = driver.findElement(By.id("local-storage"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("local-storage")));
        assertEquals("{}", localText.getText(), "Должен быть пустой текст");
    }

    @Test
    void localStorageSetItemTest() {
        WebElement local = driver.findElement(By.id("display-local"));

        JavascriptExecutor js = (JavascriptExecutor) driver;

        // сохраняем пару ключ-значение в localStorage
        String key = "age";
        String value = "15";
        js.executeScript("window.localStorage.setItem(arguments[0], arguments[1]);", key, value);

        // кликаем по кнопке, чтобы обновить UI
        local.click();

        // читаем значение из localStorage
        String actual = (String) js.executeScript("return window.localStorage.getItem(arguments[0]);", key);

        //проверка в хранилище
        assertEquals(value, actual);

        //проверка в UI
        WebElement localText = driver.findElement(By.id("local-storage"));
        String localTextS = localText.getText();
        assertTrue(localTextS.contains(key), "Ключ не отображается в UI");
        assertTrue(localTextS.contains(value), "Значение не отображается в UI");
    }

    @Test
    void localStorageSetSeveralItemTest() {
        JavascriptExecutor js = (JavascriptExecutor) driver;

        Map<String, String> data = new HashMap<>();
        data.put("CurrentTheme", "11");
        data.put("bigcal", "true");

        for (Map.Entry<String, String> entry : data.entrySet()) {
            js.executeScript("window.localStorage.setItem(arguments[0], arguments[1]);",
                    entry.getKey(), entry.getValue());

            String actual = (String) js.executeScript("return window.localStorage.getItem(arguments[0]);",
                    entry.getKey());

            assertEquals(entry.getValue(), actual, "Значение в localStorage не совпадает для ключа " + entry.getKey());
        }

        // кликаем по элементу, чтобы обновить UI
        WebElement element = driver.findElement(By.id("display-local"));
        element.click();

        //проверка в UI
        WebElement local = driver.findElement(By.id("local-storage"));
        String localText = local.getText();

        for (Map.Entry<String, String> entry : data.entrySet()) {
            assertTrue(localText.contains(entry.getKey()), "Ключ не отображается в UI");
            assertTrue(localText.contains(entry.getValue()), "Значение не отображается в UI");
        }
    }

    @Test
    void sessionStorageTest() {
        WebElement session = driver.findElement(By.id("display-session"));
        session.click();
        WebElement sessionText = driver.findElement(By.id("session-storage"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("session-storage")));
        assertEquals("{\"lastname\":\"Doe\",\"name\":\"John\"}", sessionText.getText(), "Должна отобразится аутентификация");
    }

    @Test
    void sessionStorageDeleteTest() {
        JavascriptExecutor js = (JavascriptExecutor) driver;

        WebElement session = driver.findElement(By.id("display-session"));
        session.click();

        WebElement sessionText = driver.findElement(By.id("session-storage"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("session-storage")));

        // Проверка начального состояния
        assertEquals("{\"lastname\":\"Doe\",\"name\":\"John\"}", sessionText.getText(), "Должна отобразится аутентификация");

        // Удаляем ключ 'lastname' из sessionStorage
        js.executeScript("window.sessionStorage.removeItem(arguments[0]);", "lastname");

        // Проверяем, что ключ удалён
        Object removed = js.executeScript("return window.sessionStorage.getItem(arguments[0]);", "lastname");
        assertNull(removed, "Ключ 'lastname' должен быть удалён из sessionStorage");

        // Повторный клик для обновления UI
        session.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("session-storage")));

        //проверяем, что в тексте осталось имя
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("session-storage")));
        assertEquals("{\"name\":\"John\"}", sessionText.getText(), "Должно отобразиться имя");
    }
}
