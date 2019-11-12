package com.jointcorp.jcdata.vo;

/**
 * 画图时坐标轴的数值集合
 */
public class DetailDataVO {

    private String[] y;
    private String[] x;

    public DetailDataVO() {
    }

    public DetailDataVO(String[] y, String[] x) {
        this.y = y;
        this.x = x;
    }

    public String[] getY() {
        return y;
    }

    public void setY(String[] y) {
        this.y = y;
    }

    public String[] getX() {
        return x;
    }

    public void setX(String[] x) {
        this.x = x;
    }
}
