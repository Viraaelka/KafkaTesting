package steps.rest;

import org.jbehave.core.annotations.When;
import org.jbehave.core.model.ExamplesTable;
import utils.UtilScenarioSteps;

import java.io.IOException;
import java.util.Map;

import static steps.rest.ServicesDefinitions.getServicesDefinition;
import static utils.AbstractStepsHolder.evalVariable;
import static utils.AbstractStepsHolder.setVariable;

public class RestStep {
    UtilScenarioSteps utilScenarioSteps = new UtilScenarioSteps();

    @When("в переменную \"$variableName\" записывается результат Get запроса к сервису \"$serviceName\" на выполнение метода \"$methodName\" c параметрами: $fields")
    public void stepExecuteGetRestRequestWithParameters(String variableName, String serviceName, String methodName, ExamplesTable fields) {
        serviceName = evalVariable(serviceName);
        methodName = evalVariable(methodName);

        ServicesDefinition def = getServicesDefinition(serviceName);

        String separator = (def.getPort().equals("")) ? "" : ":";
        String questionMark = (fields.getRows().size() == 0) ? "" : "?";

        String url = def.getServer() + separator + def.getPort() + def.getApiMethods().getMethodUrl(methodName) + questionMark;
        String amp = "";
        String urlParameters = "";

        for (Map<String, String> row : fields.getRows()) {
            String parameterName = row.get("parameterName");
            String value = row.get("value");
            value = evalVariable(value, fields);
            urlParameters += amp + parameterName + "=" + value.trim();
            amp = "&";
        }

        url += urlParameters.replace(" ", "%20");
        System.out.println("URL: " + url);
        utilScenarioSteps.saveXmlAttach("Запрос: ", url);
        SendRestRequestManager requestManager = new SendRestRequestManager();
        String result = null;
        try {
            result = requestManager.request(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("RESPONSE: " + result);
        utilScenarioSteps.saveXmlAttach("Ответ: ", result);
        setVariable(variableName, result);
    }
}
