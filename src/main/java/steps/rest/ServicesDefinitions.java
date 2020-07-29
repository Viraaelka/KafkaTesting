package steps.rest;

import utils.TestProperties;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.InputStreamReader;
import java.util.List;

@XmlRootElement
public class ServicesDefinitions {

    @XmlElementRef
    private List<ServicesDefinition> ServicesDefinitionsList;

    private static ServicesDefinitions INSTANCE = null;

    private static ServicesDefinitions getInstance() {
        if (INSTANCE == null) {
            InputStreamReader reader = null;
            String path = TestProperties.getInstance().getProperties().getProperty("servicesDefinitions.path");
            try {
                reader = new InputStreamReader(Thread.currentThread().getContextClassLoader().getResourceAsStream(path));
                JAXBContext jaxbContext = JAXBContext.newInstance(ServicesDefinitions.class);
                Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
                INSTANCE = (ServicesDefinitions) jaxbUnmarshaller.unmarshal(reader);
                return INSTANCE;
            } catch (JAXBException e) {
                // customized error: throw new DeserializationException("Произошла ошибка при получении объекта из XML."+"\n"+ e.getMessage());
            }

        }
        return INSTANCE;
    }

    public static List<ServicesDefinition> getServicesDefinitionsList() {
        return getInstance().ServicesDefinitionsList;
    }

    public static ServicesDefinition getServicesDefinition(String serviceName) {

        for (ServicesDefinition def : getInstance().ServicesDefinitionsList) {
            if (def.getServiceName().equals(serviceName)) return def;
        }
        return null;
    }
}