package steps.rest;

import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public class ApiMethods {

    @XmlElementRef
    private List<ApiMethod> apiMethodsList = new ArrayList<ApiMethod>();

    public List<ApiMethod> getMethodsList() {
        return this.apiMethodsList;
    }

    public String getMethodUrl(String methodName) {
        for (ApiMethod apiMethod: getMethodsList()) {
            if (apiMethod.getMethodName().equals(methodName)) {
                return apiMethod.getMethodUrl();
            }
        }
        return null;
    }

    public String getMethodSOAPAction(String methodName) {
        for (ApiMethod apiMethod: getMethodsList()) {
            if (apiMethod.getMethodName().equals(methodName)) {
                return apiMethod.getMethodSOAPAction();
            }
        }
        return null;
    }

    public String getMethodTypeQuery(String methodName) {
        for (ApiMethod apiMethod: getMethodsList()) {
            if (apiMethod.getMethodName().equals(methodName)) {
                return apiMethod.getMethodTypeQuery();
            }
        }
        return null;
    }
}