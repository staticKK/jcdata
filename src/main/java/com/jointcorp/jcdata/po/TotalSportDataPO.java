package com.jointcorp.jcdata.po;

import com.jointcorp.jcdata.entity.DataTotal;
import com.jointcorp.jcdata.utils.DateUtil;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据库查询 总运动数据
 */
public class TotalSportDataPO {

    private String dataDetail;

    private LocalDate time;

    private String mac;

    private Long uid;

    public TotalSportDataPO() {
    }

    public TotalSportDataPO(String dataDetail, String time, String mac, Long uid) {
        this.dataDetail = dataDetail;
        this.time = DateUtil.parseDate(time);
        this.mac = mac;
        this.uid = uid;
    }

    public String getDataDetail() {
        return dataDetail;
    }

    public void setDataDetail(String dataDetail) {
        this.dataDetail = dataDetail;
    }

    public LocalDate getTime() {
        return time;
    }

    public void setTime(LocalDate time) {
        this.time = time;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }


    public static List<TotalSportDataPO> parseList(List<DataTotal> dataTotalList, String mac, Long uid) {
        List<TotalSportDataPO> list = new ArrayList<>(dataTotalList.size());
        for(DataTotal total : dataTotalList) {
            list.add(new TotalSportDataPO(StringUtils.join(total.getDataDetail().toArray(), ","),total.getTime(),mac,uid));
        }
        return list;
    }

    public static TotalSportDataPO parse(DataTotal total, String mac, Long uid) {
        return new TotalSportDataPO(StringUtils.join(total.getDataDetail().toArray(), ","),total.getTime(),mac,uid);
    }
}
