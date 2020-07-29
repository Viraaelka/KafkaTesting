package objects.db;

import utils.TestProperties;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.InputStreamReader;
import java.util.List;

@XmlRootElement
public class DbDefinitions {

    @XmlElement
    private List<DbDefinition> dbDefinitionsList;

    private static DbDefinitions INSTANCE = null;

    private static DbDefinitions getInstance() {
        if (INSTANCE == null) {
            InputStreamReader reader = null;
            String path = TestProperties.getInstance().getProperties().getProperty("dbDefinitions.path");
            try {
                reader = new InputStreamReader(Thread.currentThread().getContextClassLoader().getResourceAsStream(path));
                JAXBContext jaxbContext = JAXBContext.newInstance(DbDefinitions.class);
                Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
                INSTANCE = (DbDefinitions) jaxbUnmarshaller.unmarshal(reader);
                return INSTANCE;
            } catch (JAXBException e) {
              //  throw new DeserializationException("Произошла ошибка при получении объекта из XML."+"\n"+ e.getMessage());
            }

        }
        return INSTANCE;
    }

    public static List<DbDefinition> getDbDefinitionsList() {
        return getInstance().dbDefinitionsList;
    }

    public static DbDefinition getDbDefinition(String name){

        for (DbDefinition def:getInstance().dbDefinitionsList) {
            if (def.getName().equals(name)) return def;
        }
        return null;
    }
}