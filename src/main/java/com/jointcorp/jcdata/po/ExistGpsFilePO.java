package com.jointcorp.jcdata.po;

import java.time.LocalDateTime;

public class ExistGpsFilePO {
    private String id;
    private String fileName;
    private String converFileName;
    private String region;
    private LocalDateTime time;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
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
