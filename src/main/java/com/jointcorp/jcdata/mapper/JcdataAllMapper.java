package com.jointcorp.jcdata.mapper;

import com.jointcorp.jcdata.po.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
public interface JcdataAllMapper {

    /**
     * 根据时间点查询已经存在的数据
     * @param list
     * @param mac
     * @param uid
     * @return
     * @throws Exception
     */
    List<LocalDate> selectExistDateTotalSport(@Param("list") List<LocalDate> list,
                                              @Param("mac") String mac, @Param("uid") long uid) throws Exception;

    List<LocalDateTime> selectExistDate(@Param("tableName") String tableName,
                                        @Param("list") List<LocalDateTime> list,
                                        @Param("mac") String mac, @Param("uid") long uid) throws Exception;

    /**
     * 按月查询数据
     */
    List<TotalSportDataPO> selectTotalSprot(@Param("uid") long uid, @Param("mac") String mac) throws Exception;

    List<DataTotalPO> selectData(@Param("tableName") String tableName, @Param("uid") long uid, @Param("mac") String mac) throws Exception;

    List<DataFilePO> selectFile(@Param("tableName") String tableName, @Param("mac") String mac, @Param("uid") long uid) throws Exception;

}
