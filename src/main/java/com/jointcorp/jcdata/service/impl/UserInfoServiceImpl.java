package com.jointcorp.jcdata.service.impl;

import com.jointcorp.common.util.JsonUtils;
import com.jointcorp.common.util.MessageResult;
import com.jointcorp.global.base.MsgInterpreter;
import com.jointcorp.jcdata.config.AppConfig;
import com.jointcorp.jcdata.entity.UserInfo;
import com.jointcorp.jcdata.mapper.UserInfoMapper;
import com.jointcorp.jcdata.service.UserInfoService;
import com.jointcorp.jcdata.utils.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private AppConfig appConfig;

    @Override
    public List<UserInfo> selectByUids() throws Exception {
        List<Long> ids = userInfoMapper.selectAllUid();
        String json = HttpUtil.doPostJson(appConfig.getQueryUserInfoUrl(), JsonUtils.objectToJson(ids));
        List<UserInfo> list = JsonUtils.jsonToList(json,UserInfo.class);
        return list;
    }

    @Override
    public MessageResult queryMacByUid(Long uid) throws Exception {

        return MsgInterpreter.success(userInfoMapper.selectMacByUid(uid));
    }
}
