package ui;

import base.BaseUITest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OpenAllLinksTest extends BaseUITest {

    @BeforeEach
    void open() {
        driver.get(config.getBaseUrl());
    }

    @Test
    void openAllLinks() {
        //Счетчик ссылок, начиная с 0
        int qtyLinks = 0;
        //Поиск блоков Chapters
        List<WebElement> chapters = driver.findElements(By.cssSelector("h5.card-title"));
        // Цикл по каждому блоку
        for (
            //Тип переменная : коллекция
                WebElement chapter : chapters) {
            List<WebElement> links = chapter.findElements(By.xpath("./../a"));
            //Подсчет общего количества ссылок в блоках
            qtyLinks += links.size();//аналогично qtyLinks = qtyLinks + links.size();
            System.out.println(chapter.getText());
            //Перебирает все ссылки внутри блока
            for (WebElement link : links) {
                System.out.println(link.getText());
                link.click();
                driver.navigate().back();
            }
        }
        assertEquals(6, chapters.size());
        assertEquals(27, qtyLinks);
    }

    @Test
    void openWindowsTest() {
        String currentWindow = driver.getCurrentUrl();
        String window = driver.getWindowHandle();
        driver.switchTo().newWindow(WindowType.TAB).get("https://bonigarcia.dev/selenium-webdriver-java/long-page.html");
        String textLongPage = driver.findElement(By.xpath("//h1[text()='This is a long page']")).getText();
        assertEquals("This is a long page", textLongPage, "Текст заголовка не совпадает");
        driver.close();
        driver.switchTo().window(window);
        assertEquals(currentWindow, driver.getCurrentUrl(), "Не вернулись на исходную страницу");
        driver.switchTo().newWindow(WindowType.TAB).get("https://bonigarcia.dev/selenium-webdriver-java/web-storage.html");
        String textWebStorage = driver.findElement(By.xpath("//h1[text()='Web storage']")).getText();
        assertEquals("Web storage", textWebStorage, "Текст заголовка не совпадает");
        driver.close();
    }
}
