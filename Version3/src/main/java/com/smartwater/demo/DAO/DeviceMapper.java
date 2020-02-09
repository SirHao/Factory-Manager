package com.smartwater.demo.DAO;


import com.smartwater.demo.domain.Device;
import org.apache.ibatis.annotations.Mapper;

import java.sql.Struct;
import java.util.ArrayList;
import java.util.List;

@Mapper
public interface DeviceMapper
{
    ArrayList<Device> selectAllFactory();

    ArrayList<Device> selectAllSubDevice(Integer id);

    Device selectDeviceByID(Integer id);
//
    ArrayList<Device> selectAllWorkshop();

    void insertFactory(String name,String time);

    void insertWorkshop(int parent_id,String name,int brother_id,String function_id,String time);

    void insertFacility(int parent_id, String name, int brother_id, String function_id,String time);

    Device selectByBrotherID(int brother_id);

    ArrayList<Device> selectByBrotherID2(int brother_id);

//      根据该device的type和id查找其子设备的列表

    ArrayList<Device> selectAllDevice();

    void updateBrother(int id, int brother_id);

    void updateBindingPixel(int id, String LeftOffset, String TopOffset, String PixelName, String PixelNameError, String InterfaceName);

    Device selectNextDevice(int id);

    Device selectFirstDevice(int id);

    ArrayList<Device> selectByBroAndParID(int brother_id, int parent_id);

    ArrayList<Device> selectAllDeviceNotBindResource();

    ArrayList<Device> selectAllDeviceByBackgroundName(String background_name);

    Device seleteDeviceByName(String name);

    void deleteDeviceById(Integer id);
}
