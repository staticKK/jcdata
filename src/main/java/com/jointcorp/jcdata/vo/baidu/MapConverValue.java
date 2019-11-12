package com.jointcorp.jcdata.vo.baidu;

public class MapConverValue {

    //lng纬度，
    private String x;
    //lat,经度
    private String y;

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "MapConverValue{" +
                "x='" + x + '\'' +
                ", y='" + y + '\'' +
                '}';
    }
}
