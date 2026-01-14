package TestPropertiesConfig;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.StringReader;
import java.util.Iterator;
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
        // определяем формат конфига (JSON или .properties)
        String ciConfig = System.getenv("TEST_PROPERTIES_CONTENT");
        if (ciConfig != null && !ciConfig.trim().isEmpty()) {
            // Проверяем: если строка начинается с '{' — это JSON
            if (ciConfig.trim().startsWith("{")) {
                return parseJsonConfig(ciConfig);
            } else {
                return parsePropertiesConfig(ciConfig);
            }
        }
        //загрузка из файла
        try {
            testProperties.load(getClass().getClassLoader().getResourceAsStream(env + ".properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(String.format("Cannot open %s.properties", env));
        }
        return testProperties;
    }

    private Properties parseJsonConfig(String json) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(json);
            Properties testProperties = new Properties();

            // Итерация через Iterator<String>
            Iterator<String> fieldNames = node.fieldNames();
            while (fieldNames.hasNext()) {
                String fieldName = fieldNames.next();
                JsonNode fieldValue = node.get(fieldName);
                if (fieldValue != null) {
                    testProperties.setProperty(fieldName, fieldValue.asText());
                }
            }
            return testProperties;
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse TEST_PROPERTIES_CONTENT as JSON", e);
        }
    }

    Properties parsePropertiesConfig(String content) {
        try {
            Properties testProperties = new Properties();
            testProperties.load(new StringReader(content));
            return testProperties;
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse TEST_PROPERTIES_CONTENT as .properties", e);
        }
    }
}
