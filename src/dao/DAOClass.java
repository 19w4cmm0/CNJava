package dao;

import Model.Lop;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DAOClass {

    private static Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/quanlydiemsinhvien", "root", "");
        return conn;
    }

    public List<Lop> getAllClasses() throws SQLException {
        Connection cnn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Lop> classList = new ArrayList<>();

        try {
            cnn = this.getConnection();
            String sql = "SELECT * FROM lop";
            ps = cnn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                classList.add(new Lop(
                        rs.getString("maLop"),
                        rs.getString("tenLop"),
                        rs.getString("gVCN"),
                        rs.getString("sDTGV"),
                        rs.getString("maNganh"),
                        rs.getString("maKhoa"),
                        rs.getString("tenNganh"),
                        rs.getString("tenKhoa")
                ));
            }

            return classList;
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

    public int insertClass(Lop lop) throws SQLException {
        Connection cnn = null;
        PreparedStatement ps = null;
        int row = 0;

        try {
            cnn = this.getConnection();
            ps = cnn.prepareStatement("INSERT INTO lop (maLop, tenLop, gVCN, sDTGV, maNganh, maKhoa, tenNganh, tenKhoa) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            ps.setString(1, lop.getMaLop());
            ps.setString(2, lop.getTenLop());
            ps.setString(3, lop.getGVCN());
            ps.setString(4, lop.getSDTGV());
            ps.setString(5, lop.getMaNganh());
            ps.setString(6, lop.getMaKhoa());
            ps.setString(7, lop.getTenNganh());
            ps.setString(8, lop.getTenKhoa());

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

    public int updateClass(Lop lop) throws SQLException {
        Connection cnn = null;
        PreparedStatement ps = null;
        int row = 0;

        try {
            cnn = this.getConnection();
            ps = cnn.prepareStatement("UPDATE lop SET tenLop=?, gVCN=?, sDTGV=?, maNganh=?, maKhoa=?, tenNganh=?, tenKhoa=? WHERE maLop=?");

            ps.setString(1, lop.getTenLop());
            ps.setString(2, lop.getGVCN());
            ps.setString(3, lop.getSDTGV());
            ps.setString(4, lop.getMaNganh());
            ps.setString(5, lop.getMaKhoa());
            ps.setString(6, lop.getTenNganh());
            ps.setString(7, lop.getTenKhoa());
            ps.setString(8, lop.getMaLop());

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

    public int deleteClass(String maLop) throws SQLException {
        Connection cnn = null;
        PreparedStatement ps = null;
        int row = 0;

        try {
            cnn = this.getConnection();
            ps = cnn.prepareStatement("DELETE FROM lop WHERE maLop=?");
            ps.setString(1, maLop);

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
