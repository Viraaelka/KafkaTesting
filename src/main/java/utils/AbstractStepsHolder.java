package utils;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.jbehave.core.model.ExamplesTable;
import org.jsoup.Jsoup;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AbstractStepsHolder {
    private final static String JSONRESPONSE_PATTERN = "\\#jsonResponse\\{(?<var>[^{(}).]*).(?<key>[^{}]*)\\}";

    public final static Pattern JSONRESPONSE_PATTERN_COMPILED = Pattern.compile(JSONRESPONSE_PATTERN);

    private static VariablesStorage variables = VariablesStorage.VARIABLES;

    public static void setVariable(String name, Object value) {
        variables.get().put(name, value);
    }

    public static Object getVariable(String name) {
        return variables.get().get(name);
    }

    static {
        variables.get().putAll(TestProperties.getInstance().getProperties());
    }

    public static <T> T evalVariable(String param) {
        return evalVariable(param, null);
    }

    public static <T> T evalVariable(String param, ExamplesTable exampleTable) {
        try {
            if (param.trim().matches(".*" + JSONRESPONSE_PATTERN + ".*")) {
                Matcher varMatcher = JSONRESPONSE_PATTERN_COMPILED.matcher(param);
                StringBuffer varSB = new StringBuffer();
                while (varMatcher.find()) {

                    String jsonResponse = (String) variables.get().get(varMatcher.group("var"));
                    String jsonPath = varMatcher.group("key");

                    DocumentContext jsonContextResp = JsonPath.parse(jsonResponse);

                    String value = "";
                    Object object = jsonContextResp.read(jsonPath);//list.get(0);
                    if (object instanceof Double) {
                        double d = (Double) object;
                        DecimalFormat format = new DecimalFormat("0.00##");
                        value = format.format(d);
                    } else if (object instanceof List) {
                        List<Object> list = (List) object;
                        if (list.get(0) instanceof Double) {
                            double d = (Double) list.get(0);
                            DecimalFormat format = new DecimalFormat("0.00##");
                            value = format.format(d);
                        } else
                            value = list.get(0).toString();
                    } else if (object instanceof Map) {
                        throw new Exception("Необходимо уточнить json path до конкретных значений, json path = " + jsonPath);
                    } else {
                        value = object == null ? "null" : object.toString();
                    }

                    varMatcher.appendReplacement(varSB, Matcher.quoteReplacement(value));
                }
                varMatcher.appendTail(varSB);

                //remove html tags from response
                String plain = Jsoup.parse(varSB.toString()).text();
                return evalVariable(plain, exampleTable);
            }
        } catch (Exception e) {
            e.printStackTrace();
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            throw new AssertionError("Произошла ошибка разбора, val = " + param + ", " + e.getMessage());
        }
        return (T) param;
    }
}
