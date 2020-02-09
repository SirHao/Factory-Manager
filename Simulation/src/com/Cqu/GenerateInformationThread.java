package com.Cqu;


public class GenerateInformationThread extends Thread
{
    private String threadName;
    private JsonMakerImpl jsonMaker;
    private Communication communication;
//    private JSONArray jsonArray;

    GenerateInformationThread(String name, Communication communication1)
    {
        threadName = name;
        System.out.println("Creating"+ threadName);

        jsonMaker = new JsonMakerImpl("src/com/Cqu/book.xml");

        communication = communication1;
    }


    public void run()
    {

        while(true) {

            try
            {
                communication.setListJson(jsonMaker.run());
                Thread.sleep(2000);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }

    }

//    public JSONArray getJsonArray() {
//        return jsonArray;
//    }
}
