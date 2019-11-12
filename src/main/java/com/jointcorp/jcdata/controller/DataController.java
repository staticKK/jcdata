package com.jointcorp.jcdata.controller;

import com.jointcorp.common.util.MessageResult;
import com.jointcorp.jcdata.common.DataType;
import com.jointcorp.jcdata.service.DataService;
import com.jointcorp.jcdata.utils.DateUtil;
import com.jointcorp.jcdata.vo.Data;
import com.jointcorp.jcdata.vo.GpsData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * Used for app submission data and web page query data
 */
@Controller
@RequestMapping("/data")
public class DataController {

    @Autowired
    private DataService dataService;

    /**
     * App submit data, detailed reference interface documentation
     * @param data
     * @return
     * @throws Exception
     */
    @PostMapping("/save")
    @ResponseBody
    public MessageResult save(@RequestBody Data data) throws Exception {
        return dataService.save(data);
    }

    /**
     * App submits GPS data, detailed reference interface documentation
     * @param data
     * @return
     * @throws Exception
     */
    @PostMapping("/saveGps")
    @ResponseBody
    public MessageResult saveGps(@RequestBody GpsData data) throws Exception {
        return dataService.saveGps(data);
    }

    /**
     * Web query data (except GPS and ECG)
     * @param uid  User id
     * @param mac  MAC Address
     * @param year  Year value
     * @param month Month value
     * @param day  Month of Day
     * @param dataType  Query data type
     * @return  MessageResult
     * @throws Exception
     */
    @GetMapping("/query")
    @ResponseBody
    public MessageResult query(Long uid, String mac, int year,int month,@RequestParam(required = false,defaultValue = "0") int day,String dataType) throws Exception {
        return dataService.query(uid,mac,year,month,day,null,dataType);
    }

    /**
     * Web query ECG data
     * @param uid   User id
     * @param mac    MAC Address
     * @param time
     * @return
     * @throws Exception
     */
    @GetMapping("/queryEcg")
    @ResponseBody
    public MessageResult queryEcg(Long uid, String mac, String time) throws Exception {
        LocalDateTime ldt = DateUtil.parseToLocalDateTime(time);
        return dataService.query(uid,mac,ldt.getYear(),ldt.getMonthValue(),ldt.getDayOfMonth(), ldt,DataType.ECG.getDataType());
    }

    /**
     * Web query GPS data
     * @param uid   User id
     * @param mac    MAC Address
     * @param time
     * @return
     * @throws Exception
     */
    @GetMapping("/queryGps")
    @ResponseBody
    public MessageResult queryGps(Long uid, String mac, String time) throws Exception {
        LocalDateTime ldt = DateUtil.parseToLocalDateTime(time);
        return dataService.query(uid,mac,ldt.getYear(),ldt.getMonthValue(),ldt.getDayOfMonth(), ldt,DataType.GPS_DATA.getDataType());
    }
}