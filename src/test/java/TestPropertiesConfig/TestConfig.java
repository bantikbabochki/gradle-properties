package TestPropertiesConfig;

import java.io.IOException;
import java.io.StringReader;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestConfig {
    String env;
    Properties properties;

    public TestConfig() {
        env = System.getProperty("env", "default");
        properties = getPropertiesByEnv(env);
    }

    private String getFieldByName(String fieldName) {
        String field = properties.getProperty(fieldName);
        if (field == null || field.isEmpty()) {
            field = System.getProperty(fieldName, field);
        }
        assertNotNull(field, String.format("%s is not found in %s.properties and not set by system properties", fieldName, env));
        System.out.printf("%s: %s%n", fieldName, field);
        return field;
    }

    private Properties getPropertiesByEnv(String env) {
        Properties testProperties = new Properties();
        // Сначала попробуем загрузить из секрета (переменная окружения)
        String ciConfig = System.getenv("TEST_PROPERTIES_CONTENT");
        if (ciConfig != null && !ciConfig.trim().isEmpty()) {
            try {
                testProperties.load(new StringReader(ciConfig));
                return testProperties;
            } catch (IOException e) {
                throw new RuntimeException("Failed to parse TEST_PROPERTIES_CONTENT", e);
            }
        }
        //если конфиг пустой или null
        try {
            testProperties.load(getClass().getClassLoader().getResourceAsStream(env + ".properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(String.format("Cannot open %s.properties", env));
        }
        return testProperties;
    }
}
