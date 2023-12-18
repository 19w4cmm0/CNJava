/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author Admin
 */
public class KhoaHoc {
    private String maKhoa;
    private String tenKhoa;
    private String nienKhoa;
   public KhoaHoc(){
    super();
}

    public KhoaHoc(String maKhoa, String tenKhoa, String nienKhoa) {
        this.maKhoa = maKhoa;
        this.tenKhoa = tenKhoa;
        this.nienKhoa = nienKhoa;
    }

    public String getMaKhoa() {
        return maKhoa;
    }

    public void setMaKhoa(String maKhoa) {
        this.maKhoa = maKhoa;
    }

    public String getTenKhoa() {
        return tenKhoa;
    }

    public void setTenKhoa(String tenKhoa) {
        this.tenKhoa = tenKhoa;
    }

    public String getNienKhoa() {
        return nienKhoa;
    }

    public void setNienKhoa(String nienKhoa) {
        this.nienKhoa = nienKhoa;
    }
   
}
