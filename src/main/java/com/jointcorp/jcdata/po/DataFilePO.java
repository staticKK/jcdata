package com.jointcorp.jcdata.po;

import java.time.LocalDateTime;

/**
 * 数据库查询
 */
public class DataFilePO {

    private String fileName;
    private long uid;
    private String mac;
    private LocalDateTime time;

    public DataFilePO() {
    }

    public DataFilePO(String fileName, long uid, String mac, LocalDateTime time) {
        this.fileName = fileName;
        this.uid = uid;
        this.mac = mac;
        this.time = time;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}
