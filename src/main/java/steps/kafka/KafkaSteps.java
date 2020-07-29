package steps.kafka;

import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.model.ExamplesTable;

import java.util.HashMap;
import java.util.Map;

import static utils.AbstractStepsHolder.evalVariable;
import static utils.AbstractStepsHolder.setVariable;

public class KafkaSteps {

    KafkaScenarioSteps kafkaScenarioSteps = new KafkaScenarioSteps();

    @When("подключаемся к кафке и подписываемся на топики \"$commaSeparatedTopics\"")
    public void connectToKafka(String commaSeparatedTopics) {
        kafkaScenarioSteps.connectToKafka(commaSeparatedTopics);
    }

    @When("ловим новые сообщения в топиках")
    public void runKafkaAndGetMessages() {
        kafkaScenarioSteps.runKafkaAndGetMessages();
    }

    @Then("в переменную \"$varName\" записывается сообщение из топика \"$topicName\" для брокера, которое содержит параметры: $fields")
    public void stepCheckTopic(String varName, String topicName, ExamplesTable fields) {
        System.out.println();
        HashMap<String, String> values = new HashMap<>();

        for (Map<String, String> row : fields.getRows()) {
            String key = row.get("key");
            String value = row.get("value");
            value = evalVariable(value, fields);
            values.put(key, value);
        }
        kafkaScenarioSteps.checkMessageFromTopic(topicName, values);
    }

    @Then("в переменную \"$varName\" записывается сообщение из топика \"$topicName\" для брокера")
    public void saveVariable(String varName, String topic) {
        String message = kafkaScenarioSteps.getMessageFromKafkaQueue(topic);
        System.out.println("message = " + message);
        setVariable(varName, message);
    }
}
