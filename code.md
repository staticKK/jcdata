
##### web端数据查询
```
/**
 查询除ecg和gps数据之外的数据
*/
public MessageResult query(Long uid, String mac, int year,int month,@RequestParam(required = false,defaultValue = "0") int day,String dataType) throws Exception;
```
以上上个接口统一调用com.jointcorp.jcdata.service.query接口，定义如下：  
```
/**
uid  查询的用户id
mac 查询的设备mac地址
 year  查询的年
 month    查询的月
day 查询的天
dateTime  查询的时间，格式 yyyy-MM-dd HH:mm:ss
dataType  查询的数据类型
MessageResult query(long uid, String mac, int year, int month, int day, LocalDateTime dateTime,String dataType) throws Exception;
```
该接口通过dataType对要查询的数据类型进行区分，所有数据的类型定义为枚举(enum)类com.jointcorp.jcdata.common.DataType，如下：  

| 字段        | 含义  | 
| --------   | -----  | 
| detailSleepData      | 详细睡眠          | 
| detailSportData      | 详细运动          |  
| dynamicHeartRateData | 动态心率          |   
| staticHeartRateData  | 静态心率          |   
| ecgData              | ECG             |   
| hrvData              | HRV             |   
| sportTypeData        | 运动类型数据      |   
| totalSportData       | 总运动数据        |   
| heartRateData        | 静态心率和动态心率 |   
| gpsData              | gps数据         |   

根据不同的dataType分别调用不同的方法查询处理数据  
