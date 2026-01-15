package ui;

import base.BaseUITest;
import enums.LocatorType;
import helpers.ElementHelper;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class LocatorTypesTests extends BaseUITest {

    @BeforeEach
    void open() {
        driver.get(config.getWebFormUrl());
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Base Locators: ID")
    @Description("Base Locators")
    void baseIdLocator() {
        //Проверить что поле отображается
        elementHelper.checkElementsVisible(LocatorType.ID, "my-text-id");
        //Проверка, что локатор уникален
        elementHelper.checkElementsUnique(LocatorType.ID, "my-text-id");
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Base Locators: Class name")
    @Description("Base Locators")
    void baseClassNameLocator() {
        //Проверить что поле отображается
        elementHelper.checkElementsVisible(LocatorType.CLASS_NAME, "form-range");
        //Проверка, что локатор уникален
        elementHelper.checkElementsUnique(LocatorType.CLASS_NAME, "form-range");
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Base Locators: Name")
    @Description("Base Locators")
    void baseNameLocator() {
        //Проверить что поле отображается
        elementHelper.checkElementsVisible(LocatorType.NAME, "my-range");
        //Проверка, что локатор уникален
        elementHelper.checkElementsUnique(LocatorType.NAME, "my-range");
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Base Locators: Tag Name")
    @Description("Base Locators")
    void tagNameLocator() {
        //Проверить что поле отображается
        elementHelper.checkElementsVisible(LocatorType.TAG_NAME, "footer");
        //Проверка, что локатор уникален
        elementHelper.checkElementsUnique(LocatorType.TAG_NAME, "footer");
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Base Locators: Partial Link Text")
    @Description("Base Locators")
    void partialLinkTextLocator() {
        //Проверить что поле отображается
        elementHelper.checkElementsVisible(LocatorType.PARTIAL_LINK_TEXT, "Return to index");
        //Проверка, что локатор уникален
        elementHelper.checkElementsUnique(LocatorType.PARTIAL_LINK_TEXT, "Return to index");
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("CSS Locators: ID")
    @Description("CSS Locators")
    void cssIdLocator() {
        //Проверить что поле отображается
        elementHelper.checkElementsVisible(LocatorType.CSS_SELECTOR, "#my-check-1");
        //Проверка, что локатор уникален
        elementHelper.checkElementsUnique(LocatorType.CSS_SELECTOR, "#my-check-1");
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("CSS Locators: Class name")
    @Description("CSS Locators")
    void cssClassNameLocator() {
        //Проверить что поле отображается
        elementHelper.checkElementsVisible(LocatorType.CSS_SELECTOR, ".form-range");
        //Проверка, что локатор уникален
        elementHelper.checkElementsUnique(LocatorType.CSS_SELECTOR, ".form-range");
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("CSS Locators: Name")
    @Description("CSS Locators")
    void cssNameLocator() {
        //Проверить что поле отображается
        elementHelper.checkElementsVisible(LocatorType.CSS_SELECTOR, "[name = 'my-range']");
        //Проверка, что локатор уникален
        elementHelper.checkElementsUnique(LocatorType.CSS_SELECTOR, "[name = 'my-range']");
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("CSS Locators: Tag")
    @Description("CSS Locators")
    void cssTagLocator() {
        //Проверить что поле отображается
        elementHelper.checkElementsVisible(LocatorType.CSS_SELECTOR, "footer");
        //Проверка, что локатор уникален
        elementHelper.checkElementsUnique(LocatorType.CSS_SELECTOR, "footer");
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("CSS Locators: Attribute")
    @Description("CSS Locators")
    void cssAttributeLocator() {
        //Проверить что поле отображается
        elementHelper.checkElementsVisible(LocatorType.CSS_SELECTOR, "[href='./index.html']");
        //Проверка, что локатор уникален
        elementHelper.checkElementsUnique(LocatorType.CSS_SELECTOR, "[href='./index.html']");
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("CSS Locators: Tag + Class")
    @Description("CSS Locators")
    void cssTagAndClassLocator() {
        //Проверить что поле отображается
        elementHelper.checkElementsVisible(LocatorType.CSS_SELECTOR, "img.img-fluid");
        //Проверка, что локатор уникален
        elementHelper.checkElementsUnique(LocatorType.CSS_SELECTOR, "img.img-fluid");
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("CSS Locators: Tag + ID")
    @Description("CSS Locators")
    void cssTagAndIdLocator() {
        //Проверить что поле отображается
        elementHelper.checkElementsVisible(LocatorType.CSS_SELECTOR, "input#my-check-2");
        //Проверка, что локатор уникален
        elementHelper.checkElementsUnique(LocatorType.CSS_SELECTOR, "input#my-check-2");
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("CSS Locators: Tag + Attribute")
    @Description("CSS Locators")
    void cssTagAndAttributeLocator() {
        //Проверить что поле отображается
        elementHelper.checkElementsVisible(LocatorType.CSS_SELECTOR, "input[value='#563d7c'");
        //Проверка, что локатор уникален
        elementHelper.checkElementsUnique(LocatorType.CSS_SELECTOR, "input[value='#563d7c'");
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("CSS Locators: Class + Class")
    @Description("CSS Locators")
    void cssClassAndClassLocator() {
        //Проверить что поле отображается
        elementHelper.checkElementsVisible(LocatorType.CSS_SELECTOR, ".form-control.form-control-color");
        //Проверка, что локатор уникален
        elementHelper.checkElementsUnique(LocatorType.CSS_SELECTOR, ".form-control.form-control-color");
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("CSS Locators: Prefix")
    @Description("CSS Locators")
    void cssPrefixLocator() {
        //Проверить что поле отображается
        elementHelper.checkElementsVisible(LocatorType.CSS_SELECTOR, "[placeholder^='Di']");
        //Проверка, что локатор уникален
        elementHelper.checkElementsUnique(LocatorType.CSS_SELECTOR, "[placeholder^='Di']");
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("CSS Locators: Suffix")
    @Description("CSS Locators")
    void cssSuffixLocator() {
        //Проверить что поле отображается
        elementHelper.checkElementsVisible(LocatorType.CSS_SELECTOR, "[placeholder$='input']");
        //Проверка, что локатор уникален
        elementHelper.checkElementsUnique(LocatorType.CSS_SELECTOR, "[placeholder$='input']");
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("CSS Locators: Substring")
    @Description("CSS Locators")
    void cssSubstringLocator() {
        //Проверить что поле отображается
        elementHelper.checkElementsVisible(LocatorType.CSS_SELECTOR, "[placeholder*='led']");
        //Проверка, что локатор уникален
        elementHelper.checkElementsUnique(LocatorType.CSS_SELECTOR, "[placeholder*='led']");
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("CSS Locators: Exact Match")
    @Description("CSS Locators")
    void cssExactMatchLocator() {
        //Проверить что поле отображается
        elementHelper.checkElementsVisible(LocatorType.CSS_SELECTOR, "[placeholder*='Disabled input']");
        //Проверка, что локатор уникален
        elementHelper.checkElementsUnique(LocatorType.CSS_SELECTOR, "[placeholder*='Disabled input']");
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("CSS Locators: Child")
    @Description("CSS Locators")
    void cssChildLocator() {
        //Проверить что поле отображается
        elementHelper.checkElementsVisible(LocatorType.CSS_SELECTOR, "div.container span[class='text-muted']");
        //Проверка, что локатор уникален
        elementHelper.checkElementsUnique(LocatorType.CSS_SELECTOR, "div.container span[class='text-muted']");
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("CSS Locators: Nth-Child")
    @Description("CSS Locators")
    void cssNthChildLocator() {
        //Проверить что поле отображается
        elementHelper.checkElementsVisible(LocatorType.CSS_SELECTOR, "div.form-check label:nth-child(2)");
        //Проверка, что локатор уникален
        elementHelper.checkElementsUnique(LocatorType.CSS_SELECTOR, "div.form-check label:nth-child(2)");
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("XPath: Относительный путь")
    @Description("XPath Locators")
    void xpathLocator() {
        //Проверить что поле отображается
        elementHelper.checkElementsVisible(LocatorType.XPATH, "//label[1]/select");
        //Проверка, что локатор уникален
        elementHelper.checkElementsUnique(LocatorType.XPATH, "//label[1]/select");
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("XPath: Tag")
    @Description("XPath Locators")
    void xpathTagLocator() {
        //Проверить что поле отображается
        elementHelper.checkElementsVisible(LocatorType.XPATH, "//footer");
        //Проверка, что локатор уникален
        elementHelper.checkElementsUnique(LocatorType.XPATH, "//footer");
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("XPath: Text")
    @Description("XPath Locators")
    void xpathTextLocator() {
        //Проверить что поле отображается
        elementHelper.checkElementsVisible(LocatorType.XPATH, "//h1[text()='Hands-On Selenium WebDriver with Java']");
        //Проверка, что локатор уникален
        elementHelper.checkElementsUnique(LocatorType.XPATH, "//h1[text()='Hands-On Selenium WebDriver with Java']");
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("XPath: Contains Text")
    @Description("XPath Locators")
    void xpathContainsTextLocator() {
        //Проверить что поле отображается
        elementHelper.checkElementsVisible(LocatorType.XPATH, "//h1[contains(text(),'Selenium')]");
        //Проверка, что локатор уникален
        elementHelper.checkElementsUnique(LocatorType.XPATH, "//h1[contains(text(),'Selenium')]");
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("XPath: Attribute")
    @Description("XPath Locators")
    void xpathAttributeLocator() {
        //Проверить что поле отображается
        elementHelper.checkElementsVisible(LocatorType.XPATH, "//*[@src='img/hands-on-icon.png']");
        //Проверка, что локатор уникален
        elementHelper.checkElementsUnique(LocatorType.XPATH, "//*[@src='img/hands-on-icon.png']");
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("XPath: Child")
    @Description("XPath Locators")
    void xpathChildLocator() {
        //Проверить что поле отображается
        elementHelper.checkElementsVisible(LocatorType.XPATH, "//label/input[@name='my-date']");
        //Проверка, что локатор уникален
        elementHelper.checkElementsUnique(LocatorType.XPATH, "//label/input[@name='my-date']");
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("XPath: Parent")
    @Description("XPath Locators")
    void xpathParentLocator() {
        //Проверить что поле отображается
        elementHelper.checkElementsVisible(LocatorType.XPATH, "//input[@name='my-date']/..");
        //Проверка, что локатор уникален
        elementHelper.checkElementsUnique(LocatorType.XPATH, "//input[@name='my-date']/..");
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("XPath: Close Parent")
    @Description("XPath Locators")
    void xpathCloseParentLocator() {
        //Проверить что поле отображается
        elementHelper.checkElementsVisible(LocatorType.XPATH, "//h5[text()='Practice site']/parent::div");
        //Проверка, что локатор уникален
        elementHelper.checkElementsUnique(LocatorType.XPATH, "//h5[text()='Practice site']/parent::div");
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("XPath: Any Parent")
    @Description("XPath Locators")
    void xpathParentOrSelfLocator() {
        //Проверить что поле отображается
        elementHelper.checkElementsVisible(LocatorType.XPATH, "//h5[text()='Practice site']/ancestor-or-self::*[3]");
        //Проверка, что локатор уникален
        elementHelper.checkElementsUnique(LocatorType.XPATH, "//h5[text()='Practice site']/ancestor-or-self::*[3]");
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("XPath: Ancestors of the current node")
    @Description("XPath Locators")
    void xpathAncestorOfTheCurrentNodeLocator() {
        //Проверить что поле отображается
        elementHelper.checkElementsVisible(LocatorType.XPATH, "//h5[text()='Practice site']/ancestor::*[@class= 'container']");
        //Проверка, что локатор уникален
        elementHelper.checkElementsUnique(LocatorType.XPATH, "//h5[text()='Practice site']/ancestor::*[@class= 'container']");
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("XPath: Descendant")
    @Description("XPath Locators")
    void xpathDescendantLocator() {
        //Проверить что поле отображается
        elementHelper.checkElementsVisible(LocatorType.XPATH, "//div[@class='container']/descendant::h5");
        //Проверка, что локатор уникален
        elementHelper.checkElementsUnique(LocatorType.XPATH, "//div[@class='container']/descendant::h5");
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("XPath: Descendant-or-self")
    @Description("XPath Locators")
    void xpathDescendantOrSelfLocator() {
        //Проверить что поле отображается
        elementHelper.checkElementsVisible(LocatorType.XPATH, "//div[@class='container']/descendant-or-self::h5");
        //Проверка, что локатор уникален
        elementHelper.checkElementsUnique(LocatorType.XPATH, "//div[@class='container']/descendant-or-self::h5");
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("XPath: Following-sibling")
    @Description("XPath Locators")
    void xpathFollowingSiblingLocator() {
        //Проверить что поле отображается
        elementHelper.checkElementsVisible(LocatorType.XPATH, "//option/following-sibling::option");
        //Проверка, что локатор уникален
//        elementHelper.checkElementsUnique(LocatorType.XPATH, "//option/following-sibling::option");
        //можно еще указать какой по порядку, начиная с 0: //option/following-sibling::option[4];
    }

//    @Test
//    @Severity(SeverityLevel.NORMAL)
//    @DisplayName("XPath: Preceding-sibling")
//    @Description("XPath Locators")
//    void xpathPrecedingSiblingLocator() {
//        //Проверить что поле отображается
//        elementHelper.checkElementsVisible(LocatorType.XPATH, "//option/following-sibling::option[@value='New York']");
//        //Проверка, что локатор уникален
//        elementHelper.checkElementsUnique(LocatorType.XPATH, "//option/following-sibling::option[@value='New York']");
//        //можно еще указать какой по порядку, начиная с 0: //option/preceding-sibling::option[4];
//    }
//
//    @Test
//    @Severity(SeverityLevel.NORMAL)
//    @DisplayName("XPath: Following")
//    @Description("XPath Locators")
//    void xpathFollowingLocator() {
//        //Проверить что поле отображается
//        elementHelper.checkElementsVisible(LocatorType.XPATH, "//footer/following::*");
//        //Проверка, что локатор уникален
//        elementHelper.checkElementsUnique(LocatorType.XPATH, "//footer/following::*");
//    }
//
//    @Test
//    @Severity(SeverityLevel.NORMAL)
//    @DisplayName("XPath: Preceding")
//    @Description("XPath Locators")
//    void xpathPrecedingLocator() {
//        //Проверить что поле отображается
//        elementHelper.checkElementsVisible(LocatorType.XPATH, "//footer/preceding::*");
//        //Проверка, что локатор уникален
//        elementHelper.checkElementsUnique(LocatorType.XPATH, "//footer/preceding::*");
//    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("XPath: Smth And Smth")
    @Description("XPath Locators")
    void xpathSmthAndSmthLocator() {
        //Проверить что поле отображается
        elementHelper.checkElementsVisible(LocatorType.XPATH, "//input[@class='form-check-input' and @id='my-check-2']");
        //Проверка, что локатор уникален
        elementHelper.checkElementsUnique(LocatorType.XPATH, "//input[@class='form-check-input' and @id='my-check-2']");
    }

//    @Test
//    @Severity(SeverityLevel.NORMAL)
//    @DisplayName("XPath: Smth Or Smth")
//    @Description("XPath Locators")
//    void xpathSmthOrSmthLocator() {
//        //Проверить что поле отображается
//        elementHelper.checkElementsVisible(LocatorType.XPATH, "//option[@value='San Francisco' or @value='Seattle']");
//    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("XPath: Not Smth")
    @Description("XPath Locators")
    void xpathNotSmthLocator() {
        //Проверить что поле отображается
        elementHelper.checkElementsVisible(LocatorType.XPATH, "//input[not(@disabled)]");
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("XPath: Index (first element)")
    @Description("XPath Locators")
    void xpathIndexFirstElementLocator() {
        //Проверить что поле отображается
        elementHelper.checkElementsVisible(LocatorType.XPATH, "(//input)[2]");
        //Проверка, что локатор уникален
        elementHelper.checkElementsUnique(LocatorType.XPATH, "(//input)[2]");
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("XPath: Index (last element)")
    @Description("XPath Locators")
    void xpathIndexLastElementLocator() {
        //Проверить что поле отображается
        elementHelper.checkElementsVisible(LocatorType.XPATH, "(//div)[last()]");
        //Проверка, что локатор уникален
        elementHelper.checkElementsUnique(LocatorType.XPATH, "(//div)[last()]");
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("XPath: Index (Nth element with smth)")
    @Description("XPath Locators")
    void xpathIndexNthElementLocator() {
        //Проверить что поле отображается
        elementHelper.checkElementsVisible(LocatorType.XPATH, "(//label[@class='form-label w-100'])[4]");
        //Проверка, что локатор уникален
        elementHelper.checkElementsUnique(LocatorType.XPATH, "(//label[@class='form-label w-100'])[4]");
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("XPath: Normalize-space")
    @Description("XPath Locators")
    void xpathNormalizeSpaceLocator() {
        //Проверить что поле отображается
        elementHelper.checkElementsVisible(LocatorType.ID, "my-radio-1");
        //Проверка, что локатор уникален
        elementHelper.checkElementsUnique(LocatorType.ID, "my-radio-1");
    }
}
