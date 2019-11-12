package com.jointcorp.jcdata.controller;

import com.jointcorp.common.util.DateTimeUnit;
import com.jointcorp.common.util.DateTimeUtil;
import com.jointcorp.common.util.MessageResult;
import com.jointcorp.global.base.Msg;
import com.jointcorp.global.base.MsgInterpreter;
import com.jointcorp.jcdata.common.DataType;
import com.jointcorp.jcdata.service.AppDataQueryAllService;
import com.jointcorp.jcdata.service.AppDataQueryService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 只用于app返回数据
 */
@Controller
@RequestMapping("/appData")
public class AppDataController {

    @Autowired
    private AppDataQueryService appDataService;
    @Autowired
    private AppDataQueryAllService dataQueryAllService;

    @GetMapping("/query")
    @ResponseBody
    public MessageResult query(Long uid, String mac, String time,String dataType) throws Exception {
        if(StringUtils.isBlank(time)) {
            return dataQueryAllService.query(uid,mac,dataType);
        }
        int year = 0;
        int month = 0;
        int day = 0;
        LocalDateTime dateTime = null;

        if(dataType.equals(DataType.TOTAL_SPORT_DATA.getDataType())) {
            String ymStr = time+".01";
            LocalDate ym = DateTimeUtil.parseLocalDate(ymStr, DateTimeUnit.ymdSegDot);
            year = ym.getYear();
            month = ym.getMonthValue();

            MessageResult result = appDataService.query(uid,mac,year,month,day,dateTime,dataType);

            return result;
        } else if (dataType.equals(DataType.DETAIL_SPORT.getDataType()) ||
                dataType.equals(DataType.SPORT_TYPE_DATA.getDataType()) ||
                dataType.equals(DataType.DETAIL_SLEEP.getDataType()) ||
                dataType.equals(DataType.STATIC_HEART.getDataType()) ||
                dataType.equals(DataType.DYNAMIC_HEART.getDataType()) ||
                dataType.equals(DataType.HRV.getDataType())) {
            LocalDate ymd = DateTimeUtil.parseLocalDate(time, DateTimeUnit.ymdSegDot);
            year = ymd.getYear();
            month = ymd.getMonthValue();
            day = ymd.getDayOfMonth();

            MessageResult result = appDataService.query(uid,mac,year,month,day,dateTime,dataType);

            return result;
        }  else if(dataType.equals(DataType.ECG.getDataType()) || dataType.equals(DataType.GPS_DATA.getDataType())) {
            dateTime = DateTimeUtil.parseLocalDateTime(time,DateTimeUnit.ymdhmsSegDot);
            year = dateTime.getYear();
            month = dateTime.getMonthValue();
            day = dateTime.getDayOfMonth();
            MessageResult result = appDataService.query(uid,mac,year,month,day,dateTime,dataType);
            return result;
        }
        return MsgInterpreter.error(Msg.PARAM_ERR);
    }

    @GetMapping("/queryDeviceType")
    @ResponseBody
    public MessageResult queryDeviceType(Long uid) throws Exception {
        return appDataService.queryDeviceType(uid);
    }


}
