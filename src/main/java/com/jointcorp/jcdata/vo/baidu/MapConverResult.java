package com.jointcorp.jcdata.vo.baidu;

import java.util.List;

public class MapConverResult {

    private int status;
    private List<MapConverValue> result;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<MapConverValue> getResult() {
        return result;
    }

    public void setResult(List<MapConverValue> result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "MapConverResult{" +
                "status=" + status +
                ", result=" + result +
                '}';
    }
}
