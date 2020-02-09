package com.Cqu;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.FileReader;
import java.util.*;

import java.text.SimpleDateFormat;

public class JsonMakerImpl {
    private String xmlPath;

    public JsonMakerImpl(String xmlPath) {
        this.xmlPath = xmlPath;
    }

    public void setXmlPath(String xmlPath) {
        this.xmlPath = xmlPath;
    }

    public JsonMakerImpl() {
    }

    private int getCountsByElement(Element parameterCounts){
        String parameterCountor=parameterCounts.getStringValue();
        int parameterCountsInt=Integer.valueOf(parameterCountor);
        return parameterCountsInt;
    }

    private float getARandomParameter(Element parameterName){
        float max=Float.valueOf(parameterName.element("max").getStringValue());
        float min=Float.valueOf(parameterName.element("min").getStringValue());
        return (float) (min + ((max - min) * new Random().nextDouble()));
    }

    private int getARandomId(Element ID){
        int max=Integer.valueOf(ID.element("max").getStringValue());
        int min=Integer.valueOf(ID.element("min").getStringValue());
        Random rand = new Random();
        return (rand.nextInt(max-min)+min);
    }

    private int getDeviceCounts(Element device){
        int num=Integer.valueOf(device.element("num").getStringValue());
        return num;
    }

    public JSONArray run(){
        //创建Reader对象
        SAXReader reader = new SAXReader();
        //加载xml
        Document document = null;
        try {
            document = reader.read(new File(xmlPath));
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        //获取xml根节点
        Element rootElement = document.getRootElement();
        //创建json的总list
        JSONArray maplist=new JSONArray();
        //创建时间格式对象以获取日志时间
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        List<Element> list = rootElement.elements() ;
        //遍历设备类型：水泵，净水机
        for (Element E : list)
        {
            //找到产生的设备个数
            int deviceCount=getDeviceCounts(E);

            //为当前类型设备产生几个实际的机器
            for(int j=0;j<deviceCount;j++){
                //生成一个json对象
                Map<String, Object> map1 = new HashMap<String, Object>();

                //为当前机器生成独有的Id
                Element id=E.element("id");
                int Id=getARandomId(id);
                //将Id放入json对象
                map1.put("device_id", Id);

                //找到当前机器拥有的参数个数
                int counter;
                counter =  getCountsByElement (E.element("parameterCounts"));
                //System.out.println(E.element("deviceName").getStringValue()+j);
                //打印id与创建并打印时间对象
               // System.out.println("id: "+Id+"  "+df.format(new Date()));
                //将日期放入json对象
                map1.put("time",df.format(new Date()));

                //开始遍历当前机器所有参数
                for(int i=0;i<counter;i++){
                    int index=i+1;
                    String subParameterTagName="parameter"+String.valueOf(i+1);
                    Element subParameter=E.element(subParameterTagName);
                    int subParameterName=Integer.valueOf(subParameter.element("name").getStringValue());
                    float parameterConfig=getARandomParameter(subParameter);
                    //"parai_type";
                    String subParameterTypeName="para"+index+"_type";
                    //"parai_value"
                    String subParameterValueName="para"+index+"_value";
                    //System.out.println(subParameterName+": "+parameterConfig);
                    //将参数放入json对象
                    map1.put(subParameterValueName,parameterConfig);
                    map1.put(subParameterTypeName,subParameterName);

                }
                //System.out.println("============");
                maplist.add(map1);
            }
           // System.out.println("+++++++++++++++++++");
        }
        String listJson = JSON.toJSONString(maplist);
        System.out.println(listJson);
        return maplist;
    }




}
