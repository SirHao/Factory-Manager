package com.smartwater.demo.controller;

import com.smartwater.demo.DAO.DeviceMapper;
import com.smartwater.demo.DAO.ParameterMapper;
import com.smartwater.demo.DAO.ResourceMapper;
import com.smartwater.demo.config.WebSecurityConfig;
import com.smartwater.demo.domain.Device;
import com.smartwater.demo.service.DeviceService;
import com.smartwater.demo.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.*;

@Controller
@RequestMapping(value = "changeScale")
public class ScaleController
{
    @Autowired
    DeviceService deviceService;
    @Autowired
    ParameterMapper parameterMapper;
    @Autowired
    ResourceService resourceService;
    @Autowired
    DeviceMapper deviceMapper;
    @RequestMapping(value = "addDevice", method = RequestMethod.GET)
    public String addDevice(HttpSession session, ModelMap map, @ModelAttribute(value = "Device")Device device)
    {
        map.put("username",session.getAttribute(WebSecurityConfig.SESSION_KEY));
        map.put("parameter",parameterMapper.mapperAllPara());
       // map.put("devices",deviceService.listAllFactory());
      //  map.put("username",session.getAttribute(WebSecurityConfig.SESSION_KEY));
        map.put("Interfaces",resourceService.listAllInterfaceResource());
        return "addDevice";
    }

    @RequestMapping(value = "postDataToAddFactory", method = RequestMethod.POST)
    public String postDataToAddFactory(@Valid Device device, ModelMap map, HttpSession session)
    {
        System.out.println(device.getParent_id());
        deviceService.insertNewFactory(device.getName());

        map.put("username",session.getAttribute(WebSecurityConfig.SESSION_KEY));
        map.put("Interfaces",resourceService.listAllInterfaceResource());
        return "redirect:/main";
    }

    @RequestMapping(value = "postDataToAddWorkshop", method = RequestMethod.POST)
    public String postDataToAddWorkshop(@Valid Device device, ModelMap map, HttpSession session)
    {
        System.out.println(device.getFunction_id());
        System.out.println(device.getParent_id());
        deviceService.insertNewWorkshopOrFacility(device.getName(),device.getParent_id(),device.getBrother_id(),device.getFunction_id(),2);

        map.put("username",session.getAttribute(WebSecurityConfig.SESSION_KEY));
        map.put("Interfaces",resourceService.listAllInterfaceResource());
        return "redirect:/main";
    }

    @RequestMapping(value = "postDataToAddFacility",method = RequestMethod.POST)
    public String postDataToAddFacility(@Valid Device device, ModelMap map, HttpSession session)
    {
        deviceService.insertNewWorkshopOrFacility(device.getName(),device.getParent_id(),device.getBrother_id(),device.getFunction_id(),3);
        map.put("username",session.getAttribute(WebSecurityConfig.SESSION_KEY));
        map.put("Interfaces",resourceService.listAllInterfaceResource());
        return "redirect:/main";
    }
    @RequestMapping(value = "/refresh/AttachedFactory")
    @ResponseBody
    public ArrayList<HashMap<String,String>> refresh()
    {
        ArrayList<HashMap<String,String>> temp = new ArrayList<>();
        ArrayList<Device> devices = deviceService.listAllFactory();
        for (Device device:devices)
        {
            HashMap<String,String> tempx = new HashMap<>();
            tempx.put("name",device.getName());
            tempx.put("id",device.getId().toString());
            temp.add(tempx);
        }
        return temp;
    }

    @RequestMapping(value = "refresh/BrotherWorkshop")
    @ResponseBody
    public ArrayList<HashMap<String,String>> refresh2(@RequestParam(value = "factory_id") Integer id)
    {
        ArrayList<Device> devices = deviceService.listAllSubDevice(id);
        ArrayList<HashMap<String,String>> temp = new ArrayList<>();
        for (Device device:devices)
        {
            HashMap<String,String> tempx = new HashMap<>();
            tempx.put("name",device.getName());
            tempx.put("id",device.getId().toString());
            temp.add(tempx);
        }
        return temp;
    }

    @RequestMapping(value = "refresh/AttachedWorkshop")
    @ResponseBody
    public ArrayList<HashMap<String,String>> refresh3(@RequestParam(value = "factory_id") Integer id)
    {
       return refresh2(id);
    }

    @RequestMapping(value = "refresh/BrotherFacility")
    @ResponseBody
    public ArrayList<HashMap<String,String>> refresh4(@RequestParam(value = "workshop_id") Integer id)
    {
        return  refresh2(id);
    }

    @RequestMapping(value="downscale", method = RequestMethod.GET)
    public String deleteDeviceForm(ModelMap map, HttpSession session){
        map.put("username",session.getAttribute(WebSecurityConfig.SESSION_KEY));
        map.put("parameter",parameterMapper.mapperAllPara());
        map.put("Interfaces",resourceService.listAllInterfaceResource());
        map.put("Alldevices",deviceService.listAllDevice());
//        map.put("devices",deviceService.listAllFactory());
        return "downscale";
    }

    @PostMapping(value="deviceDelete")
    public String deleteDevice(@RequestParam(name = "deleteName")String deleteName){
        Device device1=deviceMapper.seleteDeviceByName(deleteName);
        Integer type=device1.getType();
        if(type!=null){
            switch (type){
                case 1:
                    deviceService.deleteFactory(device1);
                    break;
                case 2:
                    deviceService.deleteRoom(device1);
                    break;
                case 3:
                    deviceService.deleteDevice(device1);
                    break;
            }
            return "redirect:/main";
        }
        return "redirect:/#";
    }

}
