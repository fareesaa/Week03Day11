package Comp.Assignment;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class MyServer {
    long objNilai1;
    long objNilai2;
    long objNilai3;

    class MultiThread1 extends Thread{
        Map m = new HashMap();
        public void run() {
            try{
                FileReader fr= new FileReader("C:\\Users\\BTPNSSHIFTED\\Task\\subtask\\Week02Day11\\file\\User.txt");
                int i;
                String strIsi="";
                while ((i=fr.read())!=-1){
                    strIsi = strIsi+(char)i;
                }
                


            }catch(Exception e){
                System.out.println(e);
            }
        }
    }
    class MultiThread2 extends Thread{
        public void run() {
            try{


            }catch(Exception e){
                System.out.println(e);
            }
        }
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        MyServer myServer = new MyServer();

        Class.forName("com.mysql.cj.jdbc.Driver");
        // 2. Creating Connection
        Connection con= DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/sonoo","root","mecalister07");
        Statement stmt=con.createStatement();
        try{
            Scanner input = new Scanner(System.in);
            ServerSocket ss=new ServerSocket(6666);
            Socket s=ss.accept();//establishes connection

            DataInputStream dis=new DataInputStream(s.getInputStream());

            String msg = "";
            String username = "";
            String password = "";
            String name = "";
            long mode = 0;

            String str = (String)dis.readUTF();
            System.out.println("Message from Client = "+str);

            // Decode JSON
            JSONObject jo = new JSONObject();
            try {
                Object obj = new JSONParser().parse(str);
                jo = (JSONObject) obj;
            } catch (ParseException ex) {
                ex.printStackTrace();
            }

            mode = (Long) jo.get("mode");

            switch ((int) mode){
                case 1: // Registration
                    System.out.println("Registration");
                    username = (String) jo.get("username");
                    password = (String) jo.get("password");


                    System.out.println("Username is "+username+" and password is "+password);

                    //Read if there are headers
                    File FileExist = new File("C:\\Users\\BTPNSSHIFTED\\Task\\subtask\\Week02Day11\\file\\User.txt");
                    if (FileExist.length() == 0) {
                        System.out.println("File is empty ... Write Header");
                        FileWriter fw = new FileWriter("C:\\Users\\BTPNSSHIFTED\\Task\\subtask\\Week02Day11\\file\\User.txt", true);

                        fw.write("id,username,password\n");
                        fw.write("1" +","+username+","+password+"\n");
                        stmt.executeUpdate("insert into siswa (id, username,password, id_nilai) values ('1','"+username+"','"+password+"','1')");
                        stmt.executeUpdate("insert into nilai_siswa (id,id_siswa) values ('1','1')");
                        fw.close();
                    } else {
                        System.out.println("File is not empty ... Not Writing Header");
                        // Read the File
                        FileReader fr=new FileReader("C:\\Users\\BTPNSSHIFTED\\Task\\subtask\\Week02Day11\\file\\User.txt");
                        int i = 0;
                        String strRead = "";

                        while((i=fr.read())!=-1)
                            strRead = strRead + (char)i;

                        fr.close();

                        // Cek if it is the last ID
                        String strParsed[] = strRead.trim().split("\n");

                        int lastLine = strParsed.length;

                      /*  String strParsed2[] = strParsed[lastLine].trim().split(",");
                        int id = Integer.parseInt(strParsed2[0].trim()) + 1; // Add 1*/

                        FileWriter fw = new FileWriter("C:\\Users\\BTPNSSHIFTED\\Task\\subtask\\Week02Day11\\file\\User.txt", true);

                        fw.write(lastLine + "," + username+","+password+"\n");
                        stmt.executeUpdate("insert into siswa (id, username,password,id_nilai) values ('"+lastLine+"','"+username+"','"+password+"','"+lastLine+"')");
                        stmt.executeUpdate("insert into nilai_siswa (id,id_siswa) values ('"+lastLine+"','"+lastLine+"')");
                        fw.close();
                    }

                    break;
                case 2:

                    break;
                case 3:
                    int idUp =  input.nextInt();
                    System.out.println("Update Data");
                    username = (String) jo.get("username");
                    password = (String) jo.get("password");
                    name = (String) jo.get("name");


                    System.out.println("Username is "+username+" and password is "+password);
                    JSONArray jsonArrayNilai = (JSONArray) jo.get("nilai");
                    for(int i =0;i<jsonArrayNilai.size();i++) {
                        if(i==1){
                            myServer.objNilai1 = (long) jsonArrayNilai.get(i);
                        } else if (i==2) {
                            myServer.objNilai2 = (long) jsonArrayNilai.get(i);
                        }else if (i==3) {
                            myServer.objNilai3 = (long) jsonArrayNilai.get(i);
                        }
                    }
                    String nilai1Str = ""+myServer.objNilai1;
                    int nilai1 = Integer.parseInt(nilai1Str);
                    String nilai2Str = ""+myServer.objNilai2;
                    int nilai2 = Integer.parseInt(nilai2Str);
                    String nilai3Str = ""+myServer.objNilai3;
                    int nilai3 = Integer.parseInt(nilai3Str);
                    stmt.executeUpdate("update siswa set name = '"+name+"' where id ='"+idUp+"'");
                    stmt.executeUpdate("update nilai_siswa set fisika = '"+ myServer.objNilai1+"' where id ='"+idUp+"'");
                    PreparedStatement update = con.prepareStatement
                            ("UPDATE items SET fisika = ?, biologi = ?, kimia = ? WHERE id = ?");
                    update.setInt(1, nilai1);
                    update.setInt(2, nilai2);
                    update.setInt(3, nilai3);
                    update.executeUpdate();
                    break;
            }

            ss.close();

        }catch(Exception e){System.out.println(e);}
    }

}

