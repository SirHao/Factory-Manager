package com.smartwater.demo.service;

import com.smartwater.demo.DAO.ParameterMapper;
import com.smartwater.demo.domain.Data;
import com.smartwater.demo.domain.Device;
import org.omg.PortableInterceptor.INACTIVE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.plugin2.os.windows.FLASHWINFO;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Service
public class MergeDataToWorkshopService
{
    @Autowired DeviceService deviceService;
    @Autowired DataService dataService;
    @Autowired ParameterMapper parameterMapper;
    private HashMap<Integer,HashMap<Integer,ArrayList<Integer>>> inform;




    public void buildInformationFlow(int factory_id)
   {

        ArrayList<Device> workshops = deviceService.listAllSubDevice(factory_id);
        ArrayList<ArrayList<Device>> structure= new ArrayList<ArrayList<Device>>(workshops.size());
        ArrayList<String> array_function_id = new ArrayList<String>();
        HashMap<Integer,HashMap<Integer,ArrayList<Integer>>> information = new HashMap();
       for(int i=0; i<workshops.size();i++)
        {

                 structure.add(deviceService.listAllSubDevice(workshops.get(i).getId()));
                 array_function_id.add(workshops.get(i).getFunction_id());
        }
        for(int i=0; i<structure.size();i++)
        {
            String super_function_id = array_function_id.get(i);  //第i个车间的function_id混合
            if (super_function_id==null)
            {
                information.put(workshops.get(i).getId(),null);
                continue;
            }
            List<String> super_function_id_spilt = Arrays.asList(super_function_id.split(",")); //第i个车间的function_id数组

            HashMap<Integer,ArrayList<Integer>> temp = new HashMap<>();

            for (String curr_id:super_function_id_spilt)  //第i个车间的每个function_id
            {

                ArrayList<Integer> tempx = new ArrayList<>();
                for (int j=0;j<structure.get(i).size();j++)  //第i个车间第j个设备
                {
                    List<String> sub_function_id_split = Arrays.asList(structure.get(i).get(j).getFunction_id().split(","));
                    if (sub_function_id_split.contains(curr_id))
                    {
                        tempx.add(structure.get(i).get(j).getId());
                    }
                }
                temp.put(Integer.parseInt(curr_id),tempx);
            }
            information.put(workshops.get(i).getId(),temp);

        }
        inform= information;

   }

   public HashMap<Integer,HashMap<String,Float>> mergeData()
   {
       HashMap<Integer,HashMap<String,Float>> data = new HashMap<>();
       for (Integer workshop_id:inform.keySet())
       {
           HashMap<Integer,ArrayList<Integer>> temp = inform.get(workshop_id);  //设备对照表（key:设备id value:设备数据选项）
           if (temp ==null)
           {
               data.put(workshop_id,null);
               continue;
           }
           HashMap<String,Float> set = new HashMap<>();
           for (Integer function_id:temp.keySet())
           {
               ArrayList<Integer> tempx = temp.get(function_id);
               Float sum = new Float(0);
               Float value = new Float(0);
               int flag = 0;
               for (Integer facility_id:tempx)
               {

                   value =selectExactDataByTypeAndDeviceID(function_id,facility_id);
                   sum = sum+value;
                   if (isOutOfRange(value,function_id))
                   {
                       flag = 1;
                   }

               }
               set.put(parameterMapper.mapperPara(function_id).getName(),sum);
                if (flag==1)
                {
                    set.put("Status",new Float(1));
                }
                else
                {
                    set.put("Status",new Float(0));
                }

           }
           data.put(workshop_id,set);
       }
       return data;
   }

  private Float selectExactDataByTypeAndDeviceID(int para_type, int device_id)
  {
      Data data = dataService.listLatestDATAByDeviceID(device_id);
      if (data!=null)
      {
          if (data.getPara1_type()!=null&&data.getPara1_type()==para_type)
          {
              return data.getPara1_value();
          }
          else if(data.getPara2_type()!=null&&data.getPara2_type()==para_type)
          {
              return data.getPara2_value();
          }
          else if(data.getPara3_type()!=null&&data.getPara3_type()==para_type)
          {
              return data.getPara3_value();
          }
          else if(data.getPara4_type()!=null&&data.getPara4_type()==para_type)
          {
              return data.getPara4_value();
          }
          else if(data.getPara5_type()!=null&&data.getPara5_type()==para_type)
          {
              return data.getPara5_value();
          }
          else
          {
              return new Float(0);
          }
      }
      else
          return new Float(0.0);

  }
  private Boolean isOutOfRange(Float value, int para_type)
  {
      Float min = parameterMapper.mapperPara(para_type).getMin();
      Float max = parameterMapper.mapperPara(para_type).getMax();
      if (value<min||value>max)
      {
          return true;  //超出范围,异常
      }
      else
      {
          return false;  //正常
      }
  }


}
