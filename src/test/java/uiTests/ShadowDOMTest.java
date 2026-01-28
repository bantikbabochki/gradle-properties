package uiTests;

import base.BaseUITest;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ShadowDOMTest extends BaseUITest {

    @Test
    void shadowTest() {
        driver.get(config.getShadowDomUrl());
        WebElement content = driver.findElement(By.xpath("//div[@class = 'col-12 py-2' and @id = 'content']"));
        SearchContext shadowRoot = content.getShadowRoot();
        WebElement text = shadowRoot.findElement(By.cssSelector("p"));
        assertEquals("Hello Shadow DOM", text.getText(), "Неверный текст");
        assertTrue(text.isDisplayed(), "Текст не отображается");
    }
}
