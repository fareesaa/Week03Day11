package Comp.Assignment;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.regex.Pattern;

public class MyClient {
    Socket sc;
    DataInputStream disc;
    DataOutputStream doutc;
    Scanner inputStr = new Scanner(System.in);
    Scanner inputL = new Scanner(System.in);
    boolean login = false;
    long objNilai1;
    long objNilai2;
    long objNilai3;

    public void loginEncode() throws IOException {
        System.out.println("Masukkan username");
        String username = "Jikala@s.com";/*inputStr.nextLine();*/
        System.out.println("Masukkan Password");
        String password = "asasa@#232aA";/*inputStr.nextLine();*/

        String regUname = "^[A-Za-z0-9+_.-]+@(.+)$";
        boolean regexUsername = Pattern.matches(regUname, username);
        String regPass = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,32}";
        boolean regexPassword = Pattern.matches(regPass, password);
        if(regexUsername==true && regexPassword==true){
            login = true;
            JSONArray jArrNilai = new JSONArray();
            long nilai1=0L;
            long nilai2=0L;
            long nilai3=0L;
            jArrNilai.add(nilai1);
            jArrNilai.add(nilai2);
            jArrNilai.add(nilai3);
            String name = "";
            JSONObject obj = new JSONObject();
            obj.put("username",username);
            obj.put("password",password);
            obj.put("name",name);
            obj.put("nilai",jArrNilai);

            System.out.println(obj);

            doutc.writeUTF(""+obj);
            doutc.flush();
            String msgS= (String) disc.readUTF();
            System.out.println(msgS);
        }else{
            login = false;
            System.out.println("Tidak sesuai ketentuan username maupun password!");
        }
        doutc.close();
    }
    public static void main(String[] args) {
        try{

            Socket s=new Socket("localhost",6666);

            //Encode JSON
            JSONObject jo = new JSONObject();
/*            jo.put("mode", 1);
            jo.put("username", "radit@gmail.com");
            jo.put("password", "R@dit123454");*/
            jo.put("mode", 1);
            jo.put("username", "radit@gmail.com");
            jo.put("password", "R@dit123454");
            String js = jo.toJSONString();

            DataOutputStream dout=new DataOutputStream(s.getOutputStream());
            dout.writeUTF(js);
            dout.flush();

            dout.close();
            s.close();
            Scanner inputStr= new Scanner(System.in);
            MyClient myClient = new MyClient();
            /*myClient.clientUp();*/
            /*int pil;
            do {
                System.out.println("1.Registrasi");
                System.out.println("2.Login");
                System.out.println("3.Update");
                pil = inputStr.nextInt();
                switch (pil){
                    case 1:
                        *//*myClient.regisEncode();*//*
                        break;
                    case 2:
                        myClient.loginEncode();
                        break;
                    case 3:
                        *//*myClient.updateEncode();*//*
                        break;
                    case 4:
                        break;
                    case 5:
                        break;
                }
            }while (pil>5);*/
        }catch(Exception e){System.out.println(e);}
    }
}

