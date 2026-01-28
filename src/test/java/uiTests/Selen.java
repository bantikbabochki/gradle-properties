package uiTests;

import base.BaseUITest;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Selen extends BaseUITest {

    @Test
    void openHomePageTest() {
        driver.get(config.getBaseUrl());

        assertEquals(config.getBaseUrl(), driver.getCurrentUrl());
        assertEquals("Hands-On Selenium WebDriver with Java", driver.getTitle());
    }

    @Test
    void openLoginPageTest() {
        driver.get(config.getBaseUrl());

        WebElement click = driver.findElement(By.xpath("//a[@href = 'login-form.html']"));
        safeClick(click);
        WebElement title = driver.findElement(By.className("display-6"));

        assertEquals(config.getBaseUrl() + "login-form.html", driver.getCurrentUrl());
        assertEquals("Login form", title.getText());
    }

    @Test
    void signInTest() {
        driver.get(config.getBaseUrl());
        driver.findElement(By.xpath("//a[@href = 'login-form.html']")).click();

        driver.findElement(By.id("username")).sendKeys(config.getUsername());
        driver.findElement(By.id("password")).sendKeys(config.getPassword());
        driver.findElement(By.xpath("//button[@type = 'submit']")).click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement message = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("alert")));

        assertEquals("Login successful", message.getText());
    }
}
