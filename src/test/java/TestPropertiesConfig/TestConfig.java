package TestPropertiesConfig;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Iterator;
import java.util.Properties;

public class TestConfig {

    public static Properties parseJsonConfig(String json) {
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
                    testProperties.setProperty(fieldName, fieldValue.asText().trim());
                }
            }
            return testProperties;
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse TEST_PROPERTIES_CONTENT as JSON", e);
        }
    }
}
