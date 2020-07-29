package steps.redis;

import objects.RedisPoolManager;
import org.jbehave.core.annotations.When;

import static utils.AbstractStepsHolder.*;

public class RedisSteps {
    RedisScenarioStep redisScenarioStep = new RedisScenarioStep();

    @When("в переменную \"$value\" записывается значение из БД Redis \"$redis\" для ключа \"$key\"")
    public void stepConnectToRedis(String value, String redis, String key) {
        key = evalVariable(key);
        setVariable(value, redisScenarioStep.getJedisValue(key, RedisPoolManager.getConnection(redis)));
    }

    @When("считать значение из переменной \"$redisValue\" по {ключу|индексу} \"$key\" и записать в переменную \"$value\"")
    public void stepRedisGetValueByKey(String redisValue, String key, String value) {
        key = key.equals("0") ? key : evalVariable(key);
        Object tmpValue = getVariable(redisValue);
        setVariable(value, redisScenarioStep.stepRedis(tmpValue, key));
    }
}
