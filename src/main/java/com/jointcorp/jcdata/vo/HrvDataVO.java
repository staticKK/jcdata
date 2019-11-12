package com.jointcorp.jcdata.vo;

/**
 * 页面hrv数据
 */
public class HrvDataVO {

    private String hrv;
    private String degree;
    private String heartRate;
    private String pressure;
    private String diastolic;
    private String systolic;
    private String time;

    public HrvDataVO() {
    }

    public HrvDataVO(String hrv,String degree, String heartRate, String pressure, String diastolic, String systolic, String time) {
        this.hrv = hrv;
        this.degree = degree;
        this.heartRate = heartRate;
        this.pressure = pressure;
        this.diastolic = diastolic;
        this.systolic = systolic;
        this.time = time;
    }

    public String getHrv() {
        return hrv;
    }

    public void setHrv(String hrv) {
        this.hrv = hrv;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(String heartRate) {
        this.heartRate = heartRate;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getDiastolic() {
        return diastolic;
    }

    public void setDiastolic(String diastolic) {
        this.diastolic = diastolic;
    }

    public String getSystolic() {
        return systolic;
    }

    public void setSystolic(String systolic) {
        this.systolic = systolic;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "HrvDataVO{" +
                "degree='" + degree + '\'' +
                ", heartRate='" + heartRate + '\'' +
                ", pressure='" + pressure + '\'' +
                ", diastolic='" + diastolic + '\'' +
                ", systolic='" + systolic + '\'' +
                '}';
    }
}
