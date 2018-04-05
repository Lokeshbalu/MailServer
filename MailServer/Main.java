package MailServer;

import java.io.*;
import java.net.*;
import java.sql.Connection;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws Exception {

    /*   dbManager dbs=new dbManager();
        Connection con=dbs.getConnection("cseDepartment");
        ArrayList<String> array=new ArrayList<String>();
        boolean check=dbs.select(con,array,"Email","_2018 where RollNo like \"14751A04B2\"");
        System.out.println(check);
        for(String datain:array){

            System.out.println(datain);

        }
        mailManager mailmanager=new mailManager();
         String sender="<!DOCTYPE html><html><head><title>adorein Email</title></head><body><div style=\"border-style: groove;\"><div style=\"background-color: #666666;height: 3.5em;width: 100%;top: 0;\"><h1 style=\"color: #ffffff;padding-left: 2em;\">adorein</h1></div><div ><h1 style=\"color: #66cc33;\">test <q>test</q>is confirmed. </h1><p>test.</p></div></div></body></html>";
       mailmanager.sendMail(array,"test",sender);
*/

        ServerSocket s=new ServerSocket(2000);
        while (true){
            Socket clientSocket=s.accept();
            instanceManager instance = new instanceManager(clientSocket);
            instance.start();

        }
        //InputStream in=ss.getInputStream();*/
    }
}
