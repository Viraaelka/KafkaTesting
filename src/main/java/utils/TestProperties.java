package utils;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.junit.Assert;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

// @Slf4j
public class TestProperties {
    private static TestProperties INSTANCE = null;
    private final Properties properties = new Properties();

    private TestProperties() {
        try {
            String env = System.getProperty("environment");
            Assert.assertNotNull("Не задан параметр -Denvironment", env);
            InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(env + ".properties");
            properties.load(new InputStreamReader(in, "windows-1251"));
            properties.setProperty("auth.password", new String(Base64.decode(properties.getProperty("auth.password"))));
            if (properties.getProperty("db.password") != null)
                properties.setProperty("db.password", new String(Base64.decode(properties.getProperty("db.password"))));
        } catch (IOException e) {
          //  log.error(e.toString()); // @Slf4j
        }
    }

    public static TestProperties getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new TestProperties();
        }
        return INSTANCE;
    }

    public Properties getProperties() {
        return properties;
    }
}
