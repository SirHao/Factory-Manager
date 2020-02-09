package com.Cqu;

public class Main {

    public static void main(String[] args) {
         Communication c1 =new Communication();
	 GenerateInformationThread t1 = new GenerateInformationThread("GenerateInformation",c1);
	 try{
		 NetworkConnectThread t2 = new NetworkConnectThread("NetworkConnect",6066,c1);
		 System.out.println("Starting t2");
		 t2.start();
	 }
	 catch (Exception e)
	 {
	 	System.out.println(e);
	 }

	 System.out.println("Starting t1");
	 t1.start();


    }
}
