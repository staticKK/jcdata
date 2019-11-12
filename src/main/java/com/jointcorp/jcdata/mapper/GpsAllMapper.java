package com.jointcorp.jcdata.mapper;

import com.jointcorp.jcdata.po.DataGpsFilePO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface GpsAllMapper {


    List<DataGpsFilePO> selectFile(@Param("mac") String mac, @Param("uid") long uid) throws Exception;


}
