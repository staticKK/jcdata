package com.jointcorp.jcdata.po;

import com.jointcorp.jcdata.entity.DataTotal;
import com.jointcorp.jcdata.utils.DateUtil;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class DataTotalPO {

    private String dataDetail;

    private LocalDateTime time;

    private String mac;

    private Long uid;

    public DataTotalPO() {
    }

    public DataTotalPO(String dataDetail, String time, String mac, Long uid) {
        this.dataDetail = dataDetail;
        this.time = DateUtil.parseDataTime(time);
        this.mac = mac;
        this.uid = uid;
    }

    public String getDataDetail() {
        return dataDetail;
    }

    public void setDataDetail(String dataDetail) {
        this.dataDetail = dataDetail;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
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

    public static List<DataTotalPO> parseList(List<DataTotal> dataTotalList, String mac, Long uid) {
        List<DataTotalPO> list = new ArrayList<>(dataTotalList.size());
        for(DataTotal total : dataTotalList) {
            list.add(new DataTotalPO(StringUtils.join(total.getDataDetail().toArray(), ","),total.getTime(),mac,uid));
        }
        return list;
    }

    public static DataTotalPO parse(DataTotal dataTotal,String mac,Long uid) {
        return new DataTotalPO(StringUtils.join(dataTotal.getDataDetail().toArray(), ","),dataTotal.getTime(),mac,uid);
    }
}
