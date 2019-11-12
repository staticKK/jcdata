package com.jointcorp.jcdata.vo;

import com.jointcorp.jcdata.entity.DataTotal;

import java.util.Arrays;
import java.util.List;

/**
 *  app上传的各类数据总封装对象
 */
public class Data {

    private List<DataTotal> dataTotal;

    private String dataType;

    private String mac;

    private Long uid;

    private String deviceType;


    public Data() {
    }

    public Data(List<DataTotal> dataTotal, String dataType, String mac, Long uid) {
        this.dataTotal = dataTotal;
        this.dataType = dataType;
        this.mac = mac;
        this.uid = uid;
    }

    public List<DataTotal> getDataTotal() {
        return dataTotal;
    }

    public void setDataTotal(List<DataTotal> dataTotal) {
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

    @Override
    public String toString() {
        return "Data{" +
                "dataTotal=" + dataTotal +
                ", dataType='" + dataType + '\'' +
                ", mac='" + mac + '\'' +
                ", uid=" + uid +
                ", deviceType='" + deviceType + '\'' +
                '}';
    }
}
