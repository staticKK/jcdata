package com.jointcorp.jcdata.mapper;

import com.jointcorp.jcdata.po.DataGpsFilePO;
import com.jointcorp.jcdata.po.ExistGpsFilePO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public interface GpsMapper {


    int batchInsertFile(@Param("list") List<DataGpsFilePO> list);


    int batchUpdatetFile(@Param("list") List<DataGpsFilePO> list);

    int batchUpdatetConverFile(@Param("list") List<DataGpsFilePO> list);


    DataGpsFilePO selectFile(@Param("time") LocalDateTime time,@Param("mac") String mac, @Param("uid") long uid) throws Exception;

    /**
     * 根据时间点找到重复的文件
     * @param list
     * @param maxDate
     * @param minDate
     * @param mac
     * @param uid
     * @return
     * @throws Exception
     */
    List<ExistGpsFilePO> selectExistFile(@Param("list") List<LocalDateTime> list, @Param("maxDate") LocalDateTime maxDate, @Param("minDate") LocalDateTime minDate,
                                         @Param("mac") String mac, @Param("uid") long uid) throws Exception;

}
