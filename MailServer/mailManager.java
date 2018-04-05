package MailServer;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.ArrayList;
import java.util.Properties;

public class mailManager {

    public static void sendMail( ArrayList<String> emails,String subject,String messageBody)
    {

        Properties props=new Properties();
        props.put("mail.smtp.host","smtp.gmail.com"); //replace smtp.gmail.com to your configured mail server
        props.put("mail.smtp.socketFactory.port","465"); //replace 465 to the port number to which your mail server is listening.
        props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth","true");
        props.put("mail.smtp.port","465");
        props.put("mail.smtp.socketFactory.fallback","false");
        Session session=Session.getDefaultInstance(props,new javax.mail.Authenticator(){

            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("lokeshbalaji68@gmail.com","RVLB@loke=family");
            }
        });
        try{

            MimeMessage message=new MimeMessage(session);
            Address address=new InternetAddress("lokeshbalaji68@gmail.com");
            message.setFrom(address);
          //  ..............................................

            String toUser="";
            int k=0,l=0;
            if((l=emails.size())>1) {
                System.out.println(l);
                for (String datas:emails){
                    k++;
                    if(k<l) {

                        toUser = toUser + datas + ",";
                    }
                    if(k==l){
                        toUser=toUser+datas;
                    }
                }
                Address[] toAddress =InternetAddress.parse(toUser);
                // Address toAddress =new InternetAddress(toUser);
                message.addRecipients(Message.RecipientType.TO,toAddress);
                System.out.println("this is what i send:"+ toUser);
                message.setSubject(subject);
                MimeBodyPart messageBodyPart=new MimeBodyPart();
                messageBodyPart.setContent(messageBody,"text/html");
                Multipart multipart=new MimeMultipart();
                multipart.addBodyPart(messageBodyPart);
                message.setContent(multipart);
                Transport transport=session.getTransport("smtp");
                transport.connect();
                transport.send(message);
                //  transport.sendMessage(message,message.getAllRecipients());
                transport.close();
                System.out.println("message sent");
            }
            else{
                for (String datas:emails) {

                    toUser = toUser + datas;

                }
                Address toAddress =new InternetAddress(toUser);
                message.addRecipient(Message.RecipientType.TO,toAddress);
                message.setSubject(subject);
                message.setText(messageBody);
                Transport.send(message);
            }



        }catch (Exception e){
            System.out.println("problem again");
            e.printStackTrace();
        }
    }



}
