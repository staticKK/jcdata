package com.jointcorp.jcdata.vo;

/**
 * 页面画图 睡眠在床离床标记点位置
 */
public class SleepMark {

    private String[] coord;

    public SleepMark() {
    }

    public SleepMark(String[] coord) {
        this.coord = coord;
    }

    public String[] getCoord() {
        return coord;
    }

    public void setCoord(String[] coord) {
        this.coord = coord;
    }
}
