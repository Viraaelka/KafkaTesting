package steps.rest;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import steps.rest.headers.MockHeaderDefenitions;
import utils.TestProperties;
import org.apache.http.impl.client.WinHttpClients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

//import org.apache.http.impl.client.CloseableHttpClient;

public class SendRestRequestManager {
    private CloseableHttpClient getClient(){

        if (!WinHttpClients.isWinAuthAvailable()) {
            System.out.println("Integrated Win auth is not supported!!!");
        }
        CloseableHttpClient client = WinHttpClients.createDefault();

        return client;
    }

    public String request(String urlRequests) throws IOException {
        return request(urlRequests, null);
    }

    public String request(String urlRequests, String body) throws IOException {

        HttpResponse response = null;

        if (body != null) {
            // если body есть, то запрос отправляется как POST
            HttpPost request = new HttpPost(urlRequests);
            StringEntity entity = new StringEntity(body,"UTF-8");
            request.setEntity(entity);
            request.setHeader("Accept", "application/json");
            request.setHeader("Content-type", "application/json");

            //FIXME Продумать настройку
            if (TestProperties.getInstance().getProperties().getProperty("oAuthService")!=null){
                String token = getToken(TestProperties.getInstance().getProperties().getProperty("oAuthService"));
                request.addHeader("Authorization", token);
            }

            //FIXME Продумать настройку
            //добавляем хэдеры для вызова моков
            MockHeaderDefenitions.getInstance().changeRequestHeaders(request);


            try { response = getClient().execute(request); }
            catch (IOException e) { e.printStackTrace(); }

        } else {
            // если body нет, то запрос отправляется как GET

            HttpGet request = new HttpGet(urlRequests);

            //FIXME Продумать настройку
            if (TestProperties.getInstance().getProperties().getProperty("oAuthService")!=null){
                String token = getToken(TestProperties.getInstance().getProperties().getProperty("oAuthService"));
                request.addHeader("Authorization", token);
            }

            //FIXME Продумать настройку
            //добавляем хэдеры для вызова моков
            MockHeaderDefenitions.getInstance().changeRequestHeaders(request);

            try { response = getClient().execute(request); }
            catch (IOException e) { e.printStackTrace(); }
        }

        BufferedReader rd = null;
        try {
            rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        StringBuffer result = new StringBuffer();
        String line;
        try {
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result.toString();
    }



    /**
     * Метод получения токена авторизации
     * @param urlGetToken - ссылка на сервис получения токена
     * @return Токен авторизации
     */
    private String getToken(String urlGetToken){
        String token ="";
        CloseableHttpClient client = WinHttpClients.createDefault();
        HttpGet request = new HttpGet(urlGetToken);

        HttpResponse response = null;
        try {
            response = client.execute(request);

            String json_string = EntityUtils.toString(response.getEntity());
            JSONObject temp1 = new JSONObject(json_string);
            token = temp1.getString("data");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return token;
    }
}
