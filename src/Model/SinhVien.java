package Model;

public class SinhVien {
    private String maSV;
    private String maLop;
    private String tenSV;
    private String gioiTinh;
    private String ngaySinh;
    private String queQuan;
    private String sDTSV;
    private String heDaoTao;
    private String bacDaoTao;

    public SinhVien(String maSV, String maLop, String tenSV, String gioiTinh, String ngaySinh, String queQuan, String sDTSV, String heDaoTao, String bacDaoTao) {
        this.maSV = maSV;
        this.maLop = maLop;
        this.tenSV = tenSV;
        this.gioiTinh = gioiTinh;
        this.ngaySinh = ngaySinh;
        this.queQuan = queQuan;
        this.sDTSV = sDTSV;
        this.heDaoTao = heDaoTao;
        this.bacDaoTao = bacDaoTao;
    }

    public String getMaSV() {
        return maSV;
    }

    public void setMaSV(String maSV) {
        this.maSV = maSV;
    }

    public String getMaLop() {
        return maLop;
    }

    public void setMaLop(String maLop) {
        this.maLop = maLop;
    }

    public String getTenSV() {
        return tenSV;
    }

    public void setTenSV(String tenSV) {
        this.tenSV = tenSV;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getQueQuan() {
        return queQuan;
    }

    public void setQueQuan(String queQuan) {
        this.queQuan = queQuan;
    }

    public String getsDTSV() {
        return sDTSV;
    }

    public void setsDTSV(String sDTSV) {
        this.sDTSV = sDTSV;
    }

    public String getHeDaoTao() {
        return heDaoTao;
    }

    public void setHeDaoTao(String heDaoTao) {
        this.heDaoTao = heDaoTao;
    }

    public String getBacDaoTao() {
        return bacDaoTao;
    }

    public void setBacDaoTao(String bacDaoTao) {
        this.bacDaoTao = bacDaoTao;
    }

    @Override
    public String toString() {
        return "SinhVien{" +
                "maSV='" + maSV + '\'' +
                ", maLop='" + maLop + '\'' +
                ", tenSV='" + tenSV + '\'' +
                ", gioiTinh='" + gioiTinh + '\'' +
                ", ngaySinh='" + ngaySinh + '\'' +
                ", queQuan='" + queQuan + '\'' +
                ", sDTSV='" + sDTSV + '\'' +
                ", heDaoTao='" + heDaoTao + '\'' +
                ", bacDaoTao='" + bacDaoTao + '\'' +
                '}';
    }
}
