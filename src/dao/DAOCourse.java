package dao;

import Model.KhoaHoc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DAOCourse {

    private static Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/quanlydiemsinhvien", "root", "");
        return conn;
    }

    public List<KhoaHoc> getAllCourses() throws SQLException {
        Connection cnn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<KhoaHoc> courseList = new ArrayList<>();

        try {
            cnn = this.getConnection();
            String sql = "SELECT * FROM khoahoc";
            ps = cnn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                courseList.add(new KhoaHoc(rs.getString("maKhoa"), rs.getString("tenKhoa"), rs.getString("nienKhoa")));
            }

            return courseList;
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

    public int insertCourse(KhoaHoc khoaHoc) throws SQLException {
        Connection cnn = null;
        PreparedStatement ps = null;
        int row = 0;

        try {
            cnn = this.getConnection();
            ps = cnn.prepareStatement("INSERT INTO khoahoc VALUES (?, ?, ?)");
            ps.setString(1, khoaHoc.getMaKhoa());
            ps.setString(2, khoaHoc.getTenKhoa());
            ps.setString(3, khoaHoc.getNienKhoa());

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

    public int updateCourse(KhoaHoc khoaHoc) throws SQLException {
        Connection cnn = null;
        PreparedStatement ps = null;
        int row = 0;

        try {
            cnn = this.getConnection();
            ps = cnn.prepareStatement("UPDATE khoahoc SET tenKhoa=?, nienKhoa=? WHERE maKhoa=?");

            ps.setString(1, khoaHoc.getTenKhoa());
            ps.setString(2, khoaHoc.getNienKhoa());
            ps.setString(3, khoaHoc.getMaKhoa());

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

    public int deleteCourse(String maKhoa) throws SQLException {
        Connection cnn = null;
        PreparedStatement ps = null;
        int row = 0;

        try {
            cnn = this.getConnection();
            ps = cnn.prepareStatement("DELETE FROM khoahoc WHERE maKhoa=?");
            ps.setString(1, maKhoa);

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
