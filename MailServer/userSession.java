package MailServer;

import java.sql.Connection;
import java.util.ArrayList;

public class userSession {

    int fcount=0;
    dbManager dbs=new dbManager();
    boolean checkSignIn(String username,String password)
    {
        String pass="";
        ArrayList<String> datas=new ArrayList<String>();
        try {
            Connection con = dbs.getConnection("permissions");
            System.out.println("this is : "+con);

            dbs.select(con,datas,"pass","permission where users like \""+username+"\";");
            for(String thing:datas){
                pass=thing;
            }
            if(pass.equals(password)){
                return  true;
            }
            else
            {
                return false;
            }

        }catch (Exception e){

            return false;
        }


    }
    void add(String username,String password,String cookie)
    {



    }
    void block(String username)
    {

    }
    void updateUser(String Cookie,String service)
    {

    }

}


