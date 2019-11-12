package com.jointcorp.jcdata.vo;

import com.jointcorp.jcdata.entity.DataTotal;
import com.jointcorp.jcdata.entity.GpsDataTotal;

import java.util.List;

/**
 * app上传 gps数据
 */
public class GpsData {

    private List<GpsDataTotal> dataTotal;

    private String dataType;

    private String mac;

    private Long uid;

    private String deviceType;

    public GpsData() {
    }

    public List<GpsDataTotal> getDataTotal() {
        return dataTotal;
    }

    public void setDataTotal(List<GpsDataTotal> dataTotal) {
        this.dataTotal = dataTotal;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }
}
