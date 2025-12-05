package ui;

import base.BaseUITest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.apache.commons.io.FileUtils;


import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class InfiniteScrollTests extends BaseUITest {

    @BeforeEach
    void openPage() {
        driver.get(config.getInfiniteScrollUrl());
    }

    @Test
    void infiniteScrollTest() throws IOException {
        List<WebElement> paragraphs = driver.findElements(By.xpath("//p[@class='lead']"));

        assertEquals(20, paragraphs.size(),"Ошибка на первом этапе");
        assertTrue(paragraphs.getFirst().getText().contains("Lorem ipsum dolor sit amet"), "Ошибка на первом этапе");

        WebElement lastParagraph = paragraphs.get(19);
        Actions last = new Actions(driver);
        last
                .scrollToElement(lastParagraph)
                .perform();

        new Actions(driver)
                .scrollByAmount(0, 50)
                .perform();

        List<WebElement> paragraphsNew = driver.findElements(By.xpath("//p[@class='lead']"));
        assertEquals(40, paragraphsNew.size(), "Ошибка на втором этапе");
        assertTrue(paragraphsNew.get(39).getText().contains("Magnis feugiat natoque proin"), "Ошибка на втором этапе");

        File srcFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(srcFile, new File("./image.png"));
    }
}
