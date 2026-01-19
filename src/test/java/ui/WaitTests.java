package ui;

import base.BaseUITest;
import enums.LocatorType;
import helpers.ElementHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

public class WaitTests extends BaseUITest {

    @BeforeEach
    public void initPage() {
        driver.get(config.getLoadingImagesUrl());
    }

    @Test
    void loadingImagesImplicitWaitTest() {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));

        WebElement landscape = elementHelper.getVisibleElement(LocatorType.ID, "landscape");
        //Проверка, что строка содержит слово "landscape", без учёта регистра.
        assertThat(landscape.getDomAttribute("src"))
                .isNotEmpty()
                .as("Error in search compass")
                .containsIgnoringCase("landscape");
    }

    @Test
    void loadingImagesExplicitWaitTest() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement landscape = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("landscape")));
        assertThat(landscape.getDomAttribute("src")).containsIgnoringCase("landscape");
    }

    @Test
    void loadingImagesExplicitLongWaitTest() {
        WebElement landscape = longWait.until(ExpectedConditions.presenceOfElementLocated(By.id("landscape")));
        assertThat(landscape.getDomAttribute("src")).containsIgnoringCase("landscape");
    }

    @Test
    void loadingImagesFluentWaitTest() {
        Wait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(10))
                .pollingEvery(Duration.ofSeconds(1))
                .ignoring(NoSuchElementException.class);

        WebElement landscape = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("landscape")));
        assertThat(landscape.getDomAttribute("src")).containsIgnoringCase("landscape");
    }
}
