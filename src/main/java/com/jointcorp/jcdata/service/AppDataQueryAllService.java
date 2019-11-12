package com.jointcorp.jcdata.service;

import com.jointcorp.common.util.MessageResult;

public interface AppDataQueryAllService {


    /**
     * 查询用户某个类型的所有数据
     * @param uid  用户ID
     * @param mac 设备
     * @param dataType
     * @return
     * @throws Exception
     */
    MessageResult query(long uid, String mac,String dataType) throws Exception;

}
