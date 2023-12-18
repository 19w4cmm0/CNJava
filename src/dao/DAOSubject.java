package dao;

import Model.HocPhan;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DAOSubject {

    private static Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/quanlydiemsinhvien", "root", "");
        return conn;
    }

    public List<HocPhan> getAllSubjects() throws SQLException {
        Connection cnn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<HocPhan> subjectList = new ArrayList<>();

        try {
            cnn = this.getConnection();
            String sql = "SELECT * FROM hocphan";
            ps = cnn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                subjectList.add(new HocPhan(
                        rs.getString("maHP"),
                        rs.getString("tenHP"),
                        rs.getInt("soTinChi"),
                        rs.getInt("soTiet")
                ));
            }

            return subjectList;
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

    public int insertSubject(HocPhan hocPhan) throws SQLException {
        Connection cnn = null;
        PreparedStatement ps = null;
        int row = 0;

        try {
            cnn = this.getConnection();
            ps = cnn.prepareStatement("INSERT INTO hocphan VALUES (?, ?, ?, ?)");
            ps.setString(1, hocPhan.getMaHP());
            ps.setString(2, hocPhan.getTenHP());
            ps.setInt(3, hocPhan.getSoTinChi());
            ps.setInt(4, hocPhan.getSoTiet());

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

    public int updateSubject(HocPhan hocPhan) throws SQLException {
        Connection cnn = null;
        PreparedStatement ps = null;
        int row = 0;

        try {
            cnn = this.getConnection();
            ps = cnn.prepareStatement("UPDATE hocphan SET tenHP=?, soTinChi=?, soTiet=? WHERE maHP=?");

            ps.setString(1, hocPhan.getTenHP());
            ps.setInt(2, hocPhan.getSoTinChi());
            ps.setInt(3, hocPhan.getSoTiet());
            ps.setString(4, hocPhan.getMaHP());

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

    public int deleteSubject(String maHP) throws SQLException {
        Connection cnn = null;
        PreparedStatement ps = null;
        int row = 0;

        try {
            cnn = this.getConnection();
            ps = cnn.prepareStatement("DELETE FROM hocphan WHERE maHP=?");
            ps.setString(1, maHP);

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
