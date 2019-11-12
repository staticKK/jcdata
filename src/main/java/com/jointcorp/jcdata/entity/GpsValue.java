package com.jointcorp.jcdata.entity;

public class GpsValue {

    private String gpsDate;
    //经度
    private String longitude;
    //纬度
    private String latitude;

    public GpsValue() {
    }

    public GpsValue(String gpsDate, String longitude, String latitude) {
        this.gpsDate = gpsDate;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String getGpsDate() {
        return gpsDate;
    }

    public void setGpsDate(String gpsDate) {
        this.gpsDate = gpsDate;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    @Override
    public String toString() {
        return "GpsValue{" +
                "gpsDate='" + gpsDate + '\'' +
                ", longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                '}';
    }
}
