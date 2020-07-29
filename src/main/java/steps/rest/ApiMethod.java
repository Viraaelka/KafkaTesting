package steps.rest;

import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ApiMethod {

    private String methodName;
    private String methodUrl;
    private String methodSOAPAction;
    private String methodTypeQuery;

    public String getMethodName() {
        return this.methodName;
    }

    @XmlAttribute
    public void setMethodName(String name) {
        this.methodName = name;
    }

    public String getMethodUrl() {
        return this.methodUrl;
    }
    @XmlAttribute
    public void setMethodUrl(String url){
        this.methodUrl = url;
    }

    public String getMethodSOAPAction() {
        return this.methodSOAPAction;
    }
    @XmlAttribute
    public void setMethodSOAPAction(String SOAPAction){
        this.methodSOAPAction = SOAPAction;
    }

    public String getMethodTypeQuery() {
        return this.methodTypeQuery;
    }
    @XmlAttribute
    public void setMethodTypeQuery(String typeQuery){
        this.methodTypeQuery = typeQuery;
    }
}
