package com.smartwater.demo.DAO;

import com.smartwater.demo.domain.Data;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface DataMapper {

    List<Data> selectAllDataByID(int device_id);
    void insertData(int device_id, String time, Integer para1_type, Integer para2_type, Integer para3_type, Integer para4_type, Integer para5_type,
                    Float para1_value, Float para2_value, Float para3_value, Float para4_value, Float para5_value);
    Data selectTheLastRecordById(int device_id);


}
