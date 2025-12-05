package ui;

import base.BaseUITest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.assertj.core.api.Assertions.assertThat;

public class CookiesTests extends BaseUITest {

    @Test
    void cookiesTest() {
        driver.get(config.getCookiesUrl());
        //get cookies
        driver.manage().getCookies();
        //get cookie
        Cookie cookie = driver.manage().getCookieNamed("username");
        //click on button to view cookies
        WebElement refreshButton = driver.findElement(By.xpath("//button[@id='refresh-cookies']"));
        new Actions(driver).moveToElement(refreshButton).click().perform();
        //view cookies list
        WebElement cookList = driver.findElement(By.id("cookies-list"));
        String cookiesText = cookList.getText();
        //assertions
        assertTrue(cookiesText.contains("username=John Doe"), "username=John Doe' не найден в блоке");
        assertTrue(cookiesText.contains("date=10/07/2018"), "date=10/07/2018' не найден в блоке");
        //или
        Assertions.assertNotNull(cookie);
        assertThat(cookie.getPath()).isEqualTo("/")
                .as(String.format("cookie.getPath() should be %s, but was %s", "/", cookie.getPath()));

        //add cookie
        driver.manage().addCookie(new Cookie("age", "34"));
        //click on button to view cookies
        WebElement newC = driver.findElement(By.xpath("//button[@id='refresh-cookies']"));
        new Actions(driver).moveToElement(newC).click().perform();
        //find new cookie in a list
        WebElement cookieList = driver.findElement(By.id("cookies-list"));
        String cookiesNewText = cookieList.getText();
        //assertions
        assertTrue(cookiesNewText.contains("age=34"), "Cookie 'age=34' не найден в блоке");

        //delete cookie
        driver.manage().deleteCookieNamed("age");
        //click on button to view cookies
        WebElement delC = driver.findElement(By.xpath("//button[@id='refresh-cookies']"));
        new Actions(driver).moveToElement(delC).click().perform();
        //find new cookie in a list
        WebElement cookieDelList = driver.findElement(By.id("cookies-list"));
        String cookiesDelText = cookieDelList.getText();
        //assertions
        assertFalse(cookiesDelText.contains("age=34"), "Cookie 'age=34' не найден в блоке");
    }
}
