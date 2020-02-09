package com.smartwater.demo.controller;

import com.smartwater.demo.DAO.ParameterMapper;
import com.smartwater.demo.config.WebSecurityConfig;
import com.smartwater.demo.domain.Device;
import com.smartwater.demo.domain.Parameter;
import com.smartwater.demo.service.DeviceService;
import com.smartwater.demo.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ResourceController
{
    @Autowired
    DeviceService deviceService;
    @Autowired
    ParameterMapper parameterMapper;
    @Autowired
    ResourceService resourceService;
    @RequestMapping(value = "/resourceOverview")
    public String response(ModelMap  modelMap, HttpSession session)
    {
       // List<Device> factories = deviceService.listAllFactory();
      //  modelMap.put("devices",factories);
        modelMap.put("Interfaces",resourceService.listAllInterfaceResource());
        modelMap.put("username",session.getAttribute(WebSecurityConfig.SESSION_KEY));
        ArrayList<Device> list  = deviceService.listAllDevice();
        ArrayList<Parameter> parameterArrayList = parameterMapper.mapperAllPara();
        modelMap.put("list",list);
        modelMap.put("parameter",parameterArrayList);
        return "resourceManager";
    }

}
