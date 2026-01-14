package helpers;

import enums.LocatorType;
import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ElementHelper {
    private final WebDriver driver;
    private final WebDriverWait wait;

    public ElementHelper(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
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
    public WebElement clickSubmitButton(LocatorType type, String value){
        WebElement submitButton = getVisibleElement(type, value);
        safeClick(submitButton);
        return submitButton;
    }

    /**
     * Безопасный клик с обработкой ElementClickInterceptedException
     * Сначала пробует обычный клик, если не получается - скроллит и кликает через JS
     */
    @Step("Безопасный клик на элемент")
    private void safeClick(WebElement element) {
        try {
            //Ждем, что элемент станет кликабельным
            wait.until(ExpectedConditions.elementToBeClickable(element));
            element.click();
        } catch (ElementClickInterceptedException | TimeoutException e) {
            //Если элемент перекрыт или не стал кликабельным - используем JS
            System.out.println("Обычный клик не сработал, использую JavaScript клик");
            scrollToElement(element);
        }
    }

    /**
     * Скролл к элементу
     */
    private void scrollToElement(WebElement element) {
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});",
                element
        );
        //Небольшая пауза после скролла
        try {
            Thread.sleep(300);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Клик через JavaScript
     */
    private void clickViaJavaScript(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
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

    /**
     * Безопасный клик для элементов, найденных через By локатор
     */
    @Step("Клик на элемент: {locator}")
    public void clickElement(By locator) {
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        safeClick(element);
    }
}
