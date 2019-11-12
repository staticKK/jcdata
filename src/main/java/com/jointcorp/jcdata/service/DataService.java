package com.jointcorp.jcdata.service;

import com.jointcorp.common.util.MessageResult;
import com.jointcorp.jcdata.vo.Data;
import com.jointcorp.jcdata.vo.GpsData;

import java.time.LocalDateTime;

public interface DataService {

    /**
     * 保存数据(不包括gps)
     * @param data
     * @return
     * @throws Exception
     */
    MessageResult save(Data data) throws Exception;

    /**
     * 保存gps数据
     * @param data
     * @return
     * @throws Exception
     */
    MessageResult saveGps(GpsData data) throws Exception;

    /**
     * 查询时间段的数据，用于web端，web端使用echarts制作图形，返回的有数据轴数据和x轴的值
     * @param uid  用户ID
     * @param mac 设备
     * @param month
     * @param day
     * @param dataType
     * @return
     * @throws Exception
     */
    MessageResult query(long uid, String mac, int year, int month, int day, LocalDateTime dateTime,String dataType) throws Exception;

    /**
     * 显示地图时应该显示百度(国内)还是google地图(国外)
     * @param uid
     * @param mac
     * @param time
     * @return
     * @throws Exception
     */
    int showGpsIndex(long uid, String mac, String time) throws Exception;


}
