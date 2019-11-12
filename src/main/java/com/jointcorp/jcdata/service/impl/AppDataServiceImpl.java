package com.jointcorp.jcdata.service.impl;

import com.jointcorp.common.util.DateTimeUnit;
import com.jointcorp.common.util.DateTimeUtil;
import com.jointcorp.common.util.MessageResult;
import com.jointcorp.global.base.MsgInterpreter;
import com.jointcorp.jcdata.common.DataType;
import com.jointcorp.jcdata.common.TableName;
import com.jointcorp.jcdata.config.AppConfig;
import com.jointcorp.jcdata.entity.DataTotal;
import com.jointcorp.jcdata.mapper.GpsMapper;
import com.jointcorp.jcdata.mapper.JcdataMapper;
import com.jointcorp.jcdata.po.*;
import com.jointcorp.jcdata.service.AppDataQueryService;
import com.jointcorp.jcdata.utils.DateUtil;
import com.jointcorp.jcdata.vo.Data;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class AppDataServiceImpl implements AppDataQueryService {

    @Autowired
    private JcdataMapper jcdataMapper;
    @Autowired
    private GpsMapper gpsMapper;
    @Autowired
    private AppConfig appConfig;

    @Override
    public MessageResult query(long uid, String mac, int year, int month, int day, LocalDateTime dateTime,String dataType) throws Exception {
        //总运动数据
        if(dataType.equals(DataType.TOTAL_SPORT_DATA.getDataType())) {
            LocalDate s = DateUtil.getFirstDayOfMonth(year,month);
            LocalDate e = DateUtil.getLastDayOfMonth(year,month);
            return getTotalSportData(s,e,uid,mac);
        }
        LocalDateTime startDate = LocalDateTime.of(year,month,day,0,0,0);
        LocalDateTime endDate = LocalDateTime.of(year,month,day,23,59,59);
        if(dataType.equals(DataType.DETAIL_SPORT.getDataType())) {
            return getDetailSportData(startDate,endDate,uid,mac);
        } else if(dataType.equals(DataType.SPORT_TYPE_DATA.getDataType())) {
            return getSportTypeData(startDate,endDate,uid,mac);
        } else if(dataType.equals(DataType.DETAIL_SLEEP.getDataType())) {
            return getDetailSleepData(year,month,day,uid,mac);
        } else if(dataType.equals(DataType.STATIC_HEART.getDataType())) {
            return getStaticHeartRateData(startDate,endDate,uid,mac);
        } else if(dataType.equals(DataType.DYNAMIC_HEART.getDataType())) {
            return getDynamicHeartRateData(startDate,endDate,uid,mac);
        }else if(dataType.equals(DataType.ECG.getDataType())) {
            return getEcgData(dateTime,uid,mac);
        } else if(dataType.equals(DataType.HRV.getDataType())) {
            return getHrvData(startDate,endDate,uid,mac);
        } else if(dataType.equals(DataType.GPS_DATA.getDataType())) {
            return getGpsData(dateTime,uid,mac);
        }
        return MsgInterpreter.error();
    }

    @Override
    public MessageResult queryDeviceType(long uid) throws Exception {
        DeviceAndMac deviceAndMac = jcdataMapper.selecDeviceType(uid);
        if(deviceAndMac == null) {
            String mac = jcdataMapper.selectLastMac(uid);
            deviceAndMac = new DeviceAndMac(mac,"1755");
            return MsgInterpreter.success(deviceAndMac);
        }
        return MsgInterpreter.success(deviceAndMac);
    }

    //总运动数据
    private MessageResult getTotalSportData(LocalDate startDate, LocalDate endDate, long uid, String mac) throws Exception {
        List<TotalSportDataPO> totals = jcdataMapper.selectTotalSprot(startDate,endDate,uid,mac);

        List<DataTotal> list = new ArrayList<>();
        for(TotalSportDataPO po : totals) {
            DataTotal dataTotal = new DataTotal();
            dataTotal.setDataDetail(Arrays.asList(po.getDataDetail().split(",")));
            dataTotal.setTime(DateTimeUtil.format(po.getTime(),DateTimeUnit.ymdSegDot));
            list.add(dataTotal);
        }
        Data data = new Data(list,DataType.TOTAL_SPORT_DATA.getDataType(),mac,uid);

        return MsgInterpreter.success(data);
    }

    //详细运动
    private MessageResult getDetailSportData(LocalDateTime startDate, LocalDateTime endDate, long uid, String mac) throws Exception {
        List<DataTotalPO> totals = jcdataMapper.selectData(TableName.DETAIL_SPORT.getName(),startDate,endDate,uid,mac);
        List<DataTotal> list = new ArrayList<>();
        for(DataTotalPO po : totals) {
            DataTotal dataTotal = new DataTotal();
            dataTotal.setDataDetail(Arrays.asList(po.getDataDetail().split(",")));
            dataTotal.setTime(DateTimeUtil.format(po.getTime(),DateTimeUnit.ymdhmsSegDot));
            list.add(dataTotal);
        }
        Data data = new Data(list,DataType.DETAIL_SPORT.getDataType(),mac,uid);
        return MsgInterpreter.success(data);
    }

    private MessageResult getSportTypeData(LocalDateTime startDate, LocalDateTime endDate, long uid, String mac) throws Exception {
        List<DataTotalPO> sportTypeData = jcdataMapper.selectData(TableName.SPORT_TYPE_DATA.getName(),startDate,endDate,uid,mac);

        List<DataTotal> list = new ArrayList<>();
        for(DataTotalPO po : sportTypeData) {
            DataTotal dataTotal = new DataTotal();
            dataTotal.setDataDetail(Arrays.asList(po.getDataDetail().split(",")));
            dataTotal.setTime(DateTimeUtil.format(po.getTime(),DateTimeUnit.ymdhmsSegDot));
            list.add(dataTotal);
        }
        Data data = new Data(list,DataType.SPORT_TYPE_DATA.getDataType(),mac,uid);

        return MsgInterpreter.success(data);
    }

    private MessageResult getDetailSleepData(int year,int month,int day,long uid, String mac) throws Exception {
        LocalDateTime startDate = LocalDateTime.of(year,month,day-1,12,0,0);
        LocalDateTime endDate = LocalDateTime.of(year,month,day,12,0,0);
        List<DataTotalPO> totals = jcdataMapper.selectData(TableName.DETAIL_SLEEP.getName(),startDate,endDate,uid,mac);

        List<DataTotal> list = new ArrayList<>();
        for(DataTotalPO po : totals) {
            DataTotal dataTotal = new DataTotal();
            dataTotal.setDataDetail(Arrays.asList(po.getDataDetail().split(",")));
            dataTotal.setTime(DateTimeUtil.format(po.getTime(),DateTimeUnit.ymdhmsSegDot));
            list.add(dataTotal);
        }
        Data data = new Data(list,DataType.DETAIL_SLEEP.getDataType(),mac,uid);
        return MsgInterpreter.success(data);
    }

    private MessageResult getStaticHeartRateData(LocalDateTime startDate, LocalDateTime endDate, long uid, String mac) throws Exception {
        List<DataTotalPO> staticHeartRateData = jcdataMapper.selectData(TableName.STATIC_HEART.getName(),startDate,endDate,uid,mac);

        List<DataTotal> list = new ArrayList<>();
        for(DataTotalPO po : staticHeartRateData) {
            DataTotal dataTotal = new DataTotal();
            dataTotal.setDataDetail(Arrays.asList(po.getDataDetail().split(",")));
            dataTotal.setTime(DateTimeUtil.format(po.getTime(),DateTimeUnit.ymdhmsSegDot));
            list.add(dataTotal);
        }
        Data data = new Data(list,DataType.STATIC_HEART.getDataType(),mac,uid);
        return MsgInterpreter.success(data);
    }

    private MessageResult getDynamicHeartRateData(LocalDateTime startDate, LocalDateTime endDate, long uid, String mac) throws Exception {
        List<DataTotalPO> dynamicHeartRateData = jcdataMapper.selectData(TableName.DYNAMIC_HEART.getName(),startDate,endDate,uid,mac);

        List<DataTotal> list = new ArrayList<>();
        for(DataTotalPO po : dynamicHeartRateData) {
            DataTotal dataTotal = new DataTotal();
            dataTotal.setDataDetail(Arrays.asList(po.getDataDetail().split(",")));
            dataTotal.setTime(DateTimeUtil.format(po.getTime(),DateTimeUnit.ymdhmsSegDot));
            list.add(dataTotal);
        }
        Data data = new Data(list,DataType.DYNAMIC_HEART.getDataType(),mac,uid);

        return MsgInterpreter.success(data);
    }


    private MessageResult getHrvData(LocalDateTime startDate, LocalDateTime endDate, long uid, String mac) throws Exception {
        List<DataTotalPO> hrvDatas = jcdataMapper.selectData(TableName.HRV.getName(),startDate,endDate,uid,mac);

        List<DataTotal> list = new ArrayList<>();
        for(DataTotalPO po : hrvDatas) {
            DataTotal dataTotal = new DataTotal();
            dataTotal.setDataDetail(Arrays.asList(po.getDataDetail().split(",")));
            dataTotal.setTime(DateTimeUtil.format(po.getTime(),DateTimeUnit.ymdhmsSegDot));
            list.add(dataTotal);
        }
        Data data = new Data(list,DataType.HRV.getDataType(),mac,uid);
        return MsgInterpreter.success(data);
    }

    private MessageResult getEcgData(LocalDateTime dateTime, long uid, String mac) throws Exception {
        DataFilePO po = jcdataMapper.selectFile(TableName.ECG.getName(),dateTime,mac,uid);
        if(po == null) {
            Data data = new Data(new ArrayList<>(),DataType.GPS_DATA.getDataType(),mac,uid);
            return MsgInterpreter.success(data);
        }
        String fileName = po.getFileName();
        fileName = appConfig.getDataFilePath()+ fileName;
        //读取出来已经是一个字符串数组
        String str = FileUtils.readFileToString(new File(fileName),"utf-8");
        String[] array = str.split(",");

        List<DataTotal> list = new ArrayList<>();
        DataTotal dataTotal = new DataTotal();
        dataTotal.setDataDetail(Arrays.asList(array));
        dataTotal.setTime(DateTimeUtil.format(po.getTime(),DateTimeUnit.ymdhmsSegDot));
        list.add(dataTotal);
        Data data = new Data(list,DataType.ECG.getDataType(),mac,uid);
        return MsgInterpreter.success(data);
    }

    private MessageResult getGpsData(LocalDateTime dateTime, long uid, String mac) throws Exception {
        DataGpsFilePO po = gpsMapper.selectFile(dateTime,mac,uid);
        if(Objects.isNull(po)) {
            Data data = new Data(new ArrayList<>(),DataType.GPS_DATA.getDataType(),mac,uid);
            return MsgInterpreter.success(data);
        }
        String fileName = appConfig.getDataFilePath() + po.getFileName();
        //读取出来已经是一个字符串数组
        String str = FileUtils.readFileToString(new File(fileName),"utf-8");

        List<DataTotal> list = new ArrayList<>();
        DataTotal dataTotal = new DataTotal();
        dataTotal.setDataDetail(Arrays.asList(str.split(",")));
        dataTotal.setTime(DateTimeUtil.format(po.getTime(),DateTimeUnit.ymdhmsSegDot));
        list.add(dataTotal);
        Data data = new Data(list,DataType.GPS_DATA.getDataType(),mac,uid);

        return MsgInterpreter.success(data);
    }
}
