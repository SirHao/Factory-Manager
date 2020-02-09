package com.smartwater.demo.config;

import com.smartwater.demo.DAO.DeviceMapper;
import com.smartwater.demo.DAO.ParameterMapper;
import com.smartwater.demo.domain.Data;
import com.smartwater.demo.domain.Device;
import com.smartwater.demo.domain.Parameter;
import com.smartwater.demo.service.DataService;
import com.alibaba.fastjson.*;
import com.smartwater.demo.service.AsyncQueryService;
import com.smartwater.demo.service.DeviceService;
import com.smartwater.demo.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

//@Configuration("myScheduled")
//@EnableScheduling
public class QueryConfig {
    @Autowired
    AsyncQueryService asyncQueryService;

    @Autowired
    DataService dataService;

    @Autowired
    ParameterMapper parameterMapper;

    @Autowired
    MailService mailService;

    @Autowired
    DeviceMapper deviceMapper;

    private Device device;


    @Scheduled(cron = "0/10 * * * * ?")
    public void timer() throws InterruptedException {
        String wrongMessage="";
        String a = asyncQueryService.QueryInformation();
        System.out.println(a);
        List<Data> Datas = JSONArray.parseArray(a, Data.class);
        List<Parameter> parameters=parameterMapper.mapperAllPara();
        Integer statu=0;
        for(Data data:Datas){
            //=================

            String wrong=null;
            wrong=checker(data,parameters);
            if(wrong!=null){
                statu=1;
                device=deviceMapper.selectDeviceByID(data.getDevice_id());
                wrongMessage=wrongMessage+device.getName()+": "+wrong+"\n";
            }


            //==================
            dataService.inserDataByDeviceID(data.getDevice_id(),data.getTime(),data.getPara1_type(),data.getPara2_type(),data.getPara3_type(),data.getPara4_type(),data.getPara5_type(),data.getPara1_value(),data.getPara2_value(),data.getPara3_value(),data.getPara4_value(),data.getPara5_value());
            //System.out.println(data.getDevice_id()+" "+data.getPara4_value());

        }
        if(statu==1)
            mailService.sendSimpleMail(wrongMessage);

    }
    public String checker(Data data,List<Parameter> parameters){
        String statue1="";
        if(data.getPara1_type()!=null){
            for(Parameter parameter :parameters){
                if(parameter.getId()==data.getPara1_type()){
                    if(parameter.getMax()<data.getPara1_value())
                        statue1=statue1+parameter.getName()+" 过高";
                    if(parameter.getMin()>data.getPara1_value())
                        statue1=statue1+parameter.getName()+" 过低";
                }
            }
        }
        if(data.getPara2_type()!=null){
            for(Parameter parameter :parameters){
                if(parameter.getId()==data.getPara2_type()){
                    if(parameter.getMax()<data.getPara2_value())
                        statue1=statue1+parameter.getName()+" 过高";
                    if(parameter.getMin()>data.getPara2_value())
                        statue1=statue1+parameter.getName()+" 过低";
                }
            }
        }
        if(data.getPara3_type()!=null){
            for(Parameter parameter :parameters){
                if(parameter.getId()==data.getPara3_type()){
                    if(parameter.getMax()<data.getPara3_value())
                        statue1=statue1+parameter.getName()+" 过高";
                    if(parameter.getMin()>data.getPara3_value())
                        statue1=statue1+parameter.getName()+" 过低";
                }
            }
        }
        if(data.getPara4_type()!=null){
            for(Parameter parameter :parameters){
                if(parameter.getId()==data.getPara4_type()){
                    if(parameter.getMax()<data.getPara4_value())
                        statue1=statue1+parameter.getName()+" 过高";
                    if(parameter.getMin()>data.getPara4_value())
                        statue1=statue1+parameter.getName()+" 过低";
                }
            }
        }
        if(data.getPara5_type()!=null){
            for(Parameter parameter :parameters){
                if(parameter.getId()==data.getPara5_type()){
                    if(parameter.getMax()<data.getPara5_value())
                        statue1=statue1+parameter.getName()+" 过高";
                    if(parameter.getMin()>data.getPara5_value())
                        statue1=statue1+parameter.getName()+" 过低";
                }
            }
        }
        if(statue1=="")
            return  null;
        else
            return statue1;
    }
}