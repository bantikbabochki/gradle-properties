package ui;

import base.BaseUITest;
import enums.LocatorType;
import helpers.ElementHelper;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import methods.FileUpload;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.Duration;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class InteractionsWithElementsTests extends BaseUITest {

    private ElementHelper elementHelper;

    @BeforeEach
    public void initWebForm() {
        driver.get(config.getWebFormUrl());
        elementHelper = new ElementHelper(driver);
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("(Введение текста в поле Text input(SendKeys)")
    @Tag("Interactions")
    void textInputTest() {
        //Проверить что поле доступно
        WebElement textInput = elementHelper.getVisibleElement(LocatorType.ID, "my-text-id");
        //Проверка, что текст вводится
        textInput.sendKeys("kolyan");
        assertEquals("kolyan", textInput.getDomProperty("value"));
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Очищение поля Text input(Clear text)")
    @Tag("Interactions")
    void clearTextTest() {
        //Проверить что поле доступно
        WebElement textInput = elementHelper.getVisibleElement(LocatorType.ID, "my-text-id");
        //Проверка, что текст вводится
        textInput.sendKeys("kolyan");
        assertEquals("kolyan", textInput.getDomProperty("value"));
        textInput.clear();
        assertEquals("", textInput.getDomProperty("value"));
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Отправка формы (SubmitButton)")
    @Tag("Interactions")
    void submitWebFormWitTextTest() throws InterruptedException {
        //Проверить что поле доступно
        WebElement textInput = elementHelper.getVisibleElement(LocatorType.ID, "my-text-id");
        //Проверка, что текст вводится
        textInput.sendKeys("kolyan");
        assertEquals("kolyan", textInput.getDomProperty("value"));
        elementHelper.clickSubmitButton(LocatorType.CSS_SELECTOR, "button[type='submit']");
        new WebDriverWait(driver, Duration.ofSeconds(2))
                .until(ExpectedConditions.urlContains("submitted-form.html"));
        // 4. Проверка успешной отправки
        String currentUrl = driver.getCurrentUrl();
        assertNotNull(currentUrl);
        assertTrue(currentUrl.contains("submitted-form.html"));
        WebElement successMessage = driver.findElement(By.xpath("//h1[text()='Form submitted']"));
        assertEquals("Form submitted", successMessage.getText());
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Выбор и отмена элемента (select/deselect)")
    @Tag("Interactions")
    void selectFromListTest() {
        //Проверить что поле доступно
        WebElement dropdownSelectMenu = elementHelper.getVisibleElement(LocatorType.CLASS_NAME, "form-select");
        //selectByIndex
        Select select = new Select(dropdownSelectMenu);
        select.selectByIndex(1);
        select.selectByIndex(3);
        //selectByValue
        select.selectByValue("2");
        //selectByValue
        select.selectByVisibleText("Open this select menu");

        //first selected option
        assertEquals("Open this select menu", select.getFirstSelectedOption().getText());
        assertTrue(select.getFirstSelectedOption().isSelected());

        //get all selected options
        /*
        Если нужен индекс, то будет записано так:
        for(int i = 0; i< selectedOptions.size(); i++) {
        WebElement option = selectedOptions.get(i);
        System.out.println(option.getText());
         */
        List<WebElement> selectedOptions = select.getAllSelectedOptions();
        for (WebElement selectedOption : selectedOptions) {
            System.out.println("Selected option: " + selectedOption.getText());
        }

        //get all options
        List<WebElement> options = select.getOptions();
        for (WebElement option : options) {
            System.out.printf("Availiable Option: %s isSelected = %s%n", option.getText(), option.isSelected());
        }
        /*
        у послдеднего листа будет такой ответ:
        Availiable Option: Open this select menu isSelected = true
        Availiable Option: One isSelected = false
        Availiable Option: Two isSelected = false
        Availiable Option: Three isSelected = false
         */

        //deselecting
        if (select.isMultiple()) {
            select.deselectByIndex(1);
            select.deselectByValue("1");
            select.deselectByVisibleText("One");
            select.deselectAll();
        } else {
            System.out.println("You may only deselect all options of a multi-select");
        }
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Получение данных элемента")
    @Tag("Interactions")
    void getInfoTest() {
        //get isEnabled
        WebElement disabledInput = elementHelper.getVisibleElement(LocatorType.NAME, "my-disabled");
        assertFalse(disabledInput.isEnabled());
        //get
        System.out.println("location" + disabledInput.getLocation());
        System.out.println("size" + disabledInput.getSize());
        assertThrows(ElementNotInteractableException.class, () -> disabledInput.sendKeys("kolyan"));

        //getRect (координаты и размеры)
        Rectangle rec = disabledInput.getRect();
        System.out.printf("Dimension %s, Height %s, Width %s, Point %s, X: %s, Y: %s\n",
                rec.getDimension(), rec.height, rec.width, rec.getPoint(), rec.getX(), rec.getY());
        /*
        ответ будет такой:
           Dimension (416, 38), Height 38, Width 416, Point (112, 517), X: 112, Y: 517
         */

        //get css values
        System.out.println(disabledInput.getCssValue("appearance"));
        System.out.println(disabledInput.getCssValue("opacity"));
        System.out.println(disabledInput.getCssValue("font-size"));
        System.out.println(disabledInput.getCssValue("color"));

        //get text
        assertEquals("Disabled input", disabledInput.getDomAttribute("placeholder"));
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Загрузка файла")
    @Tag("Interactions")
    void fileUploadTest() throws URISyntaxException, IOException, InterruptedException {
        WebElement fileInput = elementHelper.getVisibleElement(LocatorType.CSS_SELECTOR, "[name='my-file']");
        // имя файла задаём как переменную
        String fileName = "new.txt";
        // получаем абсолютный путь через FileUpload
        String absolutePath = FileUpload.getPath(fileName);
        // загружаем файл
        fileInput.sendKeys(absolutePath);
        elementHelper.clickSubmitButton(LocatorType.CSS_SELECTOR, "[type='submit']");
        // Явное ожидание, пока URL изменится
        new WebDriverWait(driver, Duration.ofSeconds(1))
                .until(ExpectedConditions.urlContains("submitted-form.html"));
        // проверка: имя файла появилось в UI
        assertThat(driver.getCurrentUrl()).contains("new.txt");
    }
}
