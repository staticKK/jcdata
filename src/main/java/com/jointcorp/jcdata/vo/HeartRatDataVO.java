package com.jointcorp.jcdata.vo;

import java.util.List;

public class HeartRatDataVO {

    private List<String[]> y;
    private String[] x;

    public HeartRatDataVO() {
    }

    public HeartRatDataVO(List<String[]> y, String[] x) {
        this.y = y;
        this.x = x;
    }

    public List<String[]> getY() {
        return y;
    }

    public void setY(List<String[]> y) {
        this.y = y;
    }

    public String[] getX() {
        return x;
    }

    public void setX(String[] x) {
        this.x = x;
    }
}
