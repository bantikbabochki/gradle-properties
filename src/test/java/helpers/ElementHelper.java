package helpers;

import enums.LocatorType;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ElementHelper {
    private final WebDriver driver;

    public ElementHelper(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Getting current URL")
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    @Step("Проверка, что элемент отображается: тип={type}, значение={value}")
    public void checkElementsVisible(LocatorType type, String value) {
        By locator = switch (type) {
            case ID -> By.id(value);//то же что и case ID: locator=By.id(value); break;
            case NAME -> By.name(value);
            case CLASS_NAME -> By.className(value);
            case XPATH -> By.xpath(value);
            case CSS_SELECTOR -> By.cssSelector(value);
            case TAG_NAME -> By.tagName(value);
            case LINK_TEXT -> By.linkText(value);
            case PARTIAL_LINK_TEXT -> By.partialLinkText(value);
            default -> throw new IllegalArgumentException("Неподдерживаемый тип локатора" + type);
        };
        try {
            WebElement element = driver.findElement(locator);
            assertTrue(element.isDisplayed(), "Элемент найден, но не отображается");
        } catch (NoSuchElementException e) {
            throw new AssertionError("Элемент не найден: тип=" + type + ", значение =" + value, e);
        }
    }

    @Step("Получение отображаемого элемента: тип={type}, значение={value}")
    public WebElement getVisibleElement(LocatorType type, String value) {
        By locator = switch (type) {
            case ID -> By.id(value);//то же что и case ID: locator=By.id(value); break;
            case NAME -> By.name(value);
            case CLASS_NAME -> By.className(value);
            case XPATH -> By.xpath(value);
            case CSS_SELECTOR -> By.cssSelector(value);
            case TAG_NAME -> By.tagName(value);
            case LINK_TEXT -> By.linkText(value);
            case PARTIAL_LINK_TEXT -> By.partialLinkText(value);
            default -> throw new IllegalArgumentException("Неподдерживаемый тип локатора" + type);
        };
        try {
            WebElement element = driver.findElement(locator);
            if (!element.isDisplayed()) {
                System.out.println("Элемент найден, но пока не отображается: " + locator);
            }
            return element;
        } catch (NoSuchElementException e) {
            throw new AssertionError("Элемент не найден: тип=" + type + ", значение =" + value, e);
        }
    }

    @Step("Проверка, что элемент уникален: тип={type}, значение={value}")
    public void checkElementsUnique(LocatorType type, String value) {
        By locator = switch (type) {
            case ID -> By.id(value);//то же что и case ID: locator=By.id(value); break;
            case NAME -> By.name(value);
            case CLASS_NAME -> By.className(value);
            case XPATH -> By.xpath(value);
            case CSS_SELECTOR -> By.cssSelector(value);
            case TAG_NAME -> By.tagName(value);
            case LINK_TEXT -> By.linkText(value);
            case PARTIAL_LINK_TEXT -> By.partialLinkText(value);
            default -> throw new IllegalArgumentException("Неподдерживаемый тип локатора" + type);
        };
        List<WebElement> elements = driver.findElements(locator);

        if (elements.size() != 1) {
            String message = elements.isEmpty()
                    ? "Элемент не найден: тип=" + type + ", значение=" + value
                    : "Найдено несколько элементов (" + elements.size() + ") по локатору: тип=" + type + ", значение=" + value;
            throw new AssertionError(message);
        }
        /*Или второй вариант
        if (elements.size() != 1) {
    String message = switch (elements.size()) {
        case 0 -> "Элемент не найден: тип=" + type + ", значение=" + value;
        default -> "Найдено несколько элементов (" + elements.size() + ") по локатору: тип=" + type + ", значение=" + value;
    };
    throw new AssertionError(message);
}
         */
    }

    @Step("Нажатие на кнопку отправки формы: тип={type}, значение={value}")
    public WebElement clickSubmitButton(LocatorType type, String value) throws InterruptedException {
        Thread.sleep(100);
        WebElement submitButton = getVisibleElement(type, value);
        submitButton.click();
        return submitButton;
    }

    /**
     * WebFormLocators
     */

    @Step("Получение отображаемого элемента")
    public WebElement getElement(By locator) {
        try {
            WebElement element = driver.findElement(locator);
            assertTrue(element.isDisplayed(), "Элемент найден, но не отображается");
            return element;
        } catch (NoSuchElementException e) {
            throw new AssertionError("Элемент не найден: " + locator, e);
        }
    }
}
