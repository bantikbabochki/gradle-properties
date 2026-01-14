package base;

import TestPropertiesConfig.TestConfig;
import TestPropertiesConfig.TestPropConfig;
import io.github.bonigarcia.wdm.WebDriverManager;
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
        String jsonSecret = System.getenv("TEST_PROPERTIES_CONTENT");  // ← ИСПРАВЛЕНО!

        System.out.println("=== CONFIG INIT DEBUG ===");
        System.out.println("TEST_PROPERTIES_CONTENT exists: " + (jsonSecret != null));
        if (jsonSecret != null) {
            System.out.println("JSON length: " + jsonSecret.length());
            System.out.println("JSON preview: " + jsonSecret.substring(0, Math.min(100, jsonSecret.length())));
        }

        if (jsonSecret != null && !jsonSecret.trim().isEmpty()) {
            try {
                // Парсим JSON в Properties
                Properties properties = TestConfig.parseJsonConfig(jsonSecret);

                System.out.println("Parsed properties count: " + properties.size());
                System.out.println("baseUrl from properties: [" + properties.getProperty("baseUrl") + "]");

                // Создаем директорию
                File tempDir = new File("build/tmp");
                tempDir.mkdirs();

                // Сохраняем во временный файл
                File temp = new File("build/tmp/ci.properties");  // ← ИСПРАВЛЕНО!

                try (FileOutputStream out = new FileOutputStream(temp)) {
                    properties.store(out, "Generated from TEST_PROPERTIES_CONTENT");
                }

                System.out.println("ci.properties created at: " + temp.getAbsolutePath());
                System.out.println("ci.properties exists: " + temp.exists());

            } catch (Exception e) {
                System.err.println("ERROR parsing TEST_PROPERTIES_CONTENT: " + e.getMessage());
                e.printStackTrace();
                throw new RuntimeException("Failed to parse TEST_PROPERTIES_CONTENT", e);
            }
        } else {
            System.out.println("No TEST_PROPERTIES_CONTENT found, using default properties");
        }

        // Инициализируем Owner
        config = ConfigFactory.create(TestPropConfig.class, System.getProperties());

        // Проверяем что config работает
        System.out.println("Config baseUrl: [" + config.getBaseUrl() + "]");
        System.out.println("Config dropdownMenuUrl: [" + config.getDropdownMenuUrl() + "]");
        System.out.println("=========================");
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
                Allure.addAttachment("Remote run", "Using Selenium at: " + remoteUrl);
                driver = new RemoteWebDriver(new URI(remoteUrl).toURL(), options);
            } catch (URISyntaxException | MalformedURLException e) {
                throw new RuntimeException("Malformed URL for Selenium Remote WebDriver" + remoteUrl, e);
            }
        } else {
            Allure.addAttachment("Local run", "Using local ChromeDriver");
            WebDriverManager.chromedriver().setup();  // ← ДОБАВЛЕНО!

            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless");
            options.addArguments("--disable-gpu");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");

            driver = new ChromeDriver(options);
        }
    }
}
