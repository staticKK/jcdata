package com.jointcorp.jcdata.service;

import com.jointcorp.common.util.MessageResult;
import com.jointcorp.jcdata.entity.UserInfo;

import java.util.List;

/**
 * 用户查询相关
 */
public interface UserInfoService {

    List<UserInfo> selectByUids() throws Exception;


    MessageResult queryMacByUid(Long uid) throws Exception;
}
