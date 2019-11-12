package com.jointcorp.jcdata.vo;

import java.util.Arrays;
import java.util.List;

/**
 * 页面画图睡眠数据
 */
public class SleepDataVO {

    private String[] y;
    private String[] x;
    //在床坐标轴
    private List<String[]> inBed;
    //离床坐标轴
    private List<String[]> outBed;

    public SleepDataVO() {
    }

    public SleepDataVO(String[] y, String[] x, List<String[]> inBed, List<String[]> outBed) {
        this.y = y;
        this.x = x;
        this.inBed = inBed;
        this.outBed = outBed;
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

    public List<String[]> getInBed() {
        return inBed;
    }

    public void setInBed(List<String[]> inBed) {
        this.inBed = inBed;
    }

    public List<String[]> getOutBed() {
        return outBed;
    }

    public void setOutBed(List<String[]> outBed) {
        this.outBed = outBed;
    }

    @Override
    public String toString() {
        return "SleepDataVO{" +
                "y=" + Arrays.toString(y) +
                ", x=" + Arrays.toString(x) +
                ", inBed=" + inBed +
                ", outBed=" + outBed +
                '}';
    }
}
