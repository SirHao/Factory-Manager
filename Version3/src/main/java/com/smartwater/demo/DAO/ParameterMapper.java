package com.smartwater.demo.DAO;

import com.smartwater.demo.domain.Parameter;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface ParameterMapper
{
    Parameter mapperPara(int type);

    ArrayList<Parameter> mapperAllPara();
}
