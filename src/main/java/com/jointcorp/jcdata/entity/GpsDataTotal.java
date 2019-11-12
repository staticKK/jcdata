package com.jointcorp.jcdata.entity;

import java.util.List;

public class GpsDataTotal {

    private List<GpsValue> dataDetail;

    private String time;

    public List<GpsValue> getDataDetail() {
        return dataDetail;
    }

    public void setDataDetail(List<GpsValue> dataDetail) {
        this.dataDetail = dataDetail;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
