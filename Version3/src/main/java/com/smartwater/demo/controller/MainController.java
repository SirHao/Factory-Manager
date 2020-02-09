package com.smartwater.demo.controller;

import com.alibaba.fastjson.JSON;
import com.smartwater.demo.DAO.ParameterMapper;
import com.smartwater.demo.config.WebSecurityConfig;
import com.smartwater.demo.domain.Data;
import com.smartwater.demo.domain.Device;
import com.smartwater.demo.domain.Parameter;
import com.smartwater.demo.domain.Resource3D;
import com.smartwater.demo.service.*;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
public class MainController
{
    @Autowired
    DeviceService deviceService;
    @Autowired
    DataService dataService;
    @Autowired
    MergeDataToWorkshopService mergeDataToWorkshopService;
    @Autowired
    DataFlushService dataFlushService;
    @Autowired
    ParameterMapper parameterMapper;
    @Autowired
    ResourceService resourceService;

    @RequestMapping(value = "/main")
    public String main(ModelMap map, HttpSession session)
    {

        map.put("username", session.getAttribute(WebSecurityConfig.SESSION_KEY));
        //List<Device> factories = deviceService.listAllFactory();
       // map.put("devices",factories);
        map.put("Interfaces",resourceService.listAllInterfaceResource());
        return "index.html";
    }

//    @RequestMapping(value = "/main/device/{type}/{id}")
//    public String device(ModelMap map, HttpSession session, @PathVariable String id, @PathVariable String type)
//    {
//        int _id = Integer.parseInt(id);
//        int _type = Integer.parseInt(type);
//        map.put("username",session.getAttribute(WebSecurityConfig.SESSION_KEY));
//
//        if (_type==1)
//        {
//           // List<Device> factories = deviceService.listAllFactory();
//         //   map.put("devices",factories);
//
//            List<Device> subWorkshops = deviceService.SoryByBrotherOrder(deviceService.listAllSubDevice(_id));
//            map.put("subWorkshops",subWorkshops);
////            for(Device workshop :subWorkshops)
////            {
////                if (workshop.ge)
////            }
//            //mergeDataToWorkshopService.buildInformationFlow(_id);
//           // map.put("subWorkshops",dataFlushService.workshopInformation(subWorkshops));
//            //map.put("floatData",mergeDataToWorkshopService.mergeData());
//            return "factoryMonitor.html";
//        }
//        else if(_type==2)
//        {
//            List<Device> workshops = deviceService.listAllWorkshop();
//
//        }
////        List<Device> factories = deviceService.listAllFactory();
////        List<Device> subDevices = deviceService.listAllSubDevice(_id);
////        map.put("devices",factories);
////        map.put("subDevices",subDevices);
////        map.put("username",session.getAttribute(WebSecurityConfig.SESSION_KEY));
////        return "deviceMonitor.html";
//    }

    @RequestMapping(value = "/main/data/{id}")
    public String data(ModelMap map, HttpSession session, @PathVariable String id)
    {
        int _id = Integer.parseInt(id);
        //List<Device> factories = deviceService.listAllFactory();
        List<Data> Datas = dataService.listAllDataByDeviceID(_id);
        String functionID = deviceService.listDeviceByID(_id).getFunction_id();
        map.put("functionID",functionID);
        map.put("username",session.getAttribute(WebSecurityConfig.SESSION_KEY));
       // map.put("devices",factories);
        map.put("Datas",Datas);
        map.put("currentDevice",deviceService.listDeviceByID(_id).getName());
        map.put("Parameter",parameterMapper.mapperAllPara());
        map.put("Interfaces",resourceService.listAllInterfaceResource());
        return "dataMonitor.html";

    }

    @RequestMapping(value = "/OMMonitor/{InterfaceName}")
    public String OMMonitor(ModelMap map, HttpSession session, @PathVariable String InterfaceName) throws IOException
    {

          map.put("username",session.getAttribute(WebSecurityConfig.SESSION_KEY));
          map.put("Interfaces",resourceService.listAllInterfaceResource());
          boolean isFactoryInterface=false;
          Resource3D Interface = resourceService.listResourceByName(InterfaceName);
          map.put("InterfaceName",InterfaceName);
          map.put("heading",InterfaceName.substring(0,InterfaceName.lastIndexOf(".")));
          FileInputStream fin = new FileInputStream(new File(System.getProperty("user.dir") + "/src/main/resources/static/3Dresource/"+InterfaceName));
          byte[] bytes = new byte[fin.available()];
          fin.read(bytes);
          fin.close();
          String img_str = JSON.toJSONString(bytes);
          map.put("InterfaceCoding", img_str.substring(1,img_str.length()-1));
          ArrayList<Device> subDeviceList = deviceService.listAllDeviceByBackgroundName(InterfaceName);
          ArrayList<HashMap<String,String>> Pixel = new ArrayList<>();
          Integer PixelID = 0;
          for (Device subDevice:subDeviceList)
          {
              HashMap<String,String> temp = new HashMap<>();
              temp.put("PixelName",subDevice.getImage_name());
              temp.put("PixelNameError",subDevice.getImage_name_error());
              temp.put("top",subDevice.getTop());
              temp.put("left",subDevice.getLeftu());
              fin = new FileInputStream(new File(System.getProperty("user.dir")+"/src/main/resources/static/3Dresource/"+subDevice.getImage_name()));
              fin.read(bytes);
              fin.close();
              String Pixel_str = JSON.toJSONString(bytes);
              temp.put("PixelCoding",Pixel_str.substring(1,Pixel_str.length()-1));
              fin = new FileInputStream(new File(System.getProperty("user.dir")+"/src/main/resources/static/3Dresource/"+subDevice.getImage_name_error()));
              fin.read(bytes);
              fin.close();
              String Pixel_Error_str = JSON.toJSONString(bytes);
              temp.put("PixelErrorCoding",Pixel_Error_str.substring(1,Pixel_Error_str.length()-1));
              Resource3D resource= resourceService.listResourceByName(subDevice.getImage_name());
              temp.put("PixelWidth",resource.getWidth().toString());
              temp.put("PixelHeight",resource.getHeight().toString());
              temp.put("PixelID",PixelID.toString());
              temp.put("deviceID",subDevice.getId().toString());
              PixelID++;
              Pixel.add(temp);
              if (subDevice.getType()==2)
                  isFactoryInterface=true;

          }
          map.put("PixelList",Pixel);
          map.put("InterfaceHeight",Interface.getHeight());
          map.put("InterfaceWidth",Interface.getWidth());
          map.put("IsFactoryInterface",isFactoryInterface);
          return "OM.html";


    }

    @RequestMapping(value = "/test")
    public String test()
    {
        return "factoryMonitor2";
    }

    //对于工厂鸟瞰图的数据请求
    @RequestMapping(value = "/OMMonitor/PostFactoryInterface")
    @ResponseBody
    public String PostFactoryInterface(@RequestParam(name = "FactoryInterface")String InterfaceName)
    {
       Device device = deviceService.listAllDeviceByBackgroundName(InterfaceName).get(0); //给出某个水厂下面的某个子设备 但不能确定是哪一个
       while(device.getParent_id()!= null) //只要他的父指针不为空
       {
           device = deviceService.listDeviceByID(device.getParent_id());
       }
       //此时该device一定为factory类型
        mergeDataToWorkshopService.buildInformationFlow(device.getId());
       HashMap<Integer,HashMap<String,Float>> result = mergeDataToWorkshopService.mergeData();
        HashMap<String,HashMap<String,Float>> transedResult = new HashMap<>();
        for (Integer device_ID :result.keySet())
       {
           Device device_temp = deviceService.listDeviceByID(device_ID);
           transedResult.put(device_temp.getImage_name(),result.get(device_ID));
       }

      return JSON.toJSONString(transedResult) ;
    }


    //对于车间流程图的数据请求
    @RequestMapping(value = "/OMMonitor/PostWorkshopInterface")
    @ResponseBody
    public String PostWorkshopInterface(@RequestParam(name = "WorkshopInterface")String InterfaceName)
    {
       ArrayList<Device>  devices=  deviceService.listAllDeviceByBackgroundName(InterfaceName);
       HashMap<String,HashMap<String,Float>> result = new HashMap<>();
       for (Device device: devices)
       {
            Data data = dataService.listLatestDATAByDeviceID(device.getId());
            HashMap<String,Float> temp  = new HashMap<>();
            if (data.getPara1_type()!=null)
                temp.put(parameterMapper.mapperPara(data.getPara1_type()).getName(),data.getPara1_value());
            if(data.getPara2_type()!=null)
                temp.put(parameterMapper.mapperPara(data.getPara2_type()).getName(),data.getPara2_value());
            if(data.getPara3_type()!=null)
                temp.put(parameterMapper.mapperPara(data.getPara3_type()).getName(),data.getPara3_value());
            if(data.getPara4_type()!=null)
                temp.put(parameterMapper.mapperPara(data.getPara4_type()).getName(),data.getPara4_value());
            if(data.getPara5_type()!=null)
                temp.put(parameterMapper.mapperPara(data.getPara5_type()).getName(),data.getPara5_value());

            if (dataService.isOutOfRange(data))
                temp.put("Status",new Float(1));
            else
                temp.put("Status",new Float(0));
            result.put(device.getImage_name(),temp);
       }
       return JSON.toJSONString(result);
    }




}
