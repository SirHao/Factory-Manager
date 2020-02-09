package com.smartwater.demo.DAO;


import com.smartwater.demo.domain.Resource3D;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;


@Mapper
public interface ResourceMapper
{
    void insert3DResource(String name,Integer type,Integer width, Integer height, String comment);

    void delete3DResource(String name);

    ArrayList<Resource3D> selectAllResource();

    ArrayList<Resource3D> selectAllInterfaceResource();

    ArrayList<Resource3D> selectAllPixelResource();

    Resource3D selectResource3DByName(String name);
}
