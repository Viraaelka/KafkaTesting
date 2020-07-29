package utils;

import ru.yandex.qatools.allure.annotations.Attachment;
import ru.yandex.qatools.allure.annotations.Step;

import static org.junit.Assert.assertEquals;

public class UtilScenarioSteps {
    @Step("Сохранение вложения - {0}")
    @Attachment(value = "{0}", type = "text/xml")
    public String saveXmlAttach(String attachName, String html) {
        return html;
    }

    @Step("Сравнивается значение {0} со значением {1}")
    public void checkValues(String value1, String value2) {
        assertEquals(String.format("Значение [%s] не соответствует значению [%s]", value1, value2), value1, value2);
    }
}
