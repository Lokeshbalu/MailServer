package MailServer;

import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class interfaceManager  {


    userSession sess=new userSession();
    HashMap<String , String> sessionData = new HashMap<String, String>();
    String FileHandler(String fname) {
        int  ch;
        String dataRead="";
        try{

            FileReader fr= new FileReader("webresources\\"+fname);
            while ((ch=fr.read())!=-1){

                dataRead=dataRead+(char)ch;
            }

            return  dataRead;
        }catch (Exception e){

            System.out.println(e);
            return "error 404 resource unavailable";
        }

    }

    interfaceManager(HashMap<String , String> detailer){

        sessionData=detailer;


    }

    String  InterfaceFile(){

        String fileName= sessionData.get("resourceurl");
        if(fileName=="/"){
            fileName="index.html";
        }
        return handle(fileName);
    }

    String handle(String name){

    //    String [] checker= name.split(".");
        String back="";
        if(true) {
            switch (name) {
                case "/admin.html":
                            String user=sessionData.get("uname");
                            String pass=sessionData.get("upas");
                            boolean status=sess.checkSignIn(user,pass);
                            System.out.println(status);


                    System.out.println(user);
                    System.out.println(pass);
                            if(status){
                                 back=FileHandler("admin.html");
                             }
                            else{
                                back= errhandle("admin.html");
                            }
                            break;
                case "/mailPage.html":
                            ArrayList<String> mailss=new ArrayList<String>();
                            dbManager dbs=new dbManager();
                            mailManager mailmanager=new mailManager();
                            String check=sessionData.get("rollnos");
                            String check1=sessionData.get("years");
                            String check2=sessionData.get("sections");
                            String msg=sessionData.get("msg");
                            String subj=sessionData.get("subj");
                            if(check.equals("NaN"))
                            {
                                if(check2.equals("NaN")){           //all

                                    String [] deps={"_2018","_2017","_2016","_2015"};

                                    try {
                                        Connection con = dbs.getConnection("cseDepartment");
                                        System.out.println("this is : " + con);
                                        for(String data:deps) {
                                            dbs.select(con, mailss, "Email", data+";");
                                        }
                                        mailmanager.sendMail(mailss,subj,msg);

                                    }catch (Exception e){


                                    }
                                }
                                else{           //Customised mails
                                    String [] deps=check1.split(",");
                                    String [] secs=check2.split(",");
                                    try {
                                        Connection con = dbs.getConnection("cseDepartment");
                                        System.out.println("this is : " + con);
                                        for(String data:deps) {
                                            for (String data1 : secs) {
                                                dbs.select(con, mailss, "Email", data+" where Section like \"" + data1 + "\";");
                                            }
                                        }
                                        for(String das:mailss){

                                            System.out.println(das);
                                        }
                                        mailmanager.sendMail(mailss,subj,msg);

                                    }catch (Exception e){

                                        System.out.println(e);

                                    }

                                }
                            }
                            else                        //single student
                            {
                                try {
                                    Connection con = dbs.getConnection("cseDepartment");
                                    System.out.println("this is : " + con);

                                    dbs.select(con, mailss, "Email", "_2018 where RollNo like \"" + check + "\";");
                                    for(String things:mailss){
                                        System.out.println(things);
                                    }
                                    mailmanager.sendMail(mailss,subj,msg);

                                }catch (Exception e){

                                    System.out.println(e);
                                }

                            }
                            back="<!DOCTYPE html><html><head><title>adorein Email</title></head><body><div style=\"border-style: groove;\"><div style=\"background-color: #666666;height: 3.5em;width: 100%;top: 0;\"><h1 style=\"color: #ffffff;padding-left: 2em;\">adorein</h1></div><div ><h1 style=\"color: #66cc33;\">test <q>test</q>is confirmed. </h1><p>test.</p></div></div></body></html>";

                            break;
                case "/dataupdate.html":
                    break;
                default:
                    name.replace("/","");
                    FileHandler(name);
                    break;
            }
        }
        return back;
    }
    String errhandle(String name){

        String retu="";

        switch (name) {
            case "admin.html":

                break;
            case "Mailsend.html":

                break;
            case "dataupdate.html":

                break;
            default:

                break;
        }

        return "<h1>Something Fishy</h1>";

    }

}
