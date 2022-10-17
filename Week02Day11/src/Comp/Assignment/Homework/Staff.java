package Comp.Assignment.Homework;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.Scanner;

public class Staff extends Worker{

    /** This is initialized variable **/

    //global variabel
    int tunjMakan = 220000;
    int tunjTransport = 440000;
    int totalGaji;
    Scanner input = new Scanner(System.in);

    //connect to config2.properties Start
    CrunchifyGetPropertyValues config2 = new CrunchifyGetPropertyValues();
    String ip = "";
    int port = 0;
    String user = "";
    String pass = "";
    String namadb = "";

    //Read File Karyawan.txt Start
    String dataFromFile = "";
    String [] parsedKalimat = null;
    String [] parsedKata = null;

    //connection database
    Connection con = null;
    Statement stmt = null;
    BufferedReader br = null;
    ResultSet rs = null;
    String query = "";

    //save value  in variabel
    int id = 0;
    String nama = "";
    int gaji_pokok = 0 ;
    int jml_absensi = 0;
    int jml_tidak_masuk = 0;
    String status = "";

    /**This is Method **/

    public  void HitungTunjanganMakan(){
        tunjMakan = tunjMakan - ((tunjMakan/22)*jmlCuti);
    }
    public void HitungTunjanganTransport(){
        tunjTransport = tunjTransport - ((tunjTransport/22)*jmlCuti);
    }
    public void HitungTotalGaji(){
        totalGaji = gajiPokok + (tunjMakan+tunjTransport);
    }
    @Override
    void HitungGajiPokok() {
        gajiPokok = (gajiPokok/22)*jmlAbsensi;
    }


    //First Step !
    public void connectToConfig() throws IOException {
        System.out.println("===============================");
        ip = config2.getPropValues("IP");
        port = Integer.parseInt(config2.getPropValues("PORT"));
        user = config2.getPropValues("USER");
        pass = config2.getPropValues("PASS");
        namadb = config2.getPropValues("NAMADB");
        System.out.println("===============================");
        System.out.println("-----------------ALERT----------------");
        System.out.println("--> connected to config2.propeties <--");
    }

    public void connectDatabase () throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");

        con = DriverManager.getConnection("jdbc:mysql://"+ip+":"+port+"/"+namadb+"",""+user+"",""+pass+"");
        System.out.println("-----------------ALERT----------------");
        System.out.println("connecting database........");

        con.setAutoCommit(false);
        stmt = con.createStatement();
        br = new BufferedReader(new InputStreamReader(System.in));
    }

    //Case 1
    public void sentToDatabase(){
        try {
            //insert data
            query ="insert into data_staff values(?,?,?,?,?,?)";

            /**
             *     ID int auto_increment not null,
             *     Nama varchar(50),
             *     GajiPokok bigint,
             *     JmlAbsensi int,
             *     JmlTidakMasuk int,
             *     Status varchar(50),
             * **/

            PreparedStatement psInsert = (PreparedStatement) con.prepareStatement(query);

            // file reader
            FileReader fr=new FileReader("C:\\tmp\\Karyawan.txt");
            BufferedReader br=new BufferedReader(fr);

            int i;
            while((i=br.read())!=-1){
                dataFromFile = dataFromFile + (char) i;
            }

            parsedKalimat = dataFromFile.trim().split("\n");

            //looping array parsedS
            for (int index = 1; index < parsedKalimat.length; index++ ){

                parsedKata = parsedKalimat[index].split(",");

                //insert ke tabel data_staff
                id = Integer.parseInt(parsedKata[0]);
                psInsert.setInt(1,id);

                nama = parsedKata[1];
                psInsert.setString(2,nama);

                gaji_pokok = Integer.parseInt(parsedKata[2]);
                psInsert.setInt(3,gaji_pokok);

                jml_absensi = Integer.parseInt(parsedKata[3]);
                psInsert.setInt(4,jml_absensi);

                jml_tidak_masuk = Integer.parseInt(parsedKata[4]);
                psInsert.setInt(5,jml_tidak_masuk);

                status = parsedKata[5];
                psInsert.setString(6,status);

                psInsert.executeUpdate();
                con.commit();
                System.out.println("Data Dikirim ke Database !");

                /** =======> NOTICE COMMIT ! <======
                 System.out.print("Choice (\"commit\"/\"rollback\")");
                 String answer = br.readLine();

                 if (answer.equals("commit")) {
                 con.commit();
                 System.out.println("Record Add !!");
                 }
                 if (answer.equals("rollback")) {
                 con.rollback();
                 System.out.println("Record Cancel !!");
                 }
                 System.out.println("Want to add more records ? y/n");
                 String ans = br.readLine();

                 if (ans.equals("n")) {
                 System.out.println("");
                 break;
                 }
                 **/
            }

            br.close();
            fr.close();

        }catch (Exception e){System.out.println(e);}
    }

    //Case 2
    public void editDataStatus(){
        try {
            System.out.println("// Edit Status //");
            System.out.print("Masukan id : ");
            id = input.nextInt();

            System.out.println("Pilih Status :");
            System.out.println("A. Probation");
            System.out.println("B. Kontrak");
            System.out.println("C. Tetap");
            System.out.println("D. Keluar");
            System.out.print("Tuliskan dengan huruf depan Kapital : ");
            status = input.next();

            query = "update data_staff set Status = ? where ID = ?";
            PreparedStatement psUpdate = (PreparedStatement) con.prepareStatement(query);
            psUpdate.setString(1, status);
            psUpdate.setInt(2, id);


            psUpdate.executeUpdate();
            System.out.print("Choice (\"commit\"/\"rollback\")");
            String answer = br.readLine();

            if (answer.equals("commit")) {
                con.commit();
                System.out.println("Record Add !!");
            }
            if (answer.equals("rollback")) {
                con.rollback();
                System.out.println("Record Cancel !!");
            }
            con.commit();
            System.out.println("Record successfully saved");
        }catch (Exception e) {System.out.println(e);}
    }

    //Case 3
    public void editDataNama() throws SQLException, IOException {

        try {
            System.out.println("// Edit Nama//");
            System.out.print("Masukan id : ");
            id = input.nextInt();


            System.out.print("Data " + id + " Input Nama :");
            nama = input.next();

            query = "update data_staff set Nama = ? where ID = ?";
            PreparedStatement psUpdate = (PreparedStatement) con.prepareStatement(query);
            psUpdate.setString(1, nama);
            psUpdate.setInt(2, id);

            psUpdate.executeUpdate();
            System.out.print("Choice (\"commit\"/\"rollback\")");
            String answer = br.readLine();

            if (answer.equals("commit")) {
                con.commit();
                System.out.println("Record Add !!");
            }
            if (answer.equals("rollback")) {
                con.rollback();
                System.out.println("Record Cancel !!");
            }
            con.commit();
            System.out.println("Record successfully saved");
        }catch (Exception e) {System.out.println(e);}

    }

    //Case 4

    @Override
    public void Absensi() throws SQLException {
        System.out.println("// Tambah Absensi //");
        System.out.print("Masukan id : ");
        id = input.nextInt();
        super.Absensi();
        //Note : Baru sampai sini

//        update worker.data_staff set JmlAbsensi = '20' where ID = 1



    }

    //Case 5

    @Override
    public void HitungAbsensi() {
        super.HitungAbsensi();
    }

    /**This is break to App Staff**/

    public static void main (String args[]) throws IOException, SQLException, ClassNotFoundException {
        Scanner input = new Scanner(System.in);
        Staff st = new Staff();
        st.connectToConfig();
        st.connectDatabase();

        //Obj siswa call with 5 parameters
        int pil =0;
        do {
            System.out.println("---------------------------");
            System.out.println("==========> MENU <=========");
            System.out.println("1. Connect to Database \"worker\"");
            System.out.println("2. Change Status");
            System.out.println("3. Edit Data \"Nama Karyawan\"");
            System.out.println("4. Absensi Karyawan");
            System.out.println("5. Cuti Karyawan");
            System.out.println("6. Hitung Tunjangan (Status = Tetap)");
            System.out.println("7. Hitung Total Gaji (Status != Keluar)");
            System.out.println("8. Laporan oder by Status");
            System.out.println("99. ==> Exit <==");
            System.out.println("---------------------------");

            System.out.print("Input nomor : ");
            pil = input.nextInt();

            switch (pil) {
                case 1 : st.sentToDatabase();break;
                case 2 : st.editDataStatus();break;
                case 3 : st.editDataNama();break;
                case 4 : st.Absensi();break;
                case 5 : st.HitungAbsensi();break;
                case 6 : break;
                case 7 : break;
                case 8 : break;
                default: System.out.println("PILIH 1 - 8 atau 99 Untuk Keluar !!!"); break;
            }
        }while (pil != 99);
    }
}
