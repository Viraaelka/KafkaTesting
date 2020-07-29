package steps.rest.headers;

import lombok.Getter;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.HashMap;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class MockHeaderDefenition {
    @XmlAttribute
    @Getter
    private String subStringURL;

    @XmlElement(name = "mocker", required = false, nillable = true, defaultValue = "AlwaysMocker")
    @XmlJavaTypeAdapter(MockerAdapter.class)
    @Getter
    private Mocker mocker = new AlwaysMocker();

    @XmlElement(name = "headers")
    @XmlJavaTypeAdapter(MapAdapter.class)
    @Getter
    HashMap<String, String> headers = new HashMap<>();
}
