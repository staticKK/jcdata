package com.jointcorp.jcdata.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class GpsValueTime {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime gpsDate;
    //经度
    private String longitude;
    //纬度
    private String latitude;

    public GpsValueTime() {
    }

    public GpsValueTime(LocalDateTime gpsDate, String longitude, String latitude) {
        this.gpsDate = gpsDate;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public LocalDateTime getGpsDate() {
        return gpsDate;
    }

    public void setGpsDate(LocalDateTime gpsDate) {
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
}
