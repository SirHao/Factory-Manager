package com.smartwater.demo.service;

import com.smartwater.demo.domain.Device;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class DataFlushService
{
    public ArrayList<HashMap<String,String>> workshopInformation(List<Device> deviceList)
    {
        //对某个工厂下面的车间信息进行清洗,只保留车间信息的基础数据
        ArrayList<HashMap<String,String>> list = new ArrayList<>();
        Integer i = 0;
        for (Device workshop: deviceList)
        {
            HashMap temp = new HashMap();
            temp.put("id",workshop.getId().toString());
            temp.put("name",workshop.getName());
            temp.put("index",i);
            temp.put("entry_time",workshop.getEntry_time());
            temp.put("image_name",workshop.getImage_name());
            temp.put("function_id",workshop.getFunction_id());
            i++;
            list.add(temp);
        }
        return list;

    }

//    public ArrayList<HashMap<String,String>> facilityInformation(List<Device> deviceList)
//    {
//        //对某个
//    }


}
