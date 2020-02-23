package company.model;

import company.anotations.JSON;

public class HardwareInfo {
    @JSON(name = "company")
    public String company;

    private String mac;

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}
