package ui;

import base.BaseUITest;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;


import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

    public class ActionApiTests extends BaseUITest {

        @Test
        @Severity(SeverityLevel.NORMAL)
        @DisplayName("Работа с мышью")
        @Tag("ActionAPI")
        void actionAPIMouseClickTests() {
            driver.get(config.getDropdownMenuUrl());
            //Левая кнопка мыши
            WebElement dropdown1 = driver.findElement(By.id("my-dropdown-1"));
            //Открываем меню
            new Actions(driver)
                    .click(dropdown1)
                    .perform();
            //Выбираем пункт меню
            WebElement anotherAction = driver.findElement(By.linkText("Another action"));
            new Actions(driver)
                    .click(anotherAction)
                    .perform();
            //Правая кнопка мыши
            WebElement dropdown2 = driver.findElement(By.id("my-dropdown-2"));
            //Открываем меню
            new Actions(driver)
                    .contextClick(dropdown2)
                    .perform();
            //Выбираем пункт меню
            WebElement somethingElseHere = driver.findElement(By.linkText("Something else here"));
            new Actions(driver)
                    .click(somethingElseHere)
                    .perform();
            //Двойной клик
            WebElement dropdown3 = driver.findElement(By.id("my-dropdown-3"));
            //Открываем меню
            new Actions(driver)
                    .doubleClick(dropdown3)
                    .perform();
            //Выбираем пункт меню
            WebElement action = driver.findElement(By.linkText("Action"));
            new Actions(driver)
                    .click(action)
                    .perform();
        }

        @Test
        @Severity(SeverityLevel.NORMAL)
        @DisplayName("Работа с наведением мыши")
        @Tag("ActionAPI")
        void actionAPIMouseMoveTest() {
            driver.get(config.getMouseOverUrl());
            List<WebElement> images = driver.findElements(By.className("img-fluid"));
            for (WebElement image : images) {
                new Actions(driver)
                        .moveToElement(image)
                        .perform();
            }
        }

        @Test
        @Severity(SeverityLevel.NORMAL)
        @DisplayName("Перетаскивание элемента")
        @Tag("ActionAPI")
        void actionAPIDragAndDropTest() {
            driver.get(config.getDragAndDropUrl());
            WebElement dragMe = driver.findElement(By.id("draggable"));
            WebElement border = driver.findElement(By.id("target"));

            Point initialPosition = dragMe.getLocation();
            System.out.println("initialPosition" + initialPosition);

            new Actions(driver)
                    .dragAndDrop(dragMe, border)
                    .perform();

            Point finalPosition = dragMe.getLocation();
            System.out.println("borderPosition" + border.getLocation());
            assertEquals(finalPosition, border.getLocation());
            assertNotEquals(initialPosition, finalPosition);
        }

        @Test
        @Severity(SeverityLevel.NORMAL)
        @DisplayName("Скролл страницы")
        @Tag("ActionAPI")
        void actionAPILongPageTest() {
            driver.get(config.getLongPageUrl());
            WebElement scrollTo = driver.findElement(By.className("text-muted"));
            new Actions(driver)
                    .scrollToElement(scrollTo)
                    .perform();
        }
    }

