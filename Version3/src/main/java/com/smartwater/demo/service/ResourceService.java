package com.smartwater.demo.service;


import com.smartwater.demo.DAO.ResourceMapper;
import com.smartwater.demo.domain.Resource3D;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;

@Service
public class ResourceService
{
    @Autowired
    ResourceMapper resourceMapper;

    public void insertResource(Integer type, String name,Integer width, Integer height, String comment)
    {
        resourceMapper.insert3DResource(name,type,width,height,comment);
    }

    public ArrayList<Resource3D> listAllResource()
    {
        return resourceMapper.selectAllResource();
    }

    public ArrayList<Resource3D> listAllPixelResource()
    {
        return resourceMapper.selectAllPixelResource();
    }

    public ArrayList<Resource3D> listAllInterfaceResource() { return resourceMapper.selectAllInterfaceResource();}

    public void deletResource(String name)
    {
        resourceMapper.delete3DResource(name);
    }

    public Resource3D listResourceByName(String name)
    {
        return resourceMapper.selectResource3DByName(name);
    }




}
