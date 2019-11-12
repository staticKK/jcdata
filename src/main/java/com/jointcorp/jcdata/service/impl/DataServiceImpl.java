package com.jointcorp.jcdata.service.impl;

import com.jointcorp.common.util.DateTimeUtil;
import com.jointcorp.common.util.JsonUtils;
import com.jointcorp.common.util.MessageResult;
import com.jointcorp.global.base.Msg;
import com.jointcorp.global.base.MsgInterpreter;
import com.jointcorp.jcdata.common.DataType;
import com.jointcorp.jcdata.common.TableName;
import com.jointcorp.jcdata.config.AppConfig;
import com.jointcorp.jcdata.entity.DataTotal;
import com.jointcorp.jcdata.entity.GpsDataTotal;
import com.jointcorp.jcdata.entity.GpsValue;
import com.jointcorp.jcdata.mapper.GpsMapper;
import com.jointcorp.jcdata.mapper.JcdataMapper;
import com.jointcorp.jcdata.po.*;
import com.jointcorp.jcdata.service.DataService;
import com.jointcorp.jcdata.service.handler.FileHandler;
import com.jointcorp.jcdata.service.handler.MapConver;
import com.jointcorp.jcdata.utils.BaiduscopeUtil;
import com.jointcorp.jcdata.utils.DateUtil;
import com.jointcorp.jcdata.utils.RandomUtil;
import com.jointcorp.jcdata.vo.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DataServiceImpl implements DataService {

    @Autowired
    private JcdataMapper jcdataMapper;
    @Autowired
    private GpsMapper gpsMapper;
    @Autowired
    private AppConfig appConfig;
    @Autowired
    private FileHandler fileHandler;

    @Override
    public MessageResult save(Data data) throws Exception {
        fileHandler.writeDataLog(data);
        if(CollectionUtils.isEmpty(data.getDataTotal())) {
            return MsgInterpreter.success();
        }
        if(CollectionUtils.isEmpty(data.getDataTotal())) {
            return MsgInterpreter.success();
        }
        //删除时间错误的数据
        String dataType = data.getDataType();
        if(dataType.equals(DataType.TOTAL_SPORT_DATA.getDataType())) {
            data.getDataTotal().removeIf(dataTotal -> !DateTimeUtil.matcherYmd(dataTotal.getTime()));
        } else {
            data.getDataTotal().removeIf(dataTotal -> !DateTimeUtil.matcherYmdhms(dataTotal.getTime()));
        }
        if(CollectionUtils.isEmpty(data.getDataTotal())) {
            return MsgInterpreter.success();
        }
        //过滤重复时间的数据
        List<DataTotal> totals= data.getDataTotal().stream().collect(
                Collectors.collectingAndThen(
                        Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(DataTotal::getTime))), ArrayList::new)
        );
        if(CollectionUtils.isEmpty(data.getDataTotal())) {
            return MsgInterpreter.success();
        }

        if(dataType.equals(DataType.TOTAL_SPORT_DATA.getDataType())) {
            //总运动数据
            List<TotalSportDataPO> listPO1 = new ArrayList<>(totals.size());
            List<LocalDate> dates = new ArrayList<>(totals.size());
            totals.forEach(total -> {
                dates.add(DateUtil.parseDate(total.getTime()));
                listPO1.add(TotalSportDataPO.parse(total,data.getMac(),data.getUid()));
            });
            totalSportData(listPO1,dates,data.getMac(),data.getUid());
            if(StringUtils.isNotBlank(data.getDeviceType())) {
                saveDeviceType(data.getMac(),data.getUid(),data.getDeviceType());
            }

            return MsgInterpreter.success();
        }
        List<DataTotalPO> listPO = new ArrayList<>(totals.size());
        List<LocalDateTime> localDateTimes = new ArrayList<>(totals.size());

        totals.forEach(total ->{
            localDateTimes.add(DateUtil.parseDataTime(total.getTime()));
            listPO.add(DataTotalPO.parse(total,data.getMac(),data.getUid()));
        });
        Optional<LocalDateTime> maxDate = localDateTimes.stream().max(LocalDateTime::compareTo);
        Optional<LocalDateTime> minDate = localDateTimes.stream().min(LocalDateTime::compareTo);

        if(dataType.equals(DataType.DETAIL_SPORT.getDataType())) {
            //详细运动
            detailSport(listPO,localDateTimes,maxDate.get(),minDate.get(),data.getMac(),data.getUid());
        } else if(dataType.equals(DataType.SPORT_TYPE_DATA.getDataType())) {
            //运动类型数据
            sportTypeData(listPO,localDateTimes,maxDate.get(),minDate.get(),data.getMac(),data.getUid());
        } else if(dataType.equals(DataType.DETAIL_SLEEP.getDataType())) {
            //详细睡眠数据
            detailSleep(listPO,localDateTimes,maxDate.get(),minDate.get(),data.getMac(),data.getUid());
        } else if(dataType.equals(DataType.DYNAMIC_HEART.getDataType())) {
            //动态心率
            dynamicHeart(listPO,localDateTimes,maxDate.get(),minDate.get(),data.getMac(),data.getUid());
        } else if(dataType.equals(DataType.STATIC_HEART.getDataType())) {
            //静态心率
            staticHeart(listPO,localDateTimes,maxDate.get(),minDate.get(),data.getMac(),data.getUid());
        } else if(dataType.equals(DataType.ECG.getDataType())) {
            //ecg
            ecgData(listPO,localDateTimes,maxDate.get(),minDate.get(),data.getMac(),data.getUid());
        } else if(dataType.equals(DataType.HRV.getDataType())) {
            //hrv
            hrvData(listPO,localDateTimes,maxDate.get(),minDate.get(),data.getMac(),data.getUid());
        }
        return MsgInterpreter.success();
    }

    private void saveDeviceType(String mac, Long uid, String deviceType) throws Exception {
        jcdataMapper.insertLastUseDevice(uid,mac,deviceType,LocalDateTime.now());
    }

    @Override
    public MessageResult saveGps(GpsData data) throws Exception {
        fileHandler.writeGpsdataLog(data);
        if(CollectionUtils.isEmpty(data.getDataTotal())) {
            return MsgInterpreter.success();
        }
        List<GpsDataTotal> totals = data.getDataTotal();
        String mac = data.getMac();
        long uid = data.getUid();

        List<LocalDateTime> localDateTimes = new ArrayList<>(totals.size());
        totals.forEach(total -> localDateTimes.add(DateUtil.parseToLocalDateTime2(total.getTime())));
        Optional<LocalDateTime> maxDate = localDateTimes.stream().max(LocalDateTime::compareTo);
        Optional<LocalDateTime> minDate = localDateTimes.stream().min(LocalDateTime::compareTo);

        //取出一对坐标
        MapConver mapConver = new MapConver();
        GpsValue value = totals.get(0).getDataDetail().get(0);
        value = mapConver.converOne(value);
        String lng = value.getLongitude();
        String lat = value.getLatitude();


        boolean f = BaiduscopeUtil.isInPolygon(lng,lat);
        String region = f ? "CN" : "Other";
        /*
          1.根据时间点找到重复的文件 id和fileName得到重复列表List<ExistFilePO>
          2.取出List<GpsDataTotal>中和List<ExistFilePO>一样时间点的数据，更新文件内容
          3.保存List<GpsDataTotal>中非重复的数据
          4.如果是国内，进行坐标转换，异步操作
         */
        //重复的数据
        List<ExistGpsFilePO> repeatFiles = gpsMapper.selectExistFile(localDateTimes,maxDate.get(),minDate.get(),mac,uid);
        List<GpsDataTotal> repeatData = new ArrayList<>();
        //保存的新数据
        List<DataGpsFilePO> savelist = new ArrayList<>();
        //要更新的数据
        List<DataGpsFilePO> updatelist = new ArrayList<>();

        if(!CollectionUtils.isEmpty(repeatFiles)) {
            //totals 新增的原始数据
            Iterator<GpsDataTotal> it = totals.iterator();
            while (it.hasNext()) {
                GpsDataTotal gpsdata = it.next();
                for (ExistGpsFilePO po : repeatFiles) {
                    if (DateUtil.parseToLocalDateTime2(gpsdata.getTime()).compareTo(po.getTime()) == 0) {
                        repeatData.add(gpsdata);
                        it.remove();
                        break;
                    }
                }
            }
        }
        if(!CollectionUtils.isEmpty(repeatData)) {
            for(GpsDataTotal rd : repeatData) {
                String fileName = "gps/gps_"+String.valueOf(uid) + "_" + mac.replace(":", "") + "_" + DateUtil.format(rd.getTime()) + ".txt";
                LocalDateTime time = DateUtil.parseToLocalDateTime2(rd.getTime());
                DataGpsFilePO po = new DataGpsFilePO(fileName,"",region,uid,mac,time);
                updatelist.add(po);
            }
            gpsMapper.batchUpdatetFile(updatelist);
            fileHandler.writeGpsFile(repeatData,uid,mac);
        }
        if(!CollectionUtils.isEmpty(totals)) {
            for(GpsDataTotal rd : totals) {
                String fileName = "gps/gps_"+String.valueOf(uid) + "_" + mac.replace(":", "") + "_" + DateUtil.format(rd.getTime()) + ".txt";
                LocalDateTime time = DateUtil.parseToLocalDateTime2(rd.getTime());
                DataGpsFilePO po = new DataGpsFilePO(fileName,"",region,uid,mac,time);
                savelist.add(po);
            }
            gpsMapper.batchInsertFile(savelist);
            fileHandler.writeGpsFile(totals,uid,mac);
        }
        //坐标转换
        if(f) {
            converAndSave(totals,repeatData,repeatFiles,uid,mac);
        }
        return MsgInterpreter.success();
    }

    @Async
    public void converAndSave(List<GpsDataTotal> totals, List<GpsDataTotal> repeatData,List<ExistGpsFilePO> repeatFiles,long uid,String mac) throws IOException {
        List<DataGpsFilePO> updatelist = new ArrayList<>();
        if(!CollectionUtils.isEmpty(repeatData)) {
            for(GpsDataTotal rd : repeatData) {
                String converFileName = "gps/conver_"+String.valueOf(uid) + "_" + mac.replace(":", "") + "_" + DateUtil.format(rd.getTime()) + ".txt";
                LocalDateTime time = DateUtil.parseToLocalDateTime2(rd.getTime());
                DataGpsFilePO po = new DataGpsFilePO("",converFileName,"CN",uid,mac,time);
                updatelist.add(po);
            }
        }
        if(!CollectionUtils.isEmpty(totals)) {
            for(GpsDataTotal rd : totals) {
                String converFileName = "gps/conver_"+String.valueOf(uid) + "_" + mac.replace(":", "") + "_" + DateUtil.format(rd.getTime()) + ".txt";
                LocalDateTime time = DateUtil.parseToLocalDateTime2(rd.getTime());
                DataGpsFilePO po = new DataGpsFilePO("",converFileName,"CN",uid,mac,time);
                updatelist.add(po);
            }
        }
        gpsMapper.batchUpdatetConverFile(updatelist);
        fileHandler.writeConverFile(totals,uid,mac);
        fileHandler.writeConverFile(repeatData,uid,mac);
    }

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
        } else if(dataType.equals(DataType.HEART_RATE.getDataType())) {
            return getHeartRateData(startDate,endDate,uid,mac);
        } else if(dataType.equals(DataType.ECG.getDataType())) {
            return getEcgData(dateTime,uid,mac);
        } else if(dataType.equals(DataType.HRV.getDataType())) {
            return getHrvData(startDate,endDate,uid,mac);
        } else if(dataType.equals(DataType.GPS_DATA.getDataType())) {
            return getGpsData(dateTime,uid,mac);
        }
        return MsgInterpreter.error();
    }

    @Override
    public int showGpsIndex(long uid, String mac, String time) throws Exception {
        DataGpsFilePO po = gpsMapper.selectFile(DateUtil.parseToLocalDateTime(time),mac,uid);
        if(po == null || "CN".equals(po.getRegion())) {
            return 1;
        }
        return 0;
    }

    //总运动数据
    private void totalSportData(List<TotalSportDataPO> totals,List<LocalDate> dates,String mac,long uid) throws Exception {
        Optional<LocalDate> maxDate = dates.stream().max(LocalDate::compareTo);
        Optional<LocalDate> minDate = dates.stream().min(LocalDate::compareTo);
        //重复的日期
        List<LocalDate> repeatDate = jcdataMapper.selectExistDateTotalSport(dates,maxDate.get(),minDate.get(),mac,uid);
        List<TotalSportDataPO> repeatSportData = new ArrayList<>();
        if(!CollectionUtils.isEmpty(repeatDate)) {
            //totals 已经是非重复数据了
            Iterator<TotalSportDataPO> it = totals.iterator();
            while (it.hasNext()) {
                TotalSportDataPO po = it.next();
                for (LocalDate aRepeatDate : repeatDate) {
                    if (po.getTime().compareTo(aRepeatDate) == 0) {
                        repeatSportData.add(po);
                        it.remove();
                        break;
                    }
                }
            }
        }
        if(!CollectionUtils.isEmpty(repeatSportData)) {
            jcdataMapper.updateTotalSportData(repeatSportData);
        }
        if(!CollectionUtils.isEmpty(totals)) {
            jcdataMapper.insertTotalSportData(totals);
        }
    }

    //详细运动
    private void detailSport(List<DataTotalPO> totals,List<LocalDateTime> dateTimes,LocalDateTime maxDate,LocalDateTime minDate,
                             String mac,long uid) throws Exception {
        saveOrUpdate(TableName.DETAIL_SPORT.getName(),totals,dateTimes,maxDate,minDate,mac,uid);
    }
    //运动类型数据
    private void sportTypeData(List<DataTotalPO> totals,List<LocalDateTime> dateTimes,LocalDateTime maxDate,LocalDateTime minDate,
                               String mac,long uid) throws Exception {
        saveOrUpdate(TableName.SPORT_TYPE_DATA.getName(),totals,dateTimes,maxDate,minDate,mac,uid);
    }

    //详细睡眠数据
    private void detailSleep(List<DataTotalPO> totals,List<LocalDateTime> dateTimes,LocalDateTime maxDate,LocalDateTime minDate,
                             String mac,long uid) throws Exception {
        saveOrUpdate(TableName.DETAIL_SLEEP.getName(),totals,dateTimes,maxDate,minDate,mac,uid);
    }

    //动态心率
    private void dynamicHeart(List<DataTotalPO> totals,List<LocalDateTime> dateTimes,LocalDateTime maxDate,LocalDateTime minDate,
                              String mac,long uid) throws Exception {
        saveOrUpdate(TableName.DYNAMIC_HEART.getName(),totals,dateTimes,maxDate,minDate,mac,uid);
    }

    //静态心率
    private void staticHeart(List<DataTotalPO> totals, List<LocalDateTime> dateTimes, LocalDateTime maxDate, LocalDateTime minDate,
                             String mac, long uid) throws Exception {
        saveOrUpdate(TableName.STATIC_HEART.getName(), totals, dateTimes, maxDate, minDate, mac, uid);
    }

    //ecg
    private void ecgData(List<DataTotalPO> totals,List<LocalDateTime> dateTimes,LocalDateTime maxDate,LocalDateTime minDate,
                         String mac,long uid) throws Exception {
        //存文件,ecg不会重复
        String fileName = "ecg/ecg_"+String.valueOf(uid) + "_" + mac.replace(":", "") +"_" + DateUtil.format3(totals.get(0).getTime()) + ".txt";
        FileUtils.writeStringToFile(new File(appConfig.getDataFilePath()+fileName),totals.get(0).getDataDetail(),"utf-8");
        LocalDateTime time = totals.get(0).getTime();
        jcdataMapper.insertFile(TableName.ECG.getName(),fileName,time,mac,uid);
    }

    //hrv
    private void hrvData(List<DataTotalPO> totals,List<LocalDateTime> dateTimes,LocalDateTime maxDate,LocalDateTime minDate,
                         String mac,long uid) throws Exception {
        saveOrUpdate(TableName.HRV.getName(),totals,dateTimes,maxDate,minDate,mac,uid);
    }

    private void saveOrUpdate(String tableName,List<DataTotalPO> totals,List<LocalDateTime> dateTimes,LocalDateTime maxDate,LocalDateTime minDate,
                              String mac,long uid) throws Exception {
        List<LocalDateTime> repeatDate = jcdataMapper.selectExistDate(tableName,dateTimes,maxDate,minDate,mac,uid);
        List<DataTotalPO> repeatData = new ArrayList<>();
        if(!CollectionUtils.isEmpty(repeatDate)) {
            //totals 已经是非重复数据了
            Iterator<DataTotalPO> it = totals.iterator();
            while (it.hasNext()) {
                DataTotalPO po = it.next();
                for (LocalDateTime dt : repeatDate) {
                    if (po.getTime().compareTo(dt) == 0) {
                        repeatData.add(po);
                        it.remove();
                        break;
                    }
                }
            }
        }
        if(!CollectionUtils.isEmpty(repeatData)) {
            jcdataMapper.updateData(tableName,repeatData);
        }
        if(!CollectionUtils.isEmpty(totals)) {
            jcdataMapper.insertData(tableName,totals);
        }
    }

    //总运动数据
    private MessageResult getTotalSportData(LocalDate startDate, LocalDate endDate, long uid, String mac) throws Exception {
        List<TotalSportDataPO> totals = jcdataMapper.selectTotalSprot(startDate,endDate,uid,mac);

        /*
            总运动数据数组：[步数,运动时间(单位：second),距离(单位：km),卡路里(单位：kcal),目标完成率(单位：%),强度运动时间(单位：second)]
         */
        int size = endDate.getDayOfMonth();
        String[] steps = createStringArray(size);
        String[] sportTimes = createStringArray(size);
        String[] distances = createStringArray(size);
        String[] calories = createStringArray(size);
        String[] goals = createStringArray(size);
        String[] strengths = createStringArray(size);

        for (TotalSportDataPO po : totals) {
            String detailData = po.getDataDetail();
            LocalDate day = po.getTime();
            int index = day.getDayOfMonth() - 1;

            String[] arr = detailData.split(",");
            steps[index] = arr[0];
            sportTimes[index] = (arr[1]);
            distances[index] = (arr[2]);
            calories[index] = (arr[3]);
            goals[index] = (arr[4]);
            strengths[index] = (arr[5]);
        }
        TotalSportDataVO vo = new TotalSportDataVO(steps,sportTimes,distances,calories,goals,strengths);
        return MsgInterpreter.success(vo);
    }

    //详细运动
    private MessageResult getDetailSportData(LocalDateTime startDate, LocalDateTime endDate, long uid, String mac) throws Exception {
        List<DataTotalPO> totals = jcdataMapper.selectData(TableName.DETAIL_SPORT.getName(),startDate,endDate,uid,mac);
        /*
         详细运动数组：每个点都是步数，1分钟一个点，1440个点,从0点0分到23点59分
         */
        String[] details = createStringArray(1440);
        String[] timeVaule = createXWithHm1440(startDate);
        for(DataTotalPO po : totals) {
            //起始位置索引
            int index = DateUtil.durationTo0(po.getTime());
            String detailData = po.getDataDetail();
            String[] arr = detailData.split(",");
            for(int i = 0; i < arr.length;i++) {
                if(!arr[i].equals("0")) {
                    details[index+i] = arr[i];
                }
            }
        }
        DetailDataVO vo = new DetailDataVO(details,timeVaule);
        return MsgInterpreter.success(vo);
    }

    private MessageResult getSportTypeData(LocalDateTime startDate, LocalDateTime endDate, long uid, String mac) throws Exception {
        /*
        数组值: [运动类型,心率,运动时间,步数,配速,卡路里,距离]
        配速:当前值%256:当前值/256  结果：  4'56"
         */
        List<DataTotalPO> sportTypeData = jcdataMapper.selectData(TableName.SPORT_TYPE_DATA.getName(),startDate,endDate,uid,mac);
        List<SportTypeData> list = new ArrayList<>();
        for(DataTotalPO po : sportTypeData) {
            String detail = po.getDataDetail();
            String[] arr = detail.split(",");
            int s = Integer.parseInt(arr[4]);
            String time = (s/60) + ":" + (s%60);
            String speed = (s%256) + "'" + (s/256) + "\"";
            SportTypeData data = new SportTypeData(getSportType(Integer.parseInt(arr[0])),arr[1],time,arr[3],speed,arr[5],arr[6],DateUtil.format(po.getTime()));
            list.add(data);
        }
        return MsgInterpreter.success(list);
    }

    private MessageResult getDetailSleepData(int year,int month,int day,long uid, String mac) throws Exception {
        LocalDateTime startDate = LocalDateTime.of(year,month,day-1,12,0,0);
        LocalDateTime endDate = LocalDateTime.of(year,month,day,12,0,0);
        List<DataTotalPO> totals = jcdataMapper.selectData(TableName.DETAIL_SLEEP.getName(),startDate,endDate,uid,mac);
        /*
         详细睡眠数组，每5分钟一个点共288个点,睡眠时间是12:00-12:00
         小于30分钟的离床认为非离床
         */
        String[] details = createStringArray(288);
        String[] timeVaule = createXWithHm288(startDate);
        for(DataTotalPO po : totals) {
            //起始位置索引
            int index = DateUtil.durationToyesterday12(po.getTime(),startDate) / 5;
            String detailData = po.getDataDetail();
            String[] arr = detailData.split(",");

            for(int i = 0; i < arr.length;i++) {
                if(!arr[i].equals("0")) {
                    if(Integer.parseInt(arr[i]) > 80) {
                        details[index+i] = String.valueOf(RandomUtil.getInstance().getRandom(77,80));
                    } else {
                        details[index+i] = arr[i];
                    }
                    timeVaule[index+i] = DateUtil.formatHm(po.getTime().plusMinutes((i*4)));
                }
            }
        }
        boolean inBed = false;
        boolean outBed = true;
        //[y,x]
        List<String[]> inList = new ArrayList<>();
        List<String[]> outList = new ArrayList<>();
        for(int i = 0;i < details.length;i++) {
            if(!details[i].equals("0")) {
                if(!inBed) {
                    inBed = true;
                    outBed = false;
                    String[] a = {timeVaule[i],details[i]};
                    inList.add(a);
                }
            } else {
                if(!outBed) {
                    if(i > 6 && details[i-1].equals("0") && details[i-2].equals("0") && details[i-3].equals("0") && details[i-4].equals("0") && details[i-5].equals("0")) {
                        inBed = false;
                        outBed = true;
                        String[] b = {timeVaule[i-5],details[i]};
                        outList.add(b);
                    }
                }
            }
        }
        SleepDataVO vo = new SleepDataVO(details,timeVaule,inList,outList);
        return MsgInterpreter.success(vo);
    }

    private MessageResult getHeartRateData(LocalDateTime startDate, LocalDateTime endDate, long uid, String mac) throws Exception {
        //如果同一时间点有动态和静态，则认为是动态，画图的时候，间隔时间大于60分钟，则断开
        /*
         对于echart的断开实际上是前面的值都是空
         data: [820, 932, 901, 934],
         data: ['-', '-', '-', '-','999',767,833,1033,977],
         series上这两个data在第4和5之间才会断开
         */
        List<DataTotalPO> staticHeartRateData = jcdataMapper.selectData(TableName.STATIC_HEART.getName(),startDate,endDate,uid,mac);
        List<DataTotalPO> dynamicHeartRateData = jcdataMapper.selectData(TableName.DYNAMIC_HEART.getName(),startDate,endDate,uid,mac);

        String[] details = createStringArraySetValue(1440);
        String[] timeVaule = createXWithHm1440(startDate);
        staticHeartRateData.forEach(po ->{
            //静态心率只有一个值
            String value = po.getDataDetail();
            int index = DateUtil.durationTo0(po.getTime());
            details[index] = value;
        });
        for (DataTotalPO dy : dynamicHeartRateData) {
            String detailData = dy.getDataDetail();
            String[] arr = detailData.split(",");
            LocalDateTime dyTime = dy.getTime();
            //起始位置索引
            int index = DateUtil.durationTo0(dyTime);
            for (int j = 0; j < arr.length; j++) {
                if (!"0".equals(arr[j])) {
                    details[index + j] = arr[j];
                }
            }
        }
        List<String[]> y = new ArrayList<>();
        int lastIndex = 0;
        for(int i = 0; i < details.length;i++) {

            String value = details[i];
            if(!"-".equals(value)) {
                if(continuously(details,i)) {
                    if(lastIndex == 0) {
                        //断开
                        String[] values = createStringArraySetValue(i+1);
                        System.arraycopy(details,lastIndex,values,0,i + 1);
                        y.add(values);
                    } else {
                        //断开
                        String[] values = createStringArraySetValue(i+1);
                        System.arraycopy(details,lastIndex+1,values,lastIndex+1,i-lastIndex);
                        y.add(values);
                    }
                    lastIndex = i;
                }
            }
        }
        if(lastIndex == 0) {
            y.add(details);
        }

        return MsgInterpreter.success(new HeartRatDataVO(y,timeVaule));
    }

    /**
     * 当前点之后的60个点是否有值
     * @param details
     * @param index
     * @return 有值返回false
     */
    private static boolean continuously(String[] details,int index) {
        for(int i = index+1; i < index+59;i++) {
            if(i >= 1440) {
                return true;
            }
            String value = details[i];
            if(!"-".equals(value)) {
                return false;
            }
        }
        return true;
    }


    private MessageResult getHrvData(LocalDateTime startDate, LocalDateTime endDate, long uid, String mac) throws Exception {
        /*
        hrv数组:
        [
        hrv值,
        血管老化度(等级1-6，值越小越好),
        心率值,
        压力值(0-100),0-25,25-50,50-75,75-100,越小越放松,
        血压高压(舒张压),
        低压(收缩压)
        ]
         */
        List<DataTotalPO> hrvDatas = jcdataMapper.selectData(TableName.HRV.getName(),startDate,endDate,uid,mac);
        List<HrvDataVO> list = new ArrayList<>();
        for(DataTotalPO po : hrvDatas) {
            String s = po.getDataDetail();
            String[] arr = s.split(",");
            HrvDataVO vo = new HrvDataVO(arr[0],arr[1],arr[2],arr[3],arr[4],arr[5],DateUtil.format(po.getTime()));
            list.add(vo);
        }
        return MsgInterpreter.success(list);
    }

    private MessageResult getEcgData(LocalDateTime dateTime, long uid, String mac) throws Exception {
        DataFilePO po = jcdataMapper.selectFile(TableName.ECG.getName(),dateTime,mac,uid);
        if(po == null) {
            return MsgInterpreter.success(new String[]{});
        }
        String fileName = po.getFileName();
        fileName = appConfig.getDataFilePath()+ fileName;
        //读取出来已经是一个字符串数组
        String str = FileUtils.readFileToString(new File(fileName),"utf-8");
        String[] array = str.split(",");
        return MsgInterpreter.success(array);
    }

    private MessageResult getGpsData(LocalDateTime dateTime, long uid, String mac) throws Exception {
        /*
         两个点之前的距离>2m && 距离小于100m || 两个点之间的速度<50m/s
         */
        DataGpsFilePO po = gpsMapper.selectFile(dateTime,mac,uid);
        if(Objects.isNull(po)) {
            return MsgInterpreter.error(Msg.DATA_NULL);
        }
        String fileName = appConfig.getDataFilePath();

        if("CN".equals(po.getRegion())) {
            if(StringUtils.isBlank(po.getConverFileName())) {
                return MsgInterpreter.error(Msg.DATA_NULL);
            }
            fileName = fileName + po.getConverFileName();
        } else {
            if(StringUtils.isBlank(po.getFileName())) {
                return MsgInterpreter.error(Msg.DATA_NULL);
            }
            fileName = fileName + po.getFileName();
        }
        //读取出来已经是一个字符串数组
        String str = FileUtils.readFileToString(new File(fileName),"utf-8");
        List<GpsValue> list = JsonUtils.jsonToList(str,GpsValue.class);
        //排序
        list = list.stream().sorted(Comparator.comparing(GpsValue::getGpsDate)).collect(Collectors.toList());
        return MsgInterpreter.success(list);
    }

    /**
     * 创建一个String数组，初始化为"0"
     * @param size
     * @return
     */
    private String[] createStringArray(int size) {
        String[] strArray = new String[size];
        return Arrays.stream(strArray).map(str -> "0").toArray(String[]::new);
    }

    /**
     * 创建一个String数组，初始化为"-"
     * @param size
     * @return
     */
    private String[] createStringArraySetValue(int size) {
        return Arrays.stream(new String[size]).map(str -> "-").toArray(String[]::new);
    }

    private String[] createXWithHm288(LocalDateTime time) {
        String[] strArray = new String[288];
        for(int i = 0; i < 288;i++) {
            strArray[i] = DateUtil.formatHm(time.plusMinutes(i*5));
        }
        return strArray;
    }

    private String[] createXWithHm1440(LocalDateTime time) {
        String[] strArray = new String[1440];
        for(int i = 0; i < 1440;i++) {
            strArray[i] = DateUtil.formatHm(time.plusMinutes(i*1));
        }
        return strArray;
    }

    private String getSportType(int type) {
        switch (type) {
            case 0: {
                return "Running";
            }
            case 1: {
                return "Cycling";
            }
            case 2: {
                return "Badminton";
            }
            case 3: {
                return "Football";
            }
            case 4: {
                return "Tennis";
            }
            case 5: {
                return "Yoga";
            }
            case 6: {
                return "Meditation";
            }
            case 7: {
                return "Dancing";
            }
            case 8: {
                return "Baskedball";
            }
            case 9: {
                return "Hiking";
            }
            case 10: {
                return "Workout";
            }
            default:
                return "Running";
        }
    }

}
