package com.jointcorp.jcdata.service.handler;

import com.jointcorp.common.util.JsonUtils;
import com.jointcorp.jcdata.entity.GpsValue;
import com.jointcorp.jcdata.utils.HttpUtil;
import com.jointcorp.jcdata.vo.baidu.MapConverResult;
import com.jointcorp.jcdata.vo.baidu.MapConverValue;

import java.util.List;

public class MapConver {

    private static int SIZE = 100;

    private int index = 0;

    public List<GpsValue> conver(List<GpsValue> list) {
        int dataLength = list.size();
        list = recoup(list,null,dataLength);
        return list;
    }

    private String getLnglats(List<GpsValue> list, int dataLength) {
        StringBuilder lnglats = new StringBuilder();
        for (int i = 0; i < SIZE; i++) {
            if (index >= dataLength) {
                return lnglats.toString();
            }
            GpsValue value = list.get(index);
            lnglats.append(value.getLongitude()).append(",").append(value.getLatitude());
            if (index != dataLength - 1 && i != SIZE -1) {
                lnglats.append(";");
            }
            index++;
        }
        return lnglats.toString();
    }

    private List<GpsValue> converPoint(List<GpsValue> list, int dataLength) {
        String lnglats = getLnglats(list,dataLength);
        String url = "http://api.map.baidu.com/geoconv/v1/?coords="+lnglats+"&from=1&to=5&ak=loKXMdsU0cK3SuQvzKqbKOsluzF7aDGt";
        String respStr = null;
        synchronized (MapConver.class) {
            respStr = HttpUtil.httpGet(url);
        }
        MapConverResult result = JsonUtils.jsonToPojo(respStr,MapConverResult.class);
        return recoup(list, result, dataLength);
    }

    private List<GpsValue> recoup(List<GpsValue> list, MapConverResult result, int dataLength) {
        if(result != null) {
            //处理返回的数据
            list = merge(list,result);
        }
        if(index < dataLength) {
            //回调
            return converPoint(list,dataLength);
        } else {
            return list;
        }
    }

    /**
     * 将转换后的坐标替换原来的坐标
     * @param list
     * @param result
     */
    private List<GpsValue> merge(List<GpsValue> list,MapConverResult result) {
        List<MapConverValue> values = result.getResult();
        int size = values.size();
        int j = 0;
        for(int i = index-size; i < index;i++) {
            GpsValue gpsValue = list.get(i);
            MapConverValue converValue = values.get(j);
            gpsValue.setLatitude(converValue.getY());
            gpsValue.setLongitude(converValue.getX());
            list.set(i,gpsValue);
            j++;
        }
        return list;
    }


    public GpsValue converOne(GpsValue point) {
        String lnglat = point.getLongitude() + "," + point.getLatitude();
        String url = "http://api.map.baidu.com/geoconv/v1/?coords="+lnglat+"&from=1&to=5&ak=loKXMdsU0cK3SuQvzKqbKOsluzF7aDGt";
        String respStr = HttpUtil.httpGet(url);
        MapConverResult result = JsonUtils.jsonToPojo(respStr,MapConverResult.class);
        return new GpsValue(null,result.getResult().get(0).getX(),result.getResult().get(0).getY());
    }



    public static void main(String[] args) {
        String json = "";
        List<GpsValue> list = JsonUtils.jsonToList(json,GpsValue.class);

        System.out.println("size : " + list.size());

        int dataLength = list.size();
        list = new MapConver().recoup(list,null,dataLength);

        System.out.println("size : " + list.size());

        System.out.println(JsonUtils.objectToJson(list));
    }
}
