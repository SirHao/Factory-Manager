package com.smartwater.demo.controller;

import com.alibaba.fastjson.JSON;
import com.smartwater.demo.config.WebSecurityConfig;
import com.smartwater.demo.domain.Device;
import com.smartwater.demo.domain.Resource3D;
import com.smartwater.demo.service.DeviceService;
import com.smartwater.demo.service.ResourceService;
import com.sun.tools.corba.se.idl.InterfaceGen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


@Controller
@RequestMapping("/UIConfig")
public class UIConfigController {
    @Autowired
    ResourceService resourceService;
    @Autowired
    DeviceService deviceService;

    @RequestMapping(value = "/PixelManage")
    public String PixelManage(ModelMap map, HttpSession session)
    {
        map.put("username",session.getAttribute(WebSecurityConfig.SESSION_KEY));
        map.put("Interfaces",resourceService.listAllInterfaceResource());
     //   map.put("devices",deviceService.listAllFactory());
     //   map.put("PixelList",resourceService.listAllPixelResource());
        return "pixelManage";
    }

    @RequestMapping(value = "/PixelManage/PixelShow")
    @ResponseBody
    public ArrayList<HashMap<String,String>> ajaxToImage() throws IOException {
        ArrayList<Resource3D> ResourceArr = resourceService.listAllPixelResource();
//        ArrayList<String> imgNameArr = new ArrayList<>();
        ArrayList<HashMap<String,String>> PixelRow = new ArrayList<>();
        for (Resource3D resource : ResourceArr) {
            HashMap<String,String> temp = new HashMap<>();
            temp.put("name",resource.getName());
            FileInputStream fin = new FileInputStream(new File(System.getProperty("user.dir") + "/src/main/resources/static/3Dresource/"+resource.getName()));
            byte[] bytes = new byte[fin.available()];
            fin.read(bytes);
            fin.close();
            temp.put("img",JSON.toJSONString(bytes));
//            imgNameArr.add(resource.getName());
            PixelRow.add(temp);
        }
//
        return  PixelRow;
    }

    @PostMapping(value = "/PixelManage/SinglePixelShow")
    @ResponseBody
    public ArrayList<String> ajaxToSingleImage(@RequestParam(value = "PixelName")String  PixelName,@RequestParam(value = "PixelNameError")String PixelNameError) throws IOException
    {
        ArrayList<String> information = new ArrayList<>();
        FileInputStream fin = new FileInputStream(new File(System.getProperty("user.dir") + "/src/main/resources/static/3Dresource/"+PixelName));
        byte[] bytes = new byte[fin.available()];
        fin.read(bytes);
        fin.close();
        information.add(JSON.toJSONString(bytes));

        fin = new FileInputStream(new File(System.getProperty("user.dir")+"/src/main/resources/static/3Dresource/"+PixelNameError));
        bytes = new byte[fin.available()];
        fin.read(bytes);
        fin.close();
        information.add(JSON.toJSONString(bytes));
        return information;

    }

    @RequestMapping(value = "/InterfaceManager")
    public String InterfaceManager(ModelMap map)
    {
        map.put("NotBindResourceDeviceList",deviceService.listAllDevice());
        map.put("Interfaces",resourceService.listAllInterfaceResource());
        return "interfaceManage";
    }

    @RequestMapping(value = "/InterfaceManager/InterfaceShow")
    @ResponseBody
    public ArrayList<HashMap<String,String>> ajax()
    {
        ArrayList<Resource3D> ResourceArr = resourceService.listAllInterfaceResource();
        ArrayList<HashMap<String,String>> InterfaceRow = new ArrayList<>();
        for (Resource3D resource: ResourceArr)
        {
            HashMap<String,String> temp = new HashMap<>();
            temp.put("name",resource.getName());
            temp.put("widthAndHeight",resource.getWidth().toString()+"X"+resource.getHeight().toString());
            temp.put("comment",resource.getComment());
            InterfaceRow.add(temp);
        }
        return InterfaceRow;
    }

    @PostMapping(value = "/InterfaceManager/MoreInformation" )
    @ResponseBody
    public HashMap<String,String> MoreInformation(@RequestParam(name="name")String name) throws  IOException
    {

      // Resource3D resource= resourceService.listResourceByName(name);
        FileInputStream fin = new FileInputStream(new File(System.getProperty("user.dir") + "/src/main/resources/static/3Dresource/"+name));
        byte[] bytes = new byte[fin.available()];
        fin.read(bytes);
        fin.close();
        HashMap<String,String> information = new HashMap<>();
        information.put("background",JSON.toJSONString(bytes));
        ArrayList<Device> deviceArrayList = deviceService.listAllDeviceByBackgroundName(name);
        information.put("deviceList",JSON.toJSONString(deviceArrayList));
        return information;
    }

    @PostMapping(value = "/BindPixel")
    public String BindPixel(@RequestParam(name= "PostBindInterface") String PostBindInterface,
                            @RequestParam(name="PostBindResource") Integer PostBindResource, ModelMap map)
    {

        map.put("PostBindInterface",PostBindInterface);
        Device device = deviceService.listDeviceByID(PostBindResource);
        map.put("Interfaces",resourceService.listAllInterfaceResource());
        String finalPathReverse= "";
        finalPathReverse = device.getName();
      //  Integer type = device.getType();
        while(device.getParent_id()!=null)
        {
            device = deviceService.listDeviceByID(device.getParent_id());
            finalPathReverse = finalPathReverse + "<-"+device.getName();
        }
       String Path[] = finalPathReverse.split("<-");
        String finalPath = "";
        for (int i=Path.length-1;i>0;i--)
        {
           finalPath = finalPath+Path[i]+"->";
        }
        finalPath = finalPath+ Path[0];
        map.put("PostBindResource",finalPath);
        map.put("PixelList",resourceService.listAllPixelResource());
        map.put("DeviceID",PostBindResource);
        return "BindPixel.html";
    }

    //提交绑定图元信息表单
    @PostMapping(value = "/InterfaceManager/BindPixel/PostBindInformation")
    @ResponseBody
    public String ajaxToPostInformationBind(@RequestParam(name = "InterfaceName") String InterfaceName,
                                          @RequestParam(name = "deviceID") Integer deviceID,
                                          @RequestParam(name="LeftOffset") String LeftOffset,
                                          @RequestParam(name="TopOffset") String TopOffset,
                                          @RequestParam(name="PixelName") String PixelName,
                                            @RequestParam(name="PixelNameError") String PixelNameError)
    {
       deviceService.updateDeviceBindingPixelInformation(deviceID,LeftOffset,TopOffset,PixelName,PixelNameError,InterfaceName);
       return "true";
    }

    //接收并修改已绑定图元的资源的图元位置信息
    @GetMapping(value = "/InterfaceManager/ChangePixelLocation")
    @ResponseBody
    public String ChangePixelLocation(@RequestParam (name="deviceID")Integer deviceID,
                                      @RequestParam(name="TopOffset")String TopOffset,
                                      @RequestParam(name = "LeftOffset")String LeftOffset)
    {
         deviceService.updateDeviceBindingPixelInformation(deviceID,LeftOffset,TopOffset,null,null,null);
         return "true";

    }

    //接收并重新指定新的图元
    @PostMapping(value = "/InterfaceManager/ChangePixel")
    @ResponseBody
    public String ReassignPixelName(@RequestParam(name="deviceID")Integer deviceID,
                                    @RequestParam(name="PixelName")String PixelName,
                                    @RequestParam(name = "state")Integer state)
    {
        if (state==1)
            deviceService.updateDeviceBindingPixelInformation(deviceID,null,null,PixelName,null,null);
        else
            deviceService.updateDeviceBindingPixelInformation(deviceID,null,null,null,PixelName,null);
        return "true";
    }

    //删除normal/error状态下的某个设备绑定的图元
    @PostMapping(value = "/InterfaceManager/DeletePixel")
    @ResponseBody
    public String DeleteBindedPixel(@RequestParam(name = "deviceID")Integer deviceID)
    {
        deviceService.updateDeviceDeletePixelInformation(deviceID);
        return "true";
    }


    @PostMapping(value = "/Transfer3Dresource")
    @ResponseBody
    public String Transfer3Dresource(@RequestParam(value = "THreeDResourceName")String ThreeDResourceName)
    {
       Resource3D resource =  resourceService.listResourceByName(ThreeDResourceName);
       return JSON.toJSONString(resource);

    }



}
