package com.jointcorp.jcdata.po;

public class DeviceAndMac {

    private String mac;
    private String deviceType;

    public DeviceAndMac(String mac, String deviceType) {
        this.mac = mac;
        this.deviceType = deviceType;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }
}
