package steps.rest.headers;

import io.netty.handler.codec.http.HttpRequest;
import net.lightbody.bmp.util.HttpMessageContents;
import net.lightbody.bmp.util.HttpMessageInfo;
import org.apache.http.client.methods.HttpRequestBase;
import utils.TestProperties;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Map;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class MockHeaderDefenitions {

    private static MockHeaderDefenitions INSTANCE = null;

    @XmlElementRef
    private ArrayList<MockHeaderDefenition> mockHeaderDefenitions = new ArrayList<>();

    private MockHeaderDefenitions() {
    }

    public static MockHeaderDefenitions getInstance() {
        if (INSTANCE == null) {
            InputStreamReader reader = null;
            String path = TestProperties.getInstance().getProperties().getProperty("mockHeaderDefinitions.path");
            if (path == null) {
                INSTANCE = new MockHeaderDefenitions();
                //TODO Бросать ошибку, что настройка не найдена, фиксануть после выноски параметра использования заголовков для моков
            } else {
                try {
                    reader = new InputStreamReader(Thread.currentThread().getContextClassLoader().getResourceAsStream(path));
                    JAXBContext jaxbContext = JAXBContext.newInstance(MockHeaderDefenitions.class);
                    Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
                    INSTANCE = (MockHeaderDefenitions) jaxbUnmarshaller.unmarshal(reader);
                    return INSTANCE;
                } catch (JAXBException e) {
                    //  throw new DeserializationException("Произошла ошибка при получении объекта из XML."+"\n"+ e.getMessage());
                }
            }
        }
        return INSTANCE;
    }

    public void changeRequestHeaders(HttpRequest request, HttpMessageContents contents, HttpMessageInfo messageInfo) {
        String url = messageInfo.getOriginalUrl();
        MockHeaderDefenition defMockHeaders = getMockHeaderDefenition(url);

        if (defMockHeaders == null) return;

        if (defMockHeaders.getMocker().isMocable(url)) {
            for (Map.Entry<String, String> entry : defMockHeaders.getHeaders().entrySet()) {
                request.headers().add(entry.getKey(), entry.getValue());
            }
        }
    }

    public void changeRequestHeaders(HttpRequestBase request) {
        String url = request.getURI().toString();
        MockHeaderDefenition defMockHeaders = getMockHeaderDefenition(url);

        if (defMockHeaders == null) return;

        if (defMockHeaders.getMocker().isMocable(url)) {
            for (Map.Entry<String, String> entry : defMockHeaders.getHeaders().entrySet()) {
                request.addHeader(entry.getKey(), entry.getValue());
            }
        }
    }

    public MockHeaderDefenition getMockHeaderDefenition(String URL) {
        for (MockHeaderDefenition def : this.mockHeaderDefenitions) {
            if (URL.contains(def.getSubStringURL())) {
                return def;
            }
        }
        return null;
    }
}
