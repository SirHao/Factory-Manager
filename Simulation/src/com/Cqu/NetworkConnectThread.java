package com.Cqu;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class NetworkConnectThread extends Thread
{
    private String threadName;
    private ServerSocket serverSocket;
    private Communication communication;

    NetworkConnectThread(String name,int port,Communication communication1) throws IOException
    {
        threadName = name;
        System.out.println("CreatingNetworkConnect");
        communication = communication1;
        serverSocket = new ServerSocket(port);
        serverSocket.setSoTimeout(20000);

    }

    public void run()
    {
        while(true)
       try
       {
           if (communication.getListJson()!=null)
           {
               System.out.println(communication.getListJson());
           }
           System.out.println("等待远程连接,端口号为:"+serverSocket.getLocalPort()+"....");
           Socket server = serverSocket.accept();
           DataOutputStream out = new DataOutputStream(server.getOutputStream());
           out.writeUTF(communication.getListJson());
           server.close();


       }
       catch (SocketTimeoutException e)
       {
           System.out.println("Socket timed out!");
           break;
       }
        catch(IOException e)
        {
            e.printStackTrace();
            break;
        }

       System.out.println("thread: "+threadName+" exiting.");
    }


}
