package com.jointcorp.jcdata.common;

public enum TableName {

    DETAIL_SLEEP("details_sleep_data","详细睡眠"),
    DETAIL_SPORT("details_sport_data","详细运动"),
    DYNAMIC_HEART("dynamic_heart_data","动态心率"),
    STATIC_HEART("static_heart_data","静态心率"),
    ECG("ecg_data","ecg"),
    HRV("hrv_data","hrv"),
    SPORT_TYPE_DATA("sport_type_data","运动类型数据"),
    GPS_DATA("gps_Data","gps数据"),
    TOTAL_SPROT_DATA("total_sport_data","总运动数据");

    private String name ;
    private String info;

    TableName(String name, String info) {
        this.name = name;
        this.info = info;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
