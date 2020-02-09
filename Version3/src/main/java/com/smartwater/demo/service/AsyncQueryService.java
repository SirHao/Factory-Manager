package com.smartwater.demo.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

@Service
public class AsyncQueryService
{
     public final static String serverName = "localhost";
     public final static int port = 6066;


     @Async
     public String QueryInformation()
     {

         try
           {
               System.out.println("尝试连接到主机: "+serverName+", 端口号:" +port);
               Socket client = new Socket(serverName,port);
               client.setSoTimeout(3000);
               InputStream inFromServer = client.getInputStream();

               DataInputStream in = new DataInputStream(inFromServer);
               //System.out.println("服务器相应:"+in.readUTF());
               String data=in.readUTF();
               client.close();
               return data;
           }
           catch (IOException e)
           {
               e.printStackTrace();
           }
           return "";
     }
}
