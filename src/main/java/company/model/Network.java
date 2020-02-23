package company.model;

public class Network {
    public String name;
    public String ip;
    public String mac;
    public String manufacturer;

    public Network(String name, String ip, String mac, String manufacturer) {
        this.name = name;
        this.ip = ip;
        this.mac = mac;
        this.manufacturer = manufacturer;
    }

    public String getName() {
        return name;
    }

    public String getIp() {
        return ip;
    }

    public String getMac() {
        return mac;
    }

    public String getManufacturer() {
        return manufacturer;
    }
}
