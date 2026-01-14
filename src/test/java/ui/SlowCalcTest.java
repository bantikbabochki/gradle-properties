package ui;

import base.BaseUITest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SlowCalcTest extends BaseUITest {

    @BeforeEach
    public void initPageCalc(){
        driver.get(config.getSlowCalculatorUrl());
    }

    @Test
    void slowCalcWaitTest() {

        //locators
        By secondsWait = By.id("delay");
        By calcButtonSevenLocator = By.xpath("//span[text() = '7']");
        By calcButtonPlusLocator = By.xpath("//span[text() = '+']");
        By calcButtonNineLocator = By.xpath("//span[text() = '9']");
        By calcButtonEqualLocator = By.xpath("//span[text() = '=']");
        By resultField = By.xpath("//div[@class='screen']");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfElementLocated(secondsWait));
        //actions
        driver.findElement(secondsWait).clear();
        driver.findElement(secondsWait).sendKeys("3");
        driver.findElement(calcButtonSevenLocator).click();
        driver.findElement(calcButtonPlusLocator).click();
        driver.findElement(calcButtonNineLocator).click();
        WebElement click = driver.findElement(calcButtonEqualLocator);
        safeClick(click);

//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        wait.until(ExpectedConditions.textToBe(resultField, "16"));
    }
}
