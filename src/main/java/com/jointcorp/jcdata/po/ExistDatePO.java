package com.jointcorp.jcdata.po;

import java.time.LocalDateTime;

/**
 * 数据库查询，已存在的时间和id数据
 */
public class ExistDatePO {

    private long id;
    private LocalDateTime time;

    public ExistDatePO() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "ExistDatePO{" +
                "id=" + id +
                ", time=" + time +
                '}';
    }
}
