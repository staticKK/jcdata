package com.jointcorp.jcdata.service.impl;

import com.jointcorp.common.util.DateTimeUnit;
import com.jointcorp.common.util.DateTimeUtil;
import com.jointcorp.common.util.MessageResult;
import com.jointcorp.global.base.MsgInterpreter;
import com.jointcorp.jcdata.common.DataType;
import com.jointcorp.jcdata.common.TableName;
import com.jointcorp.jcdata.entity.DataTotal;
import com.jointcorp.jcdata.entity.GpsDataTotal;
import com.jointcorp.jcdata.mapper.GpsAllMapper;
import com.jointcorp.jcdata.mapper.JcdataAllMapper;
import com.jointcorp.jcdata.po.DataFilePO;
import com.jointcorp.jcdata.po.DataGpsFilePO;
import com.jointcorp.jcdata.po.DataTotalPO;
import com.jointcorp.jcdata.po.TotalSportDataPO;
import com.jointcorp.jcdata.service.AppDataQueryAllService;
import com.jointcorp.jcdata.service.handler.FileHandler;
import com.jointcorp.jcdata.vo.Data;
import com.jointcorp.jcdata.vo.GpsData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Future;

@Service
public class AppDataAllServiceImpl implements AppDataQueryAllService {

    @Autowired
    private JcdataAllMapper jcdataAllMapper;
    @Autowired
    private GpsAllMapper gpsAllMapper;
    @Autowired
    private FileHandler fileHandler;

    @Override
    public MessageResult query(long uid, String mac,String dataType) throws Exception {
        //总运动数据
        if(dataType.equals(DataType.TOTAL_SPORT_DATA.getDataType())) {
            return getTotalSportData(uid,mac);
        }
        if(dataType.equals(DataType.DETAIL_SPORT.getDataType())) {
            return getDetailSportData(uid,mac);
        } else if(dataType.equals(DataType.SPORT_TYPE_DATA.getDataType())) {
            return getSportTypeData(uid,mac);
        } else if(dataType.equals(DataType.DETAIL_SLEEP.getDataType())) {
            return getDetailSleepData(uid,mac);
        } else if(dataType.equals(DataType.STATIC_HEART.getDataType())) {
            return getStaticHeartRateData(uid,mac);
        } else if(dataType.equals(DataType.DYNAMIC_HEART.getDataType())) {
            return getDynamicHeartRateData(uid,mac);
        }else if(dataType.equals(DataType.ECG.getDataType())) {
            return getEcgData(uid,mac);
        } else if(dataType.equals(DataType.HRV.getDataType())) {
            return getHrvData(uid,mac);
        } else if(dataType.equals(DataType.GPS_DATA.getDataType())) {
            return getGpsData(uid,mac);
        }
        return MsgInterpreter.error();
    }

    //总运动数据
    private MessageResult getTotalSportData(long uid, String mac) throws Exception {
        List<TotalSportDataPO> totals = jcdataAllMapper.selectTotalSprot(uid,mac);
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
    private MessageResult getDetailSportData(long uid, String mac) throws Exception {
        List<DataTotalPO> totals = jcdataAllMapper.selectData(TableName.DETAIL_SPORT.getName(),uid,mac);
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

    private MessageResult getSportTypeData(long uid, String mac) throws Exception {
        List<DataTotalPO> sportTypeData = jcdataAllMapper.selectData(TableName.SPORT_TYPE_DATA.getName(),uid,mac);
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

    private MessageResult getDetailSleepData(long uid, String mac) throws Exception {
        List<DataTotalPO> totals = jcdataAllMapper.selectData(TableName.DETAIL_SLEEP.getName(),uid,mac);
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

    private MessageResult getStaticHeartRateData(long uid, String mac) throws Exception {
        List<DataTotalPO> staticHeartRateData = jcdataAllMapper.selectData(TableName.STATIC_HEART.getName(),uid,mac);
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

    private MessageResult getDynamicHeartRateData(long uid, String mac) throws Exception {
        List<DataTotalPO> dynamicHeartRateData = jcdataAllMapper.selectData(TableName.DYNAMIC_HEART.getName(),uid,mac);
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


    private MessageResult getHrvData(long uid, String mac) throws Exception {
        List<DataTotalPO> hrvDatas = jcdataAllMapper.selectData(TableName.HRV.getName(),uid,mac);

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

    private MessageResult getEcgData( long uid, String mac) throws Exception {
        List<DataFilePO> pos = jcdataAllMapper.selectFile(TableName.ECG.getName(),mac,uid);
        if(CollectionUtils.isEmpty(pos)) {
            Data data = new Data(null,DataType.ECG.getDataType(),mac,uid);
            return MsgInterpreter.success(data);
        }
        Future<List<Data>> future = fileHandler.readEcgFile(pos,uid,mac);
        return MsgInterpreter.success(future.get());
    }

    private MessageResult getGpsData(long uid, String mac) throws Exception {
        List<DataGpsFilePO> pos = gpsAllMapper.selectFile(mac,uid);
        GpsData data = new GpsData();
        data.setDataType(DataType.GPS_DATA.getDataType());
        data.setMac(mac);
        data.setUid(uid);
        if(CollectionUtils.isEmpty(pos)) {
            return MsgInterpreter.success(data);
        }
        Future<List<GpsDataTotal>> future = fileHandler.readGpsFile(pos,uid,mac);
        data.setDataTotal(future.get());
        return MsgInterpreter.success(data);
    }


}
