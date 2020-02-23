package company.rest;


import company.model.HardwareInfo;
import company.model.Network;
import company.anotations.AnnotationHandler;
import company.config.ApiConfiguration;
import company.utils.MessageUtils;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

public class MacAddressApi {
    private String mac;
    private String json;
    public static final String JSON_FORMAT = "json";
    AnnotationHandler annotationHandler = new AnnotationHandler();

    public MacAddressApi(String mac) {
        this.mac = mac;
    }

    public void connect() {
        HttpURLConnection connection;
        try {
            connection = (HttpURLConnection) new URL(ApiConfiguration.BASE_URL + mac + "/" + JSON_FORMAT).openConnection();

            connection.setRequestMethod("GET");
            connection.setUseCaches(false);

            connection.connect();
            generateJson(connection);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void generateJson(HttpURLConnection connection) throws IOException {
        StringBuilder builder = new StringBuilder();
        if (HttpURLConnection.HTTP_OK == connection.getResponseCode()) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                builder.append(line).append("\n");
            }
            json = builder.toString();
        } else {
            MessageUtils.networkConnectionFailed(connection);
        }
    }

    public List<HardwareInfo> getHardwareInfo() {
        JSONObject obj = new JSONObject(json);
        return annotationHandler.handle(new HardwareInfo()).stream()
                .map(it -> {
                    HardwareInfo hardwareInfo = new HardwareInfo();
                    hardwareInfo.setMac(mac);
                    hardwareInfo.setCompany(obj.getJSONObject("result").getString(it));
                    return hardwareInfo;
                })
                .collect(Collectors.toList());
    }
}
