package steps.redis;

import org.junit.Assert;
import redis.clients.jedis.Jedis;
import ru.yandex.qatools.allure.annotations.Step;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.fail;

public class RedisScenarioStep {

    @Step("Выполнен запрос по ключу {0}")
    public Object getJedisValue(String key, Jedis jedis) {
        switch (jedis.type(key)) {
            case "hash":
                return jedis.hgetAll(key);
            case "list":
                return jedis.lrange(key, 0, jedis.llen(key) - 1);
            case "string":
                return jedis.get(key);
            case "set":
            case "zset":
                return jedis.smembers(key);
            case "none":
                fail("Не найден ключ: " + key);
            default:
                fail("Тип данных не определён: " + jedis.type(key));
        }
        return null;
    }

    @Step("считать значение из переменной {0} и записать в переменную {1}")
    public String stepRedis(Object tmpValue, String key) {
        if (tmpValue instanceof Map)
            return getMap(((Map<String, String>) tmpValue), key);
        else if (tmpValue instanceof List)
            return getList((List<String>) tmpValue, key);
        Assert.fail("Не удалось определить тип объекта Редиса [%s] по ключу [%s]");
        return "";
    }

//        if (redisValue.contains("="))
//            return getJsonOfArray(redisValue, key);
//        else
//            return getStringWithNoBrackets(redisValue);

    //    private String getJsonOfArray(String redisValue, String key) {
//        return Arrays.stream(redisValue.split("},")).filter(k -> k.contains(key)).findFirst().get().split("=")[1].replace("}}", "}");
//    }
//
//    private String getStringWithNoBrackets(String redisValue) {
//        return redisValue.replaceAll("\\[(.*)]", "$1");
//    }
    private String getMap(Map<String, String> value, String key) {
        return value.get(key);
    }

    private String getList(List<String> value, String index) {
        return value.get(Integer.parseInt(index));
    }
}