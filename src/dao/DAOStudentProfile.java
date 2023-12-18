package dao;

import Model.SinhVien;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DAOStudentProfile {

    private static Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/quanlydiemsinhvien", "root", "");
        return conn;
    }

    public List<SinhVien> getAllStudents() throws SQLException {
        Connection cnn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<SinhVien> studentList = new ArrayList<>();

        try {
            cnn = this.getConnection();
            String sql = "SELECT * FROM sinhvien";
            ps = cnn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                SinhVien student = new SinhVien(
                        rs.getString("maSV"),
                        rs.getString("maLop"),
                        rs.getString("tenSV"),
                        rs.getString("gioiTinh"),
                        rs.getString("ngaySinh"),
                        rs.getString("queQuan"),
                        rs.getString("sDTSV"),
                        rs.getString("heDaoTao"),
                        rs.getString("bacDaoTao")
                );
                studentList.add(student);
            }

            return studentList;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ps != null)
                ps.close();
            if (cnn != null)
                cnn.close();
        }

        return null;
    }

    public int insertStudent(SinhVien sinhVien) throws SQLException {
        Connection cnn = null;
        PreparedStatement ps = null;
        int row = 0;

        try {
            cnn = this.getConnection();
            ps = cnn.prepareStatement("INSERT INTO sinhvien VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
            ps.setString(1, sinhVien.getMaSV());
            ps.setString(2, sinhVien.getMaLop());
            ps.setString(3, sinhVien.getTenSV());
            ps.setString(4, sinhVien.getGioiTinh());
            ps.setString(5, sinhVien.getNgaySinh());
            ps.setString(6, sinhVien.getQueQuan());
            ps.setString(7, sinhVien.getsDTSV());
            ps.setString(8, sinhVien.getHeDaoTao());
            ps.setString(9, sinhVien.getBacDaoTao());

            row = ps.executeUpdate();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            if (ps != null)
                ps.close();
            if (cnn != null)
                cnn.close();
        }

        return row;
    }

    public int updateStudent(SinhVien sinhVien) throws SQLException {
        Connection cnn = null;
        PreparedStatement ps = null;
        int row = 0;

        try {
            cnn = this.getConnection();
            ps = cnn.prepareStatement("UPDATE sinhvien SET maLop=?, tenSV=?, gioiTinh=?, ngaySinh=?, queQuan=?, sDTSV=?, heDaoTao=?, bacDaoTao=? WHERE maSV=?");

            ps.setString(1, sinhVien.getMaLop());
            ps.setString(2, sinhVien.getTenSV());
            ps.setString(3, sinhVien.getGioiTinh());
            ps.setString(4, sinhVien.getNgaySinh());
            ps.setString(5, sinhVien.getQueQuan());
            ps.setString(6, sinhVien.getsDTSV());
            ps.setString(7, sinhVien.getHeDaoTao());
            ps.setString(8, sinhVien.getBacDaoTao());
            ps.setString(9, sinhVien.getMaSV());

            row = ps.executeUpdate();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            if (ps != null)
                ps.close();
            if (cnn != null)
                cnn.close();
        }

        return row;
    }

    public int deleteStudent(String maSV) throws SQLException {
        Connection cnn = null;
        PreparedStatement ps = null;
        int row = 0;

        try {
            cnn = this.getConnection();
            ps = cnn.prepareStatement("DELETE FROM sinhvien WHERE maSV=?");
            ps.setString(1, maSV);

            row = ps.executeUpdate();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            if (ps != null)
                ps.close();
            if (cnn != null)
                cnn.close();
        }

        return row;
    }
}

