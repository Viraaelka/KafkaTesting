package objects;

import objects.db.DbDefinition;
import objects.db.DbDefinitions;
import redis.clients.jedis.Jedis;

public class RedisPoolManager {
    private static Jedis jedis;
    private static DbDefinition def;

    public static Jedis getConnection(String redis) {

        if (jedis == null) {
            def = DbDefinitions.getDbDefinition(redis);
            jedis = new Jedis(def.getServer(), Integer.parseInt(def.getPort()));
            jedis.auth(def.getPassword());
        }
        return jedis;
    }
}
