package com.jointcorp.jcdata.service;

import com.jointcorp.common.util.MessageResult;

import java.time.LocalDateTime;

public interface AppDataQueryService {


    /**
     * 查询时间段的数据
     * @param uid  用户ID
     * @param mac 设备
     * @param month
     * @param day
     * @param dataType
     * @return
     * @throws Exception
     */
    MessageResult query(long uid, String mac, int year, int month, int day, LocalDateTime dateTime, String dataType) throws Exception;

    /**
     * 查询最后一次上传数据的设备型号和mac地址
     * @param uid
     * @return
     * @throws Exception
     */
    MessageResult queryDeviceType(long uid) throws Exception;
}
