/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import Model.SinhVien;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DAOSearchStudent {
    private static Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/quanlydiemsinhvien", "root", "");
    }

    public List<SinhVien> searchStudents(String maSV) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<SinhVien> studentList = new ArrayList<>();

        try {
            conn = getConnection();
            String sql = "SELECT * FROM sinhvien WHERE maSV LIKE ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + maSV + "%");
            rs = ps.executeQuery();

            while (rs.next()) {
                studentList.add(new SinhVien(
                        rs.getString("maSV"),
                        rs.getString("maLop"),
                        rs.getString("tenSV"),
                        rs.getString("gioiTinh"),
                        rs.getString("ngaySinh"),
                        rs.getString("queQuan"),
                        rs.getString("sDTSV"),
                        rs.getString("heDaoTao"),
                        rs.getString("bacDaoTao")));
            }

            return studentList;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection(conn, ps, rs);
        }

        return null;
    }
    

    private void closeConnection(Connection conn, PreparedStatement ps, ResultSet rs) {
        try {
            if (rs != null)
                rs.close();
            if (ps != null)
                ps.close();
            if (conn != null)
                conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

