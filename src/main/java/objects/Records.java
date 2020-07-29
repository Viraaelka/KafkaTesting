package objects;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.json.JSONObject;
import org.junit.Assert;

import java.time.Duration;
import java.util.*;


public class Records {

    private final KafkaConsumer<String, String> consumer;
    private final HashMap<String, ArrayList<ConsumerRecord<String, String>>> allTopicsRecords = new HashMap<>();

    public void runKafkaAndGetMessages() {
        ConsumerRecords<String, String> records;
        int count = 0;
        while (count < 5) {
            System.out.println(count + " *** got it");
            records = consumer.poll(Duration.ofMillis(500));
//                //    rec = CC_CREATE_HANDLING
//                //    rec = CC_CREATE_HANDLING_RESPONSE
//            for (ConsumerRecord<String, String> record : records)
//                System.out.printf("offset = %d, key = %s, value = %s\n",
//                        record.offset(), record.key(), record.value());
            records.forEach(record -> allTopicsRecords
                    .computeIfAbsent(record.topic(), k -> new ArrayList<>())
                    .add(record));
            count++;

        }
    }

    public Records(Properties props, String commaSeparatedTopics) {
        List<String> topics = Arrays.asList(commaSeparatedTopics.split("\\s*,\\s*"));
        consumer = new KafkaConsumer<>(props);
        consumer.subscribe(topics);
    }

    public void checkParameters(String topic, Map<String, String> values) {
//        System.out.println("=================================");
        List<ConsumerRecord<String, String>> recordsList = allTopicsRecords.get(topic);
//        System.out.println("size = " + recordsList.size());
//        recordsList.forEach(System.out::println); // ConsumerRecord(topic = CC_CREATE_HANDLING, partition = 0, leaderEpoch = 0, offset = 763, CreateTime = 1595532351083, serialized key size = 73, serialized value size = 818, headers = RecordHeaders(headers = [], isReadOnly = false), key = a04d8a90-84f5-436b-8d28-43fed389d978_202017f5-290b-470f-8559-b33aaeff2a71, value = {"CallAdminInteractionType":null,"CallAdminInteractionId":null,"Msisdn":"79001247683","VdnIvr":"70461","ServiceChannelId":"2e6f7c14-ac89-e811-8110-00155df43732","InteractionDirectionId":"9a4b3e83-47b7-e811-8112-00155df43732","ClientId":59734090,"ClientBranchId":3,"ClientTypeId":1,"ClientJurTypeId":1,"ClientServiceMethodId":"COTPYDHUK_TELE2","ClientCategoryId":"eac4997b-933d-e811-8119-001dd8b71d47","ClientStatusId":1,"PersonalAccount":63956973,"LastIvrNode":"Другие услуги","ServiceLineId":2,"ServiceLineName":"611","CallAdminRegionName":null,"HandlingBranchId":3,"SystemId":null,"CallCenterCode":"","SalePointId":null,"CreatedBy":"t2ru\\supp.aplana27","OpenedOn":"2020-07-23T19:25:51.0908171Z","HandlingTechId":"a04d8a90-84f5-436b-8d28-43fed389d978","LinkedHandlingId":null,"LinkedHandlingTechId":null})
//        System.out.println("=================================");
        if (recordsList.isEmpty())
            throw new NullPointerException(String.format("Не удалось отловить сообщение в топике [%s]", topic));

        recordsList.forEach(record -> {
            final Map<String, Object> recordMap = new JSONObject(record.value()).toMap();
//            System.out.println("recordMap = " + recordMap);
            values.keySet().forEach(key -> {
                String recordValue2;
                if (recordMap.get(key) != null)
                    recordValue2 = recordMap.get(key).toString();
                else
                    recordValue2 = "null";
//                System.out.println("*** Compare for (" + key + "): (" + values.get(key) + ") - (" + recordValue2 + ")");
                Assert.assertEquals("Набор полей для значения [" + key + "] не соответствует переданным входным параметрам", values.get(key), recordValue2);
            });
        });
        System.out.println(" *** SUPEEEEEEEEEEEEEER ***");
    }

    public String getMessageFromKafkaQueue(String topic) {
        return allTopicsRecords.get(topic).stream().filter(k -> k.topic().equals(topic)).findFirst().get().value();
    }
}

