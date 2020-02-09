package com.Cqu;

import com.alibaba.fastjson.JSONArray;

public class Communication
{
    private String listJson;

    synchronized public void setListJson(JSONArray jsonArray)
    {
        listJson = jsonArray.toJSONString();
    }

    synchronized public String getListJson()
    {
        return listJson;
    }

}
