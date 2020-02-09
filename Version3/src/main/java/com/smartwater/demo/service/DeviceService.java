package com.smartwater.demo.service;

import com.smartwater.demo.DAO.DeviceMapper;
import com.smartwater.demo.domain.Device;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class DeviceService
{
    @Autowired
    DeviceMapper deviceMapper;

    public ArrayList<Device> listAllFactory()
    {
        return deviceMapper.selectAllFactory();

    }

    public ArrayList<Device> listAllSubDevice(Integer id)
    {
        return deviceMapper.selectAllSubDevice(id);
    }
    public ArrayList<Device> listAllWorkshop()
    {
        return deviceMapper.selectAllWorkshop();
    }

    public void insertNewFactory(String name)
    {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = df.format(new Date());
          deviceMapper.insertFactory(name,time);
    }
    public void insertNewWorkshopOrFacility(String name,int parent_id, int brother_id,String function_id,int type)
    {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = df.format(new Date());
        Integer insertedID = 0;
        if (brother_id==-1)
        {
            Device device11 = deviceMapper.selectFirstDevice(parent_id);
            if (type==2)
                deviceMapper.insertWorkshop(parent_id,name,brother_id,function_id,time);
            else
                deviceMapper.insertFacility(parent_id,name,brother_id,function_id,time);
            for (Device devicese:deviceMapper.selectByBroAndParID(-1,parent_id))
            {
                if (devicese.getId()!=device11.getId())
                    insertedID = devicese.getId();
            }
            deviceMapper.updateBrother(device11.getId(),insertedID);
        }
        else if(deviceMapper.selectNextDevice(brother_id)==null)
        {
            if (type==2)
                deviceMapper.insertWorkshop(parent_id,name,brother_id,function_id,time);
            else
                deviceMapper.insertFacility(parent_id,name,brother_id,function_id,time);
        }
        else
        {
            Device device11 = deviceMapper.selectByBrotherID(brother_id);
            if (type==2)
                deviceMapper.insertWorkshop(parent_id,name,brother_id,function_id,time);
            else
                deviceMapper.insertFacility(parent_id,name,brother_id,function_id,time);
            for (Device devicese:deviceMapper.selectByBrotherID2(brother_id))
            {
                if (devicese.getId()!=device11.getId())
                    insertedID = devicese.getId();
            }
            deviceMapper.updateBrother(device11.getId(),insertedID);
        }

    }

    public Device listDeviceByID(Integer id)
    {
        return deviceMapper.selectDeviceByID(id);
    }
    public ArrayList<Device> SoryByBrotherOrder(ArrayList<Device> deviceList)
    {
          int brother = 0;
          ArrayList<Device> temp = new ArrayList<>();
          for (Device device :deviceList)
          {
              if(device.getBrother_id()==-1)
              {
                  temp.add(device);
                  brother = device.getId();
                  break;
              }

          }
          for (int i=1;i<deviceList.size();i++)
          {
              for (int j=0;j<deviceList.size();j++)
              {
                  if (deviceList.get(j).getBrother_id()==brother)
                  {
                      temp.add(deviceList.get(j));
                      brother = deviceList.get(j).getId();
                      break;
                  }
              }

          }
          return temp;

    }
  public  ArrayList<Device> listAllDevice()
  {
       return deviceMapper.selectAllDevice();
  }

  public ArrayList<Device> listAllDeviceWithoutResource()
  {
      return deviceMapper.selectAllDeviceNotBindResource();
  }

  public void updateDeviceBindingPixelInformation(int id,String LeftOffset, String TopOffset, String PixelName,String PixelNameError, String InterfaceName)
  {
      Device device =deviceMapper.selectDeviceByID(id);

      if (PixelName==null)
      {
          PixelName = device.getImage_name();
      }
      if (PixelNameError==null)
      {
          PixelNameError = device.getImage_name_error();
      }
      if (InterfaceName==null)
      {
          InterfaceName = device.getBackground_name();
      }
      if (LeftOffset==null)
      {
          LeftOffset = device.getLeftu();
      }
      if (TopOffset==null)
      {
          TopOffset = device.getTop();
      }
      deviceMapper.updateBindingPixel(id,LeftOffset,TopOffset,PixelName,PixelNameError,InterfaceName);
  }
  public void updateDeviceDeletePixelInformation(int id)
  {
      deviceMapper.updateBindingPixel(id,"","","","","");
  }
  public  ArrayList<Device> listAllDeviceByBackgroundName(String background_name)
  {
     return  deviceMapper.selectAllDeviceByBackgroundName(background_name);
  }

    public void deleteDevice(Device device){
        deviceMapper.deleteDeviceById(device.getId());
    }
    public void deleteRoom(Device device){
        List<Device> devices=deviceMapper.selectAllSubDevice(device.getId());
        for(Device subDeviceDelete:devices){
            deviceMapper.deleteDeviceById(subDeviceDelete.getId());
        }
        deviceMapper.deleteDeviceById(device.getId());
    }
    public void deleteFactory(Device device){
        List<Device> rooms=deviceMapper.selectAllSubDevice(device.getId());
        for(Device subRoomDelete:rooms){
            deleteRoom(subRoomDelete);
        }
        deleteDevice(device);
    }



}
