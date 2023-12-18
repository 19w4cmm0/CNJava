package Model;

public class KetQuaHocTap {
    private String maSV;
    private String maHP;
    private String hocKy;
    private float diemQT;
    private float diemGK;
    private float diemTH;
    private float diemCK;
    private float diemHP;
    private float diemThiLai;
    private float diemTB;

    // Constructors
    public KetQuaHocTap(String maSV, String maHP, String hocKy, float diemQT, float diemGK,
                        float diemTH, float diemCK, float diemHP, float diemThiLai, float diemTB) {
        this.maSV = maSV;
        this.maHP = maHP;
        this.hocKy = hocKy;
        this.diemQT = diemQT;
        this.diemGK = diemGK;
        this.diemTH = diemTH;
        this.diemCK = diemCK;
        this.diemHP = diemHP;
        this.diemThiLai = diemThiLai;
        this.diemTB = diemTB;
    }

    public String getMaSV() {
        return maSV;
    }

    public void setMaSV(String maSV) {
        this.maSV = maSV;
    }

    public String getMaHP() {
        return maHP;
    }

    public void setMaHP(String maHP) {
        this.maHP = maHP;
    }

    public String getHocKy() {
        return hocKy;
    }

    public void setHocKy(String hocKy) {
        this.hocKy = hocKy;
    }

    public float getDiemQT() {
        return diemQT;
    }

    public void setDiemQT(float diemQT) {
        this.diemQT = diemQT;
    }

    public float getDiemGK() {
        return diemGK;
    }

    public void setDiemGK(float diemGK) {
        this.diemGK = diemGK;
    }

    public float getDiemTH() {
        return diemTH;
    }

    public void setDiemTH(float diemTH) {
        this.diemTH = diemTH;
    }

    public float getDiemCK() {
        return diemCK;
    }

    public void setDiemCK(float diemCK) {
        this.diemCK = diemCK;
    }

    public float getDiemHP() {
        return diemHP;
    }

    public void setDiemHP(float diemHP) {
        this.diemHP = diemHP;
    }

    public float getDiemThiLai() {
        return diemThiLai;
    }

    public void setDiemThiLai(float diemThiLai) {
        this.diemThiLai = diemThiLai;
    }

    public float getDiemTB() {
        return diemTB;
    }

    public void setDiemTB(float diemTB) {
        this.diemTB = diemTB;
    }

    @Override
public String toString() {
    return "KetQuaHocTap{" +
            "maSV='" + maSV + '\'' +
            ", maHP='" + maHP + '\'' +
            ", hocKy='" + hocKy + '\'' +
            ", diemQT=" + diemQT +
            ", diemGK=" + diemGK +
            ", diemTH=" + diemTH +
            ", diemCK=" + diemCK +
            ", diemHP=" + diemHP +
            ", diemThiLai=" + diemThiLai +
            ", diemTB=" + diemTB +
            '}';
}

}

