package base;

import TestPropertiesConfig.TestConfig;
import TestPropertiesConfig.TestPropConfig;
import io.qameta.allure.Allure;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import java.util.Map;
import java.util.Properties;

public abstract class BaseUITest {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected static TestPropConfig config;

    @BeforeAll
    static void initConfig() {
        String jsonSecret = System.getenv("EST_PROPERTIES_CONTENT");
        if (jsonSecret != null && !jsonSecret.trim().isEmpty()) {
            // Парсим JSON в Properties
            Properties properties = TestConfig.parseJsonConfig(jsonSecret);

            // 🔍 ВРЕМЕННАЯ ДИАГНОСТИКА — НАЧАЛО
            System.out.println("=== CONFIG DEBUG START ===");
            System.out.println("JSON length: " + jsonSecret.length());
            System.out.println("Parsed properties count: " + properties.size());
            properties.list(System.out); // выводит все ключ=значение
            System.out.println("=== CONFIG DEBUG END ===");
            // 🔍 ВРЕМЕННАЯ ДИАГНОСТИКА — КОНЕЦ

            // Сохраняем во временный файл
            File temp = new File("build/tmp");
            if (!temp.exists() && !temp.mkdirs()) {
                throw new RuntimeException("Failed to create directory: " + temp.getAbsolutePath());
            }
            File propFile = new File(temp, "ci.properties");
            try (FileOutputStream out = new FileOutputStream(propFile)) {
                properties.store(out, "Generated from TEST_PROPERTIES_CONTENT");
            } catch (IOException e) {
                throw new RuntimeException("Failed to write ci.properties", e);
            }

            // Передаём путь через системное свойство
            System.setProperty("config.file", propFile.getAbsolutePath());
        }

        // Инициализируем Owner
        config = ConfigFactory.create(TestPropConfig.class, System.getProperties());
    }

    @BeforeEach
    void setup() {
        initDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    private void initDriver() {
        String remoteUrl = System.getenv("SELENIUM_REMOTE_URL");
        if (remoteUrl != null && !remoteUrl.isEmpty()) {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless");  // Add headless mode
            options.addArguments("--disable-gpu"); // Switch off GPU, because we don't need it in headless mode
            options.addArguments("--no-sandbox"); // Switch off sandbox to prevent access rights issues
            options.addArguments("--disable-dev-shm-usage"); // Use /tmp instead of /dev/shm
            options.setCapability("goog:loggingPrefs", Map.of("browser", "ALL"));
            try {
                driver = new RemoteWebDriver(new URI(remoteUrl).toURL(), options);
            } catch (URISyntaxException | MalformedURLException e) {
                throw new RuntimeException("Malformed URL for Selenium Remote WebDriver" + remoteUrl, e);
            }
        } else {
            Allure.addAttachment("Local run", "No remote driver");
            driver = new ChromeDriver();
        }
    }
}
