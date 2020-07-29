package utils;

import org.jbehave.core.annotations.Then;
import org.jbehave.core.model.ExamplesTable;

import java.util.Map;

import static utils.AbstractStepsHolder.evalVariable;

public class UtilScenario {
    UtilScenarioSteps utilScenarioSteps = new UtilScenarioSteps();

    @Then("выполняется проверка равенства значений: $values")
    public void stepCheckValues(ExamplesTable values) {
        for (Map<String, String> row : values.getRows()) {
            String value1 = row.get("value1");
            String value2 = row.get("value2");
            value1 = evalVariable(value1, values);
            value2 = evalVariable(value2, values);
            utilScenarioSteps.checkValues(value1, value2);
        }

    }
}
