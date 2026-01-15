package ui;

import base.BaseUITest;
import enums.LocatorType;
import helpers.ElementHelper;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class NavigationExampleTests extends BaseUITest {

    @BeforeEach
    public void initNavigationPage() {
        driver.get(config.getNavigationUrl());
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Отображение главной страницы")
    @Tag("NavigationExample")
    void deFaultDisplayTest() {
        WebElement text = driver.findElement(By.xpath("//p[contains(text(),'Lorem ipsum dolor sit amet')]"));
        assertEquals("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt" +
                " ut labore et dolore magna aliqua.", text.getText());
        elementHelper.checkElementsVisible(LocatorType.LINK_TEXT, "Back to index");
        elementHelper.checkElementsVisible(LocatorType.LINK_TEXT, "Previous");
        elementHelper.checkElementsVisible(LocatorType.LINK_TEXT, "1");
        elementHelper.checkElementsVisible(LocatorType.LINK_TEXT, "2");
        elementHelper.checkElementsVisible(LocatorType.LINK_TEXT, "3");
        elementHelper.checkElementsVisible(LocatorType.LINK_TEXT, "Next");
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Пагинация")
    @Tag("NavigationExample")
    void secondPagePaginationTests() throws InterruptedException {

        //Переход на 2 страницу
        driver.findElement(By.linkText("2"));
        elementHelper.clickSubmitButton(LocatorType.LINK_TEXT, "2");
        //Находим текст
        WebElement secondText = driver.findElement(By.xpath("//p[contains(text(),'Ut enim ad minim veniam')]"));
        //Сравниваем текст
        assertEquals("Ut enim ad minim veniam, quis nostrud exercitation ullamco" +
                " laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in " +
                "voluptate velit esse cillum dolore eu fugiat nulla pariatur.", secondText.getText());

        //Переход на 3 страницу
        driver.findElement(By.linkText("3"));
        elementHelper.clickSubmitButton(LocatorType.LINK_TEXT, "3");
        //Находим текст
        WebElement thirdText = driver.findElement(By.xpath("//p[contains(text(),'Excepteur sint occaecat cupidatat non proident')]"));
        //Сравниваем текст
        assertEquals("Excepteur sint occaecat cupidatat non proident, sunt in culpa qui " +
                "officia deserunt mollit anim id est laborum.", thirdText.getText());

        //Переход на страницу кнопкой Previous
        WebElement previous = driver.findElement(By.linkText("Previous"));
        new Actions(driver)
                .moveToElement(previous)
                .click()
                .perform();
        WebElement secondText2 = driver.findElement(By.xpath("//p[contains(text(),'Ut enim ad minim veniam')]"));
        assertEquals("Ut enim ad minim veniam, quis nostrud exercitation ullamco" +
                " laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in " +
                "voluptate velit esse cillum dolore eu fugiat nulla pariatur.", secondText2.getText());

        //Переход на страницу кнопкой Next
        WebElement next = driver.findElement(By.linkText("Next"));
        new Actions(driver)
                .moveToElement(next)
                .click()
                .perform();
        WebElement thirdText2 = driver.findElement(By.xpath("//p[contains(text(),'Excepteur sint occaecat cupidatat non proident')]"));
        //Сравниваем текст
        assertEquals("Excepteur sint occaecat cupidatat non proident, sunt in culpa qui " +
                "officia deserunt mollit anim id est laborum.", thirdText2.getText());
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Возврат на главную страницу")
    @Tag("NavigationExample")
    void returnToHomePageTest() {
        WebElement backToIndex = driver.findElement(By.linkText("Back to index"));
        new Actions(driver)
                .moveToElement(backToIndex)
                .click()
                .perform();
        assertEquals("https://bonigarcia.dev/selenium-webdriver-java/index.html", Objects.requireNonNull(driver.getCurrentUrl()).trim());
    }

//нужно не хардкодить кол-во страниц, а учесть, что они масштабируются
//добавить проверку, что переходя на последнюю страницу далее кнопка некст не работает
//надо посчитать кол-во страниц

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Количество страниц")
    @Tag("NavigationExample")
    void numberOfPagesTest() {
        // Находим все ссылки навигации
        List<WebElement> pages = driver.findElements(By.cssSelector("nav a"));
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Получение последней страницы перед Next")
    @Tag("NavigationExample")
    void lastNumberBeforeTest() {
        // Находим все ссылки навигации
        List<WebElement> links = driver.findElements(By.cssSelector("nav a"));
        List<String> texts = links.stream()//превращаем список элементов в поток (stream)
                .map(e -> e.getText().trim())//для каждого элемента берём его текст (getText())
                .toList();//собираем обратно в список строк.
        // Теперь у нас список текстов, например: ["Previous", "1", "2", "3", "Next"].

        // Находим индекс кнопки Next
        int nextIndex = texts.indexOf("Next");//Например, если список ["Previous", "1", "2", "3", "Next"], то индекс "Next" будет 4

        // Получаем текст последней числовой страницы перед Next
        final String lastNumericBeforeNext;
        if (nextIndex > 0) {
            String candidate = texts.get(nextIndex - 1);//берём элемент, который стоит перед "Next"
            if (candidate.matches("\\d+")) {//проверяем, что это число
                lastNumericBeforeNext = candidate;//Если это число — сохраняем его в переменную lastNumericBeforeNext
            } else {
                throw new AssertionError("Элемент перед Next не является числом");
            }
        } else {
            throw new AssertionError("Next не найдена или стоит первой");
        }

        System.out.println("Последняя страница: " + lastNumericBeforeNext);

        // Переход на последнюю страницу
        WebElement lastPageLink = links.stream()
                .filter(e -> e.getText().trim().equals(lastNumericBeforeNext))//оставляем только те, чей текст равен lastNumericBeforeNext
                .findFirst()//берём первую подходящую
                .orElseThrow(() -> new NoSuchElementException("Ссылка на последнюю страницу не найдена"));//если не нашли, выбрасываем ошибку
        lastPageLink.click();

        // Ждём, пока URL изменится на navigationN.html
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.urlContains("navigation" + lastNumericBeforeNext + ".html"));

        // Находим родительский li для кнопки Next
        WebElement nextLi = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//li[a[text()='Next']]")));//или так //a[text()='Next']/..

        // Проверка: у родителя есть класс disabled
        String liClass = nextLi.getDomAttribute("class");
        Assertions.assertNotNull(liClass);
        assertTrue(liClass.contains("disabled"), "Кнопка Next должна быть отключена");
    }
}
