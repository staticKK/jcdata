package com.jointcorp.jcdata.common;

/**
 * 上传的各种数据类型
 */
public enum DataType {

    DETAIL_SLEEP("detailSleepData","详细睡眠"),
    DETAIL_SPORT("detailSportData","详细运动"),
    DYNAMIC_HEART("dynamicHeartRateData","动态心率"),
    STATIC_HEART("staticHeartRateData","静态心率"),
    ECG("ecgData","ecg"),
    HRV("hrvData","hrv"),
    SPORT_TYPE_DATA("sportTypeData","运动类型数据"),
    TOTAL_SPORT_DATA("totalSportData","总运动数据"),
    HEART_RATE("heartRateData","心率"),
    GPS_DATA("gpsData","gps数据");

    private String dataType ;
    private String info;

    DataType(String dataType, String info) {
        this.dataType = dataType;
        this.info = info;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
