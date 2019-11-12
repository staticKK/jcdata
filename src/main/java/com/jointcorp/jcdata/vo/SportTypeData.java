package com.jointcorp.jcdata.vo;

/**
 * 页面 运动类型数据
 */
public class SportTypeData {

    private String type;
    private String heartRate;
    private String time; //运动时长
    private String step;
    private String speed;
    private String calorie;
    private String distance;
    private String dayTime; //运动时间

    public SportTypeData() {
    }

    public SportTypeData(String type, String heartRate, String time, String step, String speed, String calorie, String distance,String dayTime) {
        this.type = type;
        this.heartRate = heartRate;
        this.time = time;
        this.step = step;
        this.speed = speed;
        this.calorie = calorie;
        this.distance = distance;
        this.dayTime = dayTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(String heartRate) {
        this.heartRate = heartRate;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public String getCalorie() {
        return calorie;
    }

    public void setCalorie(String calorie) {
        this.calorie = calorie;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {

        this.distance = distance;
    }

    public String getDayTime() {
        return dayTime;
    }

    public void setDayTime(String dayTime) {
        this.dayTime = dayTime;
    }
}
