package com.jointcorp.jcdata.service.handler;

import com.jointcorp.common.util.DateTimeUnit;
import com.jointcorp.common.util.DateTimeUtil;
import com.jointcorp.common.util.JsonUtils;
import com.jointcorp.jcdata.common.DataType;
import com.jointcorp.jcdata.config.AppConfig;
import com.jointcorp.jcdata.entity.DataTotal;
import com.jointcorp.jcdata.entity.GpsDataTotal;
import com.jointcorp.jcdata.entity.GpsValue;
import com.jointcorp.jcdata.po.DataFilePO;
import com.jointcorp.jcdata.po.DataGpsFilePO;
import com.jointcorp.jcdata.po.ExistGpsFilePO;
import com.jointcorp.jcdata.utils.DateUtil;
import com.jointcorp.jcdata.vo.Data;
import com.jointcorp.jcdata.vo.GpsData;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Future;

@Component
public class FileHandler {

    @Autowired
    private AppConfig appConfig;

    @Async
    public void writeDataLog(Data data) {
        String fileName = "logs/"+DateTimeUtil.format(LocalDateTime.now(),"yyyyMMddHHmmss")+"_"+ data.getUid() +"_"+ data.getMac().replace(":","") +"_"+ data.getDataType()+".txt";
        try {
            FileUtils.writeStringToFile(new File(appConfig.getDataFilePath() + fileName), JsonUtils.objectToJson(data),"utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Async
    public void deleteGpsFile(List<ExistGpsFilePO> repeatFiles) {
        //删除无用的文件
        if(!CollectionUtils.isEmpty(repeatFiles)) {
            for(ExistGpsFilePO po : repeatFiles) {
                String file = appConfig.getDataFilePath() + po.getFileName();
                FileUtils.deleteQuietly(new File(file));
            }
        }
    }

    @Async
    public void writeGpsdataLog(GpsData data) {
        String fileName = "logs/"+DateTimeUtil.format(LocalDateTime.now(),"yyyyMMddHHmmss")+"_"+ data.getUid() +"_"+ data.getMac().replace(":","") +"_"+ data.getDataType()+".txt";
        try {
            FileUtils.writeStringToFile(new File(appConfig.getDataFilePath() + fileName), JsonUtils.objectToJson(data),"utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Async
    public void writeGpsFile(List<GpsDataTotal> totals,long uid,String mac) throws IOException {
        for(GpsDataTotal rd : totals) {
            String fileName = "gps/gps_"+String.valueOf(uid) + "_" + mac.replace(":", "") + "_" + DateUtil.format(rd.getTime()) + ".txt";
            FileUtils.writeStringToFile(new File(appConfig.getDataFilePath() + fileName), JsonUtils.objectToJson(rd.getDataDetail()),"utf-8");
        }
    }

    @Async
    public void writeConverFile(List<GpsDataTotal> totals,long uid,String mac) throws IOException {
        for(GpsDataTotal rd : totals) {
            MapConver mapConver2 = new MapConver();
            List<GpsValue> result = mapConver2.conver(rd.getDataDetail());
            String converFileName = "gps/conver_"+String.valueOf(uid) + "_" + mac.replace(":", "") + "_" + DateUtil.format(rd.getTime()) + ".txt";
            FileUtils.writeStringToFile(new File(appConfig.getDataFilePath() + converFileName), JsonUtils.objectToJson(result),"utf-8");
        }
    }

    @Async
    public Future<List<Data>> readEcgFile(List<DataFilePO> pos, long uid, String mac) throws IOException {
        List<Data> dataList = new ArrayList<>(pos.size());
        for(DataFilePO po : pos) {
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
            dataList.add(data);
        }
        return new AsyncResult<>(dataList);
    }

    @Async
    public Future<List<GpsDataTotal>> readGpsFile(List<DataGpsFilePO> pos, long uid, String mac) throws IOException {
        List<GpsDataTotal> list = new ArrayList<>();
        for(DataGpsFilePO po : pos) {
            String fileName = appConfig.getDataFilePath() + po.getFileName();
            //读取出来已经是一个字符串数组
            String str = FileUtils.readFileToString(new File(fileName),"utf-8");
            List<GpsValue> gpsValue = JsonUtils.jsonToList(str,GpsValue.class);
            GpsDataTotal dataTotal = new GpsDataTotal();
            dataTotal.setDataDetail(gpsValue);
            dataTotal.setTime(DateTimeUtil.format(po.getTime(), DateTimeUnit.ymdhmsSegDot));
            list.add(dataTotal);
        }
        return new AsyncResult<>(list);
    }
}
