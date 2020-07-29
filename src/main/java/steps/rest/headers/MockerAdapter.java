package steps.rest.headers;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.lang.reflect.Constructor;

public class MockerAdapter extends XmlAdapter<String, Mocker> {

    private DocumentBuilder documentBuilder;

    public MockerAdapter() throws Exception {
        documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
    }


    @Override
    public Mocker unmarshal(String v) throws Exception {
        Class<? extends Mocker> clazz= (Class<? extends Mocker>) Class.forName("ru.tele2.autotests.rest.headers."+v);
        Constructor<? extends Mocker> constructor = clazz.getConstructor();
        return (Mocker)constructor.newInstance();
    }

    @Override
    public String marshal(Mocker v) throws Exception {
        return v.getClass().getName();
    }
}
