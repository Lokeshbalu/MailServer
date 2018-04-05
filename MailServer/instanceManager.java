package MailServer;

import java.io.IOException;
import java.io.OutputStream;
import java.io.InputStream;
import java.net.Socket;
import java.util.HashMap;
/*
 *      .................................Need For InstanceManager.........................................
  *  The InstanceManager class takes care of the instance to the server at any time.
  *  It connects the instance to the correct interface. Hence providing multiple platform compatability.
  *  This is the gate way for interfaceManager.
  *
 */
public class instanceManager extends Thread{


    public static HashMap<String , String> detailer=new HashMap<String , String>();
    private final Socket ClientSocket;
    interfaceManager driveAccess=new interfaceManager(detailer);
    public instanceManager(Socket x){
        this.ClientSocket=x;
        System.out.println(ClientSocket);
    }
    @Override
    public void run(){

        try{
            html();
        }catch (IOException e){
            System.out.println(e);
        }

    }
    void html() throws IOException {
        InputStream in=ClientSocket.getInputStream();
        String data="";
        interfaceManager interfacer= null;
        int c;
        long i=in.available();
        while(i!=0){
            c=in.read();
            char k=(char)c;
            i--;
            data=data+k;
        }
        data=data.toString();
        System.out.println(data);
        String [] indu=data.split("\r\n\r\n",2);
        headerParser(indu[0]);  //parses header data from client
        BodyParser(indu[1]);    //parses body data from client
        String godata;
        interfacer=new interfaceManager(detailer);
        godata=interfacer.InterfaceFile();
        String outdata="HTTP/1.1 200 OK\r\n\r\n"+godata;
        System.out.println(outdata);
        OutputStream out=ClientSocket.getOutputStream();
        out.write(outdata.getBytes());
        ClientSocket.close();
    }
    public static void headerParser(String data)
    {
        int i=1;
        String [] deepu = data.split("\r\n");
        for(String inner:deepu)
        {
            if(i==1){
                String [] lineone=inner.split(" ");
                detailer.put("Protocol",lineone[0]);
                detailer.put("resourceurl",lineone[1]);
                detailer.put("status",lineone[2]);
                i=i+1;
                continue;
            }
            String [] getit=inner.split(":");

            detailer.put(getit[0],getit[1]);

        }
        String [] bounds=((detailer.get("Content-Type")).split(";"));
        String [] bound=(bounds[1].split("="));
        detailer.put("Boundry",bound[1]);
        System.out.println(detailer);

    }
    public static void BodyParser(String data)
    {
        int i=1;

        String [] body1=data.split("--"+detailer.get("Boundry")+"--\r\n");
        System.out.println(body1[0]);
        String [] body = body1[0].split("--"+detailer.get("Boundry")+"\r\n");
        for(String x:body)
        {
            if(i==1){
                i=i+1;
                continue;
            }
            String [] gobypart=x.split("\r\n\r\n",2);
            String [] formhead=gobypart[0].split(";");
            String [] nameSearch=formhead[1].split("=");
            detailer.put(nameSearch[1].replace("\"",""),gobypart[1].replace("\r\n",""));
        }
        System.out.println(detailer);

    }


}

