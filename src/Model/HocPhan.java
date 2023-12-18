package Model;

public class HocPhan {
    private String maHP;
    private String tenHP;
    private int soTinChi;
    private int soTiet;

    public HocPhan(String maHP, String tenHP, int soTinChi, int soTiet) {
        this.maHP = maHP;
        this.tenHP = tenHP;
        this.soTinChi = soTinChi;
        this.soTiet = soTiet;
    }

    public String getMaHP() {
        return maHP;
    }

    public void setMaHP(String maHP) {
        this.maHP = maHP;
    }

    public String getTenHP() {
        return tenHP;
    }

    public void setTenHP(String tenHP) {
        this.tenHP = tenHP;
    }

    public int getSoTinChi() {
        return soTinChi;
    }

    public void setSoTinChi(int soTinChi) {
        this.soTinChi = soTinChi;
    }

    public int getSoTiet() {
        return soTiet;
    }

    public void setSoTiet(int soTiet) {
        this.soTiet = soTiet;
    }

    @Override
    public String toString() {
        return "HocPhan{" +
                "maHP='" + maHP + '\'' +
                ", tenHP='" + tenHP + '\'' +
                ", soTinChi=" + soTinChi +
                ", soTiet=" + soTiet +
                '}';
    }
}
