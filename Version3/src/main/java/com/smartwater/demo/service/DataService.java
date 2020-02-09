package com.smartwater.demo.service;


import com.smartwater.demo.DAO.DataMapper;
import com.smartwater.demo.DAO.ParameterMapper;
import com.smartwater.demo.domain.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DataService {
    @Autowired
    DataMapper dataMapper;
    @Autowired ParameterMapper parameterMapper;
    public void inserDataByDeviceID(int device_id, String time, Integer para1_type, Integer para2_type, Integer para3_type, Integer para4_type, Integer para5_type,
                                    Float para1_value, Float para2_value, Float para3_value, Float para4_value, Float para5_value){
        dataMapper.insertData(device_id,time,para1_type,para2_type,para3_type,para4_type,para5_type,para1_value,para2_value,para3_value,para4_value,para5_value);
    }

    public List<Data> listAllDataByDeviceID(int device_id)
    {
        return dataMapper.selectAllDataByID(device_id);
    }

    public Data listLatestDATAByDeviceID(int device_id)
    {
        return dataMapper.selectTheLastRecordById(device_id);
    }

    public boolean isOutOfRange(Data data)
    {

        if (data.getPara1_value()!=null&&data.getPara1_type()!=null)
        {
            return  (data.getPara1_value()<parameterMapper.mapperPara(data.getPara1_type()).getMin()||data.getPara1_value()>parameterMapper.mapperPara(data.getPara1_type()).getMax());
                //return true;
        }
       if (data.getPara2_value()!=null&&data.getPara2_type()!=null)
       {
           return  (data.getPara2_value()<parameterMapper.mapperPara(data.getPara2_type()).getMin()||data.getPara2_value()>parameterMapper.mapperPara(data.getPara2_type()).getMax());
//               return true;
       }
        if (data.getPara3_value()!=null&&data.getPara3_type()!=null)
        {
            return (data.getPara3_value()<parameterMapper.mapperPara(data.getPara3_type()).getMin()||data.getPara3_value()>parameterMapper.mapperPara(data.getPara3_type()).getMax());
               // return true;
        }
        if (data.getPara4_value()!=null&&data.getPara4_type()!=null)
        {
            return  (data.getPara4_value()<parameterMapper.mapperPara(data.getPara4_type()).getMin()||data.getPara4_value()>parameterMapper.mapperPara(data.getPara4_type()).getMax());
              //  return true;
        }
        if (data.getPara5_value()!=null&&data.getPara5_type()!=null)
        {
            return  (data.getPara5_value()<parameterMapper.mapperPara(data.getPara5_type()).getMin()||data.getPara5_value()>parameterMapper.mapperPara(data.getPara5_type()).getMax());
               // return true;
        }

        return false;
    }
}
