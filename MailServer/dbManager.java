package MailServer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

/*
    This class takes care of all the database management tasks like
    1. Getting connected to MySQL server
    2. Accessing the data in the table

*/
public class dbManager {
    public static Connection getConnection(String tableName) throws Exception{
        try{
            String driver="com.mysql.jdbc.Driver";
            String url="jdbc:mysql://127.0.0.1:3306/"+tableName;
            String username="root";
            String password="RVLB@loke=family";
            Class.forName(driver);
            Connection conn= DriverManager.getConnection(url,username,password);
            System.out.println("Connected");
            return conn;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
    public static String post(Connection con,String command) throws Exception{

       try {
           PreparedStatement posted = con.prepareStatement(command);
           posted.executeUpdate();
       } catch (Exception e)
       {
           System.out.println(e);
           return null;
       }
       return "ok";

    }
    public static boolean select(Connection con, ArrayList<String> array,String column, String table) throws Exception{

        String command="select "+column+" from"+" "+table;
        try {
            PreparedStatement statement = con.prepareStatement(command);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                array.add(result.getString(column));
             //   System.out.println(result);

            }
        } catch (Exception e){
            System.out.println(e);
            return false;
        }
        return true;
    }
}
