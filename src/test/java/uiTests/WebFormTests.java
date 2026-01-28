package uiTests;

import base.BaseUITest;
import com.github.javafaker.Faker;
import elements.WebFormLocators;
import enums.LocatorType;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class WebFormTests extends BaseUITest {

    private static final Faker fake = new Faker(Locale.ENGLISH);

    @BeforeEach
    public void initWebPage() {
        driver.get(config.getWebFormUrl());
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Отправка формы. Поле текста 100 предложений")
    @Tag("NavigationExample")
    void textInput100Test() throws InterruptedException {
        String textLong = fake.lorem().paragraph(100);
        elementHelper.getElement(WebFormLocators.TEXT_INPUT).sendKeys(textLong);
        elementHelper.clickSubmitButton(LocatorType.CSS_SELECTOR, "button[type='submit']");
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.urlContains("submitted-form.html"));
        // Проверка успешной отправки
        String currentUrl = driver.getCurrentUrl();
        assertNotNull(currentUrl);
        assertTrue(currentUrl.contains("submitted-form.html"));
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Отправка формы с пустым полем текста")
    @Tag("NavigationExample")
    void textInputNullTest() throws InterruptedException {
        elementHelper.getElement(WebFormLocators.TEXT_INPUT).sendKeys("");
        elementHelper.clickSubmitButton(LocatorType.CSS_SELECTOR, "button[type='submit']");
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.urlContains("submitted-form.html"));
        // Проверка успешной отправки
        String currentUrl = driver.getCurrentUrl();
        assertNotNull(currentUrl);
        assertTrue(currentUrl.contains("submitted-form.html"));
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Отправка формы. Поле текста со знаками")
    @Tag("NavigationExample")
    void textInputSignsTest() throws InterruptedException {
        elementHelper.getElement(WebFormLocators.TEXT_INPUT).sendKeys("$$#@#$^@^$#%$^%%545376857");
        elementHelper.clickSubmitButton(LocatorType.CSS_SELECTOR, "button[type='submit']");
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.urlContains("submitted-form.html"));
        // Проверка успешной отправки
        String currentUrl = driver.getCurrentUrl();
        assertNotNull(currentUrl);
        assertTrue(currentUrl.contains("submitted-form.html"));
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Отправка формы. Поле пароль с длинным текстом")
    @Tag("NavigationExample")
    void passwordLongTextTest() throws InterruptedException {
        String textLong = fake.lorem().paragraph(100);
        elementHelper.getElement(WebFormLocators.PASSWORD).sendKeys(textLong);
        elementHelper.clickSubmitButton(LocatorType.CSS_SELECTOR, "button[type='submit']");
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.urlContains("submitted-form.html"));
        // Проверка успешной отправки
        String currentUrl = driver.getCurrentUrl();
        assertNotNull(currentUrl);
        assertTrue(currentUrl.contains("submitted-form.html"));
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Отправка формы. Поле пароль с длинным текстом")
    @Tag("NavigationExample")
    void passwordNullTest() throws InterruptedException {
        elementHelper.getElement(WebFormLocators.PASSWORD).sendKeys("");
        elementHelper.clickSubmitButton(LocatorType.CSS_SELECTOR, "button[type='submit']");
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.urlContains("submitted-form.html"));
        // Проверка успешной отправки
        String currentUrl = driver.getCurrentUrl();
        assertNotNull(currentUrl);
        assertTrue(currentUrl.contains("submitted-form.html"));
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Отправка формы. Поле пароль со знаками и разной высоты текстом")
    @Tag("NavigationExample")
    void passwordSignsTest() throws InterruptedException {
        elementHelper.getElement(WebFormLocators.PASSWORD).sendKeys("hghg$^$&^НЕНЕЕНПрррпорм");
        elementHelper.clickSubmitButton(LocatorType.CSS_SELECTOR, "button[type='submit']");
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.urlContains("submitted-form.html"));
        // Проверка успешной отправки
        String currentUrl = driver.getCurrentUrl();
        assertNotNull(currentUrl);
        assertTrue(currentUrl.contains("submitted-form.html"));
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Отправка формы. Поле пароль со знаками и разной высоты текстом")
    @Tag("NavigationExample")
    void passwordPasteTest() throws InterruptedException {
        //Копируем текст
        WebElement source = driver.findElement(By.xpath("//h5[text()='Practice site']"));
        String copiedText = source.getText();
        //Вставляем текст
        WebElement target = elementHelper.getElement(WebFormLocators.PASSWORD);
        target.clear();              // очищаем поле
        target.sendKeys(copiedText); // вставляем текст
        assertEquals(copiedText, target.getDomProperty("value"));
        //Отправляем форму
        elementHelper.clickSubmitButton(LocatorType.CSS_SELECTOR, "button[type='submit']");
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.urlContains("submitted-form.html"));
        // Проверка успешной отправки
        String currentUrl = driver.getCurrentUrl();
        assertNotNull(currentUrl);
        assertTrue(currentUrl.contains("submitted-form.html"));
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Отправка формы. Поле текстовое со скролом")
    @Tag("NavigationExample")
    void textAreaTest() throws InterruptedException {
        String text = fake.lorem().paragraph(100);
        //Добавляем перенос текста
        String textLong = text.replace(". ", ".\n");
        elementHelper.getElement(WebFormLocators.TEXTAREA).sendKeys(textLong);
        elementHelper.clickSubmitButton(LocatorType.CSS_SELECTOR, "button[type='submit']");
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.urlContains("submitted-form.html"));
        // Проверка успешной отправки
        String currentUrl = driver.getCurrentUrl();
        assertNotNull(currentUrl);
        assertTrue(currentUrl.contains("submitted-form.html"));
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Отправка формы c пустой текстовой областью")
    @Tag("NavigationExample")
    void textAreaNullTest() throws InterruptedException {
        elementHelper.getElement(WebFormLocators.TEXTAREA).sendKeys("");
        elementHelper.clickSubmitButton(LocatorType.CSS_SELECTOR, "button[type='submit']");
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.urlContains("submitted-form.html"));
        // Проверка успешной отправки
        String currentUrl = driver.getCurrentUrl();
        assertNotNull(currentUrl);
        assertTrue(currentUrl.contains("submitted-form.html"));
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Проверка скролла текстовой области")
    @Tag("NavigationExample")
    void textAreaScrollTest() {
        String textLong = fake.lorem().paragraph(100);
        WebElement textArea = elementHelper.getElement(WebFormLocators.TEXTAREA);
        textArea.sendKeys(textLong);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", textArea);
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Отправка формы. Поле текстовое макс длина")
    @Tag("NavigationExample")
    void textAreaMaxLengthTest() {
        // Находим поле пароля
        WebElement textArea = elementHelper.getElement(WebFormLocators.TEXTAREA);
        // Получаем значение атрибута maxlength
        String maxLength = textArea.getDomAttribute("maxlength");
        if (maxLength == null) {
            System.out.println("Атрибут maxlength не задан — поле не ограничено по длине.");
            return;
        }

        int maxLengthInt = Integer.parseInt(maxLength);

        String longText = "A".repeat(maxLengthInt+20);
        // Вводим текст в поле
        textArea.clear();
        textArea.sendKeys(longText);

        // Проверяем, что значение в поле обрезано до maxlength
        String actualValue = textArea.getDomProperty("value");
        assertNotNull(actualValue);
        assertEquals(maxLengthInt, actualValue.length(), "Ожидалось, что длина текста в поле будет равна max length");
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Невозможность ввести текст/знаки в заблокированное поле")
    @Tag("NavigationExample")
    void disabledInputTest() {
        WebElement disabledInput = elementHelper.getElement(WebFormLocators.DISABLED_INPUT);
        assertFalse(disabledInput.isEnabled(), "Поле должно быть заблокировано");
        assertEquals("Disabled input", disabledInput.getDomAttribute("placeholder"),
                "Неправильный placeholder");
        assertThrows(ElementNotInteractableException.class, () -> disabledInput.sendKeys("uytiyt"));
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Невозможность ввести текст/знаки в readonly поле")
    @Tag("NavigationExample")
    void readonlyInputTest() {
        WebElement readonlyInput = elementHelper.getElement(WebFormLocators.READONLY_INPUT);
        String readonly = readonlyInput.getDomAttribute("readonly");
        assertTrue(readonlyInput.isEnabled(), "Поле не должно быть отключено - оно ридонли!");
        assertNotNull(readonly);
        readonlyInput.sendKeys("jhgh");
        assertEquals("Readonly input", readonlyInput.getDomProperty("value"),
                "Readonly поле не должно изменяться");
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Проверка копирования текста из readonly")
    @Tag("NavigationExample")
    void copyReadonlyTest() {
        //Копируем текст
        WebElement source = elementHelper.getElement(WebFormLocators.READONLY_INPUT);
        String copiedText = source.getText();
        //Вставляем текст
        WebElement target = elementHelper.getElement(WebFormLocators.TEXTAREA);
        target.clear();              // очищаем поле
        target.sendKeys(copiedText); // вставляем текст
        assertEquals(copiedText, target.getDomProperty("value"));
    }

    //Тесты на дропдаун и файл меню лежат в LocatorTypesTests

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Проверка dropdown (datalist)")
    @Tag("NavigationExample")
    void dropdownDataListTest() {
        WebElement dropdown = elementHelper.getElement(WebFormLocators.DROPDOWN);
        // Вводим первую букву
        dropdown.sendKeys("S");
        // Получаем все варианты из datalist
        List<WebElement> options = driver.findElements(By.cssSelector("#my-options option"));
        // Проверяем, что среди вариантов есть Seattle
        assertTrue(options.stream()
                        .anyMatch(o -> Objects.equals(o.getDomAttribute("value"), "Seattle")),
                "В списке должен присутствовать вариант Seattle");
        // Вводим полный текст варианта
        dropdown.clear();
        dropdown.sendKeys("Seattle");
        // Проверяем, что значение установлено
        String selectedValue = dropdown.getDomProperty("value");
        assertEquals("Seattle", selectedValue, "В поле должен отображаться выбранный вариант");
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Отправка формы с включенным чек-боксом Checked")
    @Tag("NavigationExample")
    void checkedIsEnabledTest() throws InterruptedException {
        WebElement checked = elementHelper.getElement(WebFormLocators.CHECKED_CHECKBOX);
        //Проверяем состояние чек-бокса
        if (!checked.isSelected()) {  // ! оператор отрицания (NOT)
            // Если выключен — включаем
            checked.click();
        }
        //Отправка формы
        elementHelper.clickSubmitButton(LocatorType.CSS_SELECTOR, "button[type='submit']");
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.urlContains("submitted-form.html"));
        // Проверка успешной отправки
        String currentUrl = driver.getCurrentUrl();
        assertNotNull(currentUrl);
        assertTrue(currentUrl.contains("submitted-form.html"));
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Отправка формы с включенным чек-боксом Checked")
    @Tag("NavigationExample")
    void checkedIsDisabledTest() throws InterruptedException {
        WebElement checked = elementHelper.getElement(WebFormLocators.CHECKED_CHECKBOX);
        //Проверяем состояние чек-бокса
        if (checked.isSelected()) {  // ! оператор отрицания (NOT)
            // Если включен — выключаем
            checked.click();
        }
        elementHelper.clickSubmitButton(LocatorType.CSS_SELECTOR, "button[type='submit']");
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.urlContains("submitted-form.html"));
        // Проверка успешной отправки
        String currentUrl = driver.getCurrentUrl();
        assertNotNull(currentUrl);
        assertTrue(currentUrl.contains("submitted-form.html"));
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Невозможность включения двух чекбоксов радио")
    @Tag("NavigationExample")
    void bothRadioCheckboxTest() {
        WebElement checkedRadio = elementHelper.getElement(WebFormLocators.CHECKED_RADIO);
        WebElement defaultRadio = elementHelper.getElement(WebFormLocators.DEFAULT_RADIO);
        //Проверяем состояние чек-бокса (оператор ^ XOR→ он проверяет, что ровно один из двух условий истинный)
        assertTrue(checkedRadio.isSelected() ^ defaultRadio.isSelected(), "Должен быть включен только один чек-бокс");
        //Проверяем checkedRadio
        safeClick(checkedRadio);
        assertTrue(checkedRadio.isSelected(), "checkedRadio должен быть включен");
        assertFalse(defaultRadio.isSelected(), "defaultRadio должен быть выключен");
        //Проверяем defaultRadio
        safeClick(defaultRadio);
        assertTrue(defaultRadio.isSelected(), "defaultRadio должен быть включен");
        assertFalse(checkedRadio.isSelected(), "checkedRadio должен быть выключен");
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Изменение цвета в Color picker")
    @Tag("NavigationExample")
    void changeColorTest() {
        WebElement colourPicker = elementHelper.getElement(WebFormLocators.COLOR_PICKER);
        //Проверяем начальное значение
        String initialColor = colourPicker.getDomAttribute("value");
        assertEquals("#563d7c", initialColor, "Цвето по умолчанию - сиреневый");
        //Установка нового значения
        colourPicker.sendKeys("#000000");
        String selectedColor = colourPicker.getDomProperty("value");
        assertEquals("#000000", selectedColor, "Цвет должен измениться на черный");
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Отправка формы с датой")
    @Tag("NavigationExample")
    void datePickerTest() throws InterruptedException {
        WebElement datePicker = elementHelper.getElement(WebFormLocators.DATE_PICKER);
        //Проверяем начальное значение
        String initialDate = datePicker.getDomProperty("value");
        assertEquals("", initialDate, "По умолчанию поле должно быть пустым");
        //Вводим корректную дату
        String validDate = "2025.08.17";// формат YYYY-MM-DD
        datePicker.sendKeys(validDate);
        //Проверяем, что значение установлено
        String selectedDate = datePicker.getDomProperty("value");
        assertEquals("2025.08.17", selectedDate, "Дата должна совпадать с введённой");
        //Отправка формы
        elementHelper.clickSubmitButton(LocatorType.CSS_SELECTOR, "button[type='submit']");
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.urlContains("submitted-form.html"));
        // Проверка успешной отправки
        String currentUrl = driver.getCurrentUrl();
        assertNotNull(currentUrl);
        assertTrue(currentUrl.contains("submitted-form.html"));
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Отправка формы с датой")
    @Tag("NavigationExample")
    void datePickerTest2() {
        String dateFormat = "MM/dd/yyyy";
        WebElement datePicker = elementHelper.getElement(WebFormLocators.DATE_PICKER);
        assertTrue(Objects.requireNonNull(datePicker.getDomProperty("value")).isEmpty(), "Some date is selected");

        String date = LocalDate.now().format(DateTimeFormatter.ofPattern(dateFormat));
        datePicker.sendKeys(date);
        datePicker.sendKeys(Keys.TAB);
        assertEquals(date, datePicker.getDomProperty("value"),
                "Date is not equal to the entered one");

        datePicker.click();
        LocalDate newDate = LocalDate.now().plusDays(1);
        //handling the case when next day is the next month, locator is different
        /*
        Определяем класс ячейки
        getMonth().maxLength() — максимальное количество дней в месяце (например, 30).
getDayOfMonth() — сегодняшний день месяца.
Если сегодня последний день месяца → используем класс "new day".
Иначе → "day". 👉 Это нужно, потому что календарь может по-разному размечать ячейки.
         */
        String xpathClassName = LocalDate.now().getMonth().maxLength() ==
                LocalDate.now().getDayOfMonth() ? "new day"  : "day";
        /*
        формируем xpath
        String.format(...) — подставляем значения в строку
        Получаем XPath вида: //td[@class = 'day' and text()=22] (если завтра 22 число).
         */
        String xpath = String.format(
                "//td[@class = '%s' and text()=%d]",
                xpathClassName,
                newDate.getDayOfMonth());
        //Находим ячейку календаря
        WebElement dateToSelect = driver.findElement(By.xpath(xpath));
        dateToSelect.click();
        assertEquals(newDate.format(DateTimeFormatter.ofPattern(dateFormat)),
                datePicker.getDomProperty("value"), "IncorrectDateSelected");
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Отправка формы с выбранным значением Example range")
    @Tag("NavigationExample")
    void exampleRangeTest() {
        WebElement range = elementHelper.getElement(WebFormLocators.EXAMPLE_RANGE);
        // Проверка начального значения
        // Проверка начального значения
        assertEquals("5", range.getDomAttribute("value"), "Значение по умолчанию должно быть 5");
        // Проверка минимального значения
        assertEquals("0", range.getDomAttribute("min"), "Минимальное значение должно быть 0");
        // Проверка максимального значения
        assertEquals("10", range.getDomAttribute("max"), "Максимальное значение должно быть 10");
        // Проверка шага
        assertEquals("1", range.getDomAttribute("step"), "Шаг должен быть равен 1");
        // Установка нового значения
        range.sendKeys("7");
        //далее должна быть такая проверка
        //assertEquals("7", range.getAttribute("value"), "Значение должно измениться на 7");
        //но тк сайт сломан - ее не будет

        //Проверка, что можно двигать мышкой
        int width = range.getSize().getWidth();//range.getSize() возвращает размер элемента (ширину и высоту)
        // .getWidth() берёт ширину.
        int x = range.getLocation().getX();//range.getLocation() возвращает координаты верхнего левого угла элемента
        int y = range.getLocation().getY();
        for (int i = 0; i < 10; i++) {
            new Actions(driver)
                    .moveToElement(range)
                    .clickAndHold()//нажимаем и держим кнопку мыши
                    .moveToLocation(x + width / 10 * i, y)//делим ширину на 10 частей и двигаем
                    // на i‑ую часть, y — остаёмся на той же вертикали.
                    .release()//отпускаем кнопку мыши
                    .perform();
            assertEquals(String.valueOf(i), range.getDomProperty("value"));//превращаем число i в строку
        }
    }
}
