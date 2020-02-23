package company.scanner;

import company.model.Network;
import company.rest.MacAddressApi;
import company.utils.MessageUtils;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Objects;

public class NetworkScanner implements Runnable {
    public static final int TIMEOUT = 2000;
    public static final String EMPTY_STRING = "";
    private byte[] ip;
    private int mask;

    public NetworkScanner(int mask) {
        initIP();
        this.mask = mask;
        Thread thread = new Thread(this);
        thread.start();
    }

    private void initIP() {
        try {
            ip = InetAddress.getLocalHost().getAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    private String getMacAddressByIP(InetAddress ip) {
        StringBuilder macAddress = new StringBuilder();
        byte[] mac = getHardwareAddressMask(ip);
        if (mac != null) {
            for (int i = 0; i < mac.length; i++) {
                macAddress.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
            }
        }
        return macAddress.toString();
    }

    private byte[] getHardwareAddressMask(InetAddress ip) {
        try {
            NetworkInterface network = NetworkInterface.getByInetAddress(ip);
            return network != null ? network.getHardwareAddress() : null;
        } catch (SocketException ignored) {
        }
        return null;
    }

    private String getManufacturerByMac(String currentMac) {
        if (currentMac.equals(EMPTY_STRING)) {
            return MessageUtils.noInformation();
        }
        MacAddressApi macAddressApi = new MacAddressApi(currentMac);
        macAddressApi.connect();
        return Objects.requireNonNull(macAddressApi.getHardwareInfo()
                .stream()
                .filter(it -> currentMac.equals(it.getMac()))
                .findAny()
                .orElse(null)).getCompany();
    }

    @Override
    public void run() {
        try {
            ip[3] = (byte) mask;
            InetAddress address = InetAddress.getByAddress(ip);
            String macAddress = getMacAddressByIP(address);
            Network network = new Network(address.getCanonicalHostName(), address.getHostAddress(), macAddress, getManufacturerByMac(macAddress));
            if (address.isReachable(TIMEOUT)) {
                MessageUtils.showOnlineNetworkInfo(network);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
