package Comp.Example;

import com.mysql.cj.x.protobuf.MysqlxCrud;

import java.sql.*;
import java.util.Scanner;

public class MysqlCon {
    public static void main(String args[]) throws ClassNotFoundException, SQLException, SQLException {
        Scanner inputI = new Scanner(System.in);
        Scanner inputS = new Scanner(System.in);
        Class.forName("com.mysql.cj.jdbc.Driver");
        // 2. Creating Connection
        Connection con= DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/sonoo","root","mecalister07");
        //here sonoo is database name, root is username and password
        // 3. Create Statement
        Statement stmt=con.createStatement();
        int w = 1, loop = 2;
        while (w < loop) {
            System.out.println("MENU ");
            System.out.println("1. Show Data");
            System.out.println("2. Create Data");
            System.out.println("3. Update Data ");
            System.out.println("4. Delete Data ");
            System.out.println("5. EXIT ");
            System.out.println("");
            System.out.print("Input Menu : ");
            System.out.print("");
            int menu = inputI.nextInt();
            switch (menu) {
                case 1:
                    ResultSet rs = stmt.executeQuery("select * from emp");
                    System.out.print("");
                    System.out.println("== Data From Database ==");
                    while(rs.next())
                        System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3));
                    break;
                case 2:
                    System.out.print("");
                    System.out.println("== Tambah Data ==");
                    System.out.println("Input Nama : ");
                    String nama = inputS.next();
                    System.out.println("Input Umur : ");
                    int age = inputI.nextInt();
                    stmt.executeUpdate("insert into emp (name,age) values ('"+nama+"','"+age+"')");
                    break;
                case 3:
                    System.out.print("");
                    System.out.println("== Ubah Data ==");
                    System.out.println("Cari Id : ");
                    int scId = inputI.nextInt();
                    System.out.println("Ubah Umur : ");
                    int upAge = inputI.nextInt();
                    stmt.executeUpdate("update emp set age = '"+upAge+"' where id ='"+scId+"'");
                    break;
                case 4:
                    System.out.print("");
                    System.out.println("== Delete Data ==");
                    System.out.println("Cari Id : ");
                    int delId = inputI.nextInt();
                    stmt.executeUpdate("delete from emp where id ='"+delId+"'");
                    break;
                default :
                    System.out.println("== Anda telah Exit ==");
                    w= 10;
                    con.close();
                    inputI.close();
                    inputS.close();
                    break;
            }
        }
    }
}

