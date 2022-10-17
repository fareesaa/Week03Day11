package Comp.Assignment.Homework;

import java.sql.SQLException;

abstract  class  Worker {
    int idKaryawan;
    String nama;
    int gajiPokok;
    int jmlAbsensi;
    int jmlCuti;

    public void Absensi() throws SQLException {
        this.jmlAbsensi = jmlAbsensi + 1;
    }
    public void HitungAbsensi(){
        jmlAbsensi = jmlAbsensi - jmlCuti;
    }
    abstract void HitungGajiPokok();

}
