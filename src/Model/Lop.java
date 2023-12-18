package Model;

public class Lop {

    private String maLop;
    private String tenLop;
    private String gVCN;
    private String sDTGV;
    private String maNganh;
    private String maKhoa;
    private String tenNganh;
    private String tenKhoa;

    public Lop() {
    }

    public Lop(String maLop, String tenLop, String gVCN, String sDTGV, String maNganh, String maKhoa, String tenNganh, String tenKhoa) {
        this.maLop = maLop;
        this.tenLop = tenLop;
        this.gVCN = gVCN;
        this.sDTGV = sDTGV;
        this.maNganh = maNganh;
        this.maKhoa = maKhoa;
        this.tenNganh = tenNganh;
        this.tenKhoa = tenKhoa;
    }

    public String getMaLop() {
        return maLop;
    }

    public void setMaLop(String maLop) {
        this.maLop = maLop;
    }

    public String getTenLop() {
        return tenLop;
    }

    public void setTenLop(String tenLop) {
        this.tenLop = tenLop;
    }

    public String getGVCN() {
      return gVCN;
    }

    public void setGVCN(String gVCN) {
        this.gVCN = gVCN;
    }

    public String getSDTGV() {
        return sDTGV;
    }

    public void setSDTGV(String sDTGV) {
        this.sDTGV = sDTGV;
    }

    public String getMaNganh() {
        return maNganh;
    }

    public void setMaNganh(String maNganh) {
        this.maNganh = maNganh;
    }

    public String getMaKhoa() {
        return maKhoa;
    }

    public void setMaKhoa(String maKhoa) {
        this.maKhoa = maKhoa;
    }

    public String getTenNganh() {
        return tenNganh;
    }

    public void setTenNganh(String tenNganh) {
        this.tenNganh = tenNganh;
    }

    public String getTenKhoa() {
        return tenKhoa;
    }

    public void setTenKhoa(String tenKhoa) {
        this.tenKhoa = tenKhoa;
    }

    
}
