package com.smartwater.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

@Service
public class GraphService {
    public String executeUpload(String filename, String uploadDir, MultipartFile file) {

        String fileName = filename;
        //服务器端保存的文件对象
        String path = uploadDir + "/" + fileName;
        File fatherPath = new File(uploadDir);
        File serverFile = new File(path);
        //将上传的文件写入到服务器端文件内
        try {
            if (!Arrays.asList(fatherPath.list()).contains(fileName)) {
                file.transferTo(serverFile);
                return "success";
            } else
                return "existed,rename";

        } catch (IOException e) {
            e.printStackTrace();

        }
        return "fail";
    }

    public String executeDelete(String filename, String uploadDir) {

        String fileName = filename;
        //服务器端保存的文件对象
        String path = uploadDir + "/" + fileName;
        File fatherPath = new File(uploadDir);
        File serverFile = new File(path);
        //将上传的文件写入到服务器端文件内
        if (Arrays.asList(fatherPath.list()).contains(fileName)) {
            serverFile.delete();
            //System.out.println("done");
            return "success";

        } else
            return "no such file";


    }
}
