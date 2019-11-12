package com.jointcorp.jcdata.entity;

import java.util.List;

public class DataTotal {

    private List<String> dataDetail;

    private String time;

    public DataTotal() {
    }

    public DataTotal(List<String> dataDetail, String time) {
        this.dataDetail = dataDetail;
        this.time = time;
    }

    public List<String> getDataDetail() {
        return dataDetail;
    }

    public void setDataDetail(List<String> dataDetail) {
        this.dataDetail = dataDetail;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "DataTotal{" +
                "dataDetail=" + dataDetail +
                ", time='" + time + '\'' +
                '}';
    }
}
