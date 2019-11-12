package com.jointcorp.jcdata.mapper;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserInfoMapper {

    List<Long> selectAllUid() throws Exception;

    List<String> selectMacByUid(long uid) throws Exception;
}
