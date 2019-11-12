package com.jointcorp.jcdata.po;

import java.time.LocalDate;

/**
 * 数据库查询，重复的总运动数据的时间和id
 */
public class TotalSportExistDatePO {

    private long id;
    private LocalDate time;

    public TotalSportExistDatePO() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDate getTime() {
        return time;
    }

    public void setTime(LocalDate time) {
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
