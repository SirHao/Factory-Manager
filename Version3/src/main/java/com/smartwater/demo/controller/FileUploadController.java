package com.smartwater.demo.controller;

import com.smartwater.demo.service.GraphService;
import com.smartwater.demo.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;

@Controller
public class FileUploadController
{
    @Autowired
    GraphService graphService;
    @Autowired
    ResourceService resourceService;
    @PostMapping("/uploadPixel")
    public String upload(ModelMap map, @RequestParam("status")String status,
                         @RequestParam("filename")String refilename, @RequestParam("file")MultipartFile file,
                         @RequestParam("PixelWidth")Integer PixelWidth, @RequestParam("PixelHeight")Integer PixelHeight){
    String msg="";
    String refilename_postfix;
    String filename = file.getOriginalFilename();
    String suffix = filename.substring(filename.lastIndexOf(".")+1);
    if (refilename.lastIndexOf(".")!=-1) {
        refilename_postfix = refilename.substring(0, refilename.lastIndexOf("."));
    }
    else
    {
        refilename_postfix = refilename;
    }

    String finalName = refilename_postfix+"_"+status+"."+suffix;
    try {
        String uploadDir = System.getProperty("user.dir")+"/src/main/resources/static/3Dresource";
        //System.out.println(uploadDir);
        //如果目录不存在，自动创建目录
        File dir = new File(uploadDir);
        if (!dir.exists()) {
            dir.mkdir();
        }
        msg=graphService.executeUpload(finalName, uploadDir, file);

    }catch (Exception e) {
        //打印错误堆栈信息
        msg="error";
        e.printStackTrace();
    }
    if (msg=="success")
    {
        resourceService.insertResource(1,finalName,PixelWidth,PixelHeight,null); // 0代表背景 1代表图元
    }
    map.put("msg",msg);
    return "redirect:UIConfig/PixelManage";
}

    @PostMapping("/uploadInterface")
    public String uploadInterface(ModelMap map, @RequestParam("InterfaceName")String InterfaceName,
                                  @RequestParam("BaseMapWidth")Integer BaseMapWidth,
                                  @RequestParam("BaseMapHeight")Integer BaseMapHeight,
                                  @RequestParam("Comment")String Comment,
                                  @RequestParam("File") MultipartFile File)
    {
       String msg;
       String originalFileName = File.getOriginalFilename();
       String suffix = originalFileName.substring(originalFileName.lastIndexOf(".")+1);
       String finalName = InterfaceName +"."+ suffix;
        try {
            String uploadDir = System.getProperty("user.dir")+"/src/main/resources/static/3Dresource";
            //System.out.println(uploadDir);
            //如果目录不存在，自动创建目录
            File dir = new File(uploadDir);
            if (!dir.exists()) {
                dir.mkdir();
            }
            msg=graphService.executeUpload(finalName, uploadDir, File);

        }catch (Exception e) {
            //打印错误堆栈信息
            msg="error";
            e.printStackTrace();
        }
        if (msg=="success")
        {
            resourceService.insertResource(0,finalName,BaseMapWidth,BaseMapHeight,Comment); // 0代表背景 1代表图元
        }
        map.put("msg",msg);
        return "redirect:UIConfig/InterfaceManager";

    }

    @PostMapping("/delete")
    public String delete(ModelMap map, @RequestParam("filenameD")String filenameD) {
        String msg="";
        try {
            String uploadDir = System.getProperty("user.dir")+"/src/main/resources/static/3Dresource";
      //      String uploadDir = "/Users/scl_89757/Desktop/3Dresource";
            //System.out.println(uploadDir);
            //如果目录不存在，自动创建目录
            File dir = new File(uploadDir);
            if (!dir.exists()) {
                dir.mkdir();
            }
            msg=graphService.executeDelete(filenameD, uploadDir);
        }catch (Exception e) {
            //打印错误堆栈信息
            msg="error";
            e.printStackTrace();
        }
        if(msg=="success") {
            System.out.println("===============================");
            resourceService.deletResource(filenameD);
            System.out.println("===============================");
        }
        map.put("msg",msg);
        return "redirect:UIConfig/PixelManage";
    }


}
