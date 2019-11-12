package com.jointcorp.jcdata.mapper;

import com.jointcorp.jcdata.po.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
public interface JcdataMapper {

    /**
     * 保存数据
     * @param tableName
     * @param list
     * @return
     * @throws Exception
     */
    int insertData(@Param("tableName") String tableName,@Param("list") List<DataTotalPO> list) throws Exception;

    int insertTotalSportData(List<TotalSportDataPO> list) throws Exception;

    /**
     * 根据时间点查询已经存在的数据
     * @param list
     * @param maxDate
     * @param minDate
     * @param mac
     * @param uid
     * @return
     * @throws Exception
     */
    List<LocalDate> selectExistDateTotalSport(@Param("list") List<LocalDate> list, @Param("maxDate") LocalDate maxDate, @Param("minDate") LocalDate minDate,
                                                          @Param("mac") String mac, @Param("uid") long uid) throws Exception;

    List<LocalDateTime> selectExistDate(@Param("tableName") String tableName,
                                      @Param("list") List<LocalDateTime> list, @Param("maxDate") LocalDateTime maxDate, @Param("minDate") LocalDateTime minDate,
                                      @Param("mac") String mac, @Param("uid") long uid) throws Exception;

    /**
     * 更新已经存在的时间点的数据
     * @param list
     * @return
     * @throws Exception
     */
    int updateTotalSportData(List<TotalSportDataPO> list
                             ) throws Exception;
    int updateData(@Param("tableName") String tableName,@Param("list") List<DataTotalPO> list) throws Exception;

    /**
     * 按月查询数据
     */
    List<TotalSportDataPO> selectTotalSprot(@Param("startDate") LocalDate startDate,@Param("endDate") LocalDate endDate,
                                            @Param("uid") long uid,@Param("mac") String mac) throws Exception;

    List<DataTotalPO> selectData(@Param("tableName") String tableName,@Param("startDate") LocalDateTime startDate,@Param("endDate") LocalDateTime endDate,
                                            @Param("uid") long uid,@Param("mac") String mac) throws Exception;


    int insertFile(@Param("tableName") String tableName,@Param("fileName") String fileName,@Param("time") LocalDateTime time,
                  @Param("mac") String mac,@Param("uid") long uid);

    int batchInsertFile(@Param("tableName") String tableName,@Param("list") List<DataFilePO> list);


    int batchUpdatetFile(@Param("tableName") String tableName,@Param("list") List<DataFilePO> list);


    DataFilePO selectFile(@Param("tableName") String tableName,@Param("time") LocalDateTime time,@Param("mac") String mac,@Param("uid") long uid) throws Exception;

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
    List<ExistFilePO> selectExistFile(@Param("list") List<LocalDateTime> list, @Param("maxDate") LocalDateTime maxDate, @Param("minDate") LocalDateTime minDate,
                                      @Param("mac") String mac, @Param("uid") long uid) throws Exception;


    DeviceAndMac selecDeviceType(@Param("uid") long uid) throws Exception;

    String selectLastMac(@Param("uid") long uid) throws Exception;

    int insertLastUseDevice(@Param("uid") long uid,@Param("mac") String mac,
                            @Param("deviceType") String deviceType,@Param("created") LocalDateTime created) throws Exception;


}
