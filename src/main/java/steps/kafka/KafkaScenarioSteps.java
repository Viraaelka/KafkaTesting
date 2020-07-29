package steps.kafka;

import objects.Records;
import ru.yandex.qatools.allure.annotations.Step;
import utils.TestProperties;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

public class KafkaScenarioSteps {

    private static Records records;

    @Step("подключаемся к кафке и подписываемся на топики {0}")
    public void connectToKafka(String commaSeparatedTopics) {
        Properties props = new Properties();
        List<String> kafkaValues = TestProperties.getInstance().getProperties().stringPropertyNames().stream()
                .filter(key -> key.contains("kafka")).collect(Collectors.toList());
        kafkaValues.forEach(key -> props.setProperty(key.replace("kafka.", ""), TestProperties.getInstance().getProperties().getProperty(key)));
        records = new Records(props, commaSeparatedTopics);
    }

    @Step("ловим новое сообщение в топике {0}")
    public void runKafkaAndGetMessages() {
        records.runKafkaAndGetMessages();
    }

    @Step("в переменную {0} записывается сообщение из топика {1} для брокера, которое содержит параметры: {2}")
    public void checkMessageFromTopic(String topicName, Map<String, String> fields) {
        records.checkParameters(topicName, fields);
    }

    @Step("в переменную {0} записывается сообщение из топика {1} для брокера")
    public String getMessageFromKafkaQueue(String topic) {
        return records.getMessageFromKafkaQueue(topic);
    }
}
