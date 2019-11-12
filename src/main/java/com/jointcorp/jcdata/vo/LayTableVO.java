package com.jointcorp.jcdata.vo;

import java.util.List;

/**
 * 页面分页table
 **/
public class LayTableVO {

    private int code;
    private String msg;
    private long count;

    private List<?> data;

    public LayTableVO(long count, List<?> data) {
        this.code = 0;
        this.msg = "ok";
        this.count = count;
        this.data = data;
    }

    public LayTableVO(int code, String msg, long count, List<?> data) {
        this.code = code;
        this.msg = msg;
        this.count = count;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public List<?> getData() {
        return data;
    }

    public void setData(List<?> data) {
        this.data = data;
    }
}
