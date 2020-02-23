package company.utils;

import company.model.Network;

import java.io.IOException;
import java.net.HttpURLConnection;

import static company.Colors.*;

public class MessageUtils {
    public static void showOnlineNetworkInfo(Network network) {
        System.out.println(RED.getCode() +
                "Name: " + network.getName() + GREEN.getCode() + "\n" +
                "IP: " + network.getIp().substring(1) + "\n" +
                "MAC: " + network.getMac() + "\n" +
                "Manufacturer: " + network.getManufacturer() + "\n" +
                "Status: Is on the network" + "\n" +
                RESET.getCode());
    }

    public static void networkConnectionFailed(HttpURLConnection connection) {
        try {
            System.out.println(RED.getCode() + "FAILED " + connection.getResponseCode());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String noInformation() {
        return YELLOW.getCode() + "No Information :(" + GREEN.getCode();
    }
}
