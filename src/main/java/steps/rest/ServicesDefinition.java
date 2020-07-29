package steps.rest;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ServicesDefinition {

    private String serviceName;
    private String server;
    private String port;
    @XmlElement
    private ApiMethods apiMethods = new ApiMethods();

    public String getServiceName() {
        return serviceName;
    }

    @XmlAttribute
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServer() {
        return server;
    }

    @XmlElement
    public void setServer(String server) {
        this.server = server;
    }

    public String getPort() {
        return port;
    }

    @XmlElement
    public void setPort(String port) {
        this.port = port;
    }

    public ApiMethods getApiMethods() {
        return apiMethods;
    }


    @Override
    public String toString() {
        return "serviceName: " + getServiceName() + " server: " + getServer() + " port: " + getPort();
    }

    @Override
    public boolean equals(Object obj) {
        ServicesDefinition def = (ServicesDefinition) obj;
        return this.server.equals(def.getServer()) && this.port.equals(def.getPort());
    }

    @Override
    public int hashCode() {
        int result = getServer().hashCode() + getPort().hashCode();
        return result;
    }
}
