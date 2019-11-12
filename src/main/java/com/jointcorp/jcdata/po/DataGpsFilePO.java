package com.jointcorp.jcdata.po;

import java.time.LocalDateTime;

/**
 * 数据库查询
 */
public class DataGpsFilePO {

    private String fileName;
    private String converFileName;
    private String region;
    private long uid;
    private String mac;
    private LocalDateTime time;

    public DataGpsFilePO() {
    }

    public DataGpsFilePO(String fileName,String converFileName,String region, long uid, String mac, LocalDateTime time) {
        this.fileName = fileName;
        this.converFileName = converFileName;
        this.region = region;
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

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getConverFileName() {
        return converFileName;
    }

    public void setConverFileName(String converFileName) {
        this.converFileName = converFileName;
    }
}
