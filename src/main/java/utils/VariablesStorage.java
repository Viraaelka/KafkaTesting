package utils;

import java.util.HashMap;
import java.util.Map;

public enum VariablesStorage {
    VARIABLES;


    private final HashMap<Object, Object> storage;

    VariablesStorage() {
        storage = new HashMap<Object, Object>() {
            {
                putAll(TestProperties.getInstance().getProperties());
            }
        };

    }

    public Map<Object, Object> get() {
        return storage;
    }
}
