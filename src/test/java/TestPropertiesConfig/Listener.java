package TestPropertiesConfig;

import io.qameta.allure.listener.StepLifecycleListener;
import io.qameta.allure.model.StepResult;

public class Listener implements StepLifecycleListener {

    @Override
    public void beforeStepStart(StepResult result) {
        if (result.getName().toLowerCase().contains("url") ||
                result.getName().toLowerCase().contains("password") ||
                result.getName().toLowerCase().contains("login")) {
            result.getParameters().clear();
        }
    }
}
