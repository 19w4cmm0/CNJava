/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author Admin
 */
public class ChuyenNganh {
    private int maNganh;
    private String tenNganh;
   
   public ChuyenNganh() {
       super();
   }

    public ChuyenNganh(int maNganh, String tenNganh) {
        this.maNganh = maNganh;
        this.tenNganh = tenNganh;
    }

    public int getMaNganh() {
        return maNganh;
    }

    public void setMaNganh(int maNganh) {
        this.maNganh = maNganh;
    }

    public String getTenNganh() {
        return tenNganh;
    }

    public void setTenNganh(String tenNganh) {
        this.tenNganh = tenNganh;
    }
   
}
