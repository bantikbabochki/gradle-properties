package TestPropertiesConfig;

import constants.Constants;
import org.aeonbits.owner.Config;

@Config.Sources({
        "classpath:ci.properties",
        "classpath:${env}.properties",
        "classpath:test.properties"
})
public interface TestPropConfig extends Config {

    @Key("baseUrl")
    @DefaultValue(Constants.BASE_URL)
    String getBaseUrl();

    @Key("webFormUrl")
    String getWebFormUrl();

    @Key("navigationUrl")
    String getNavigationUrl();

    @Key("dropdownMenuUrl")
    String getDropdownMenuUrl();

    @Key("mouseOverUrl")
    String getMouseOverUrl();

    @Key("dragAndDropUrl")
    String getDragAndDropUrl();

    @Key("loadingImagesUrl")
    String getLoadingImagesUrl();

    @Key("longPageUrl")
    String getLongPageUrl();

    @Key("shadowDomUrl")
    String getShadowDomUrl();

    @Key("cookiesUrl")
    String getCookiesUrl();

    @Key("iframesUrl")
    String getIframesUrl();

    @Key("dialogBoxesUrl")
    String getDialogBoxesUrl();

    @Key("webStorageUrl")
    String getWebStorageUrl();

    @Key("infiniteScrollUrl")
    String getInfiniteScrollUrl();

    @Key("slowCalculatorUrl")
    String getSlowCalculatorUrl();

    @Key("username")
    String getUsername();

    @Key("password")
    String getPassword();
}