package dao;

import Model.KetQuaHocTap;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DAOSearchGrade {
    private static Connection getConnection() throws ClassNotFoundException, SQLException {
         Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/quanlydiemsinhvien", "root", "");
    }

    public List<KetQuaHocTap> searchGrades(String maSV) throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<KetQuaHocTap> gradeList = new ArrayList<>();

        try {
            connection = getConnection(); // Kết nối cơ sở dữ liệu
            String sql = "SELECT * FROM ketquahoctap WHERE maSV LIKE ?";
            ps = connection.prepareStatement(sql);
            ps.setString(1, "%" + maSV + "%");
            rs = ps.executeQuery();

            while (rs.next()) {
                KetQuaHocTap grade = new KetQuaHocTap(
                        rs.getString("maSV"),
                        rs.getString("maHP"),
                        rs.getString("hocKy"),
                        rs.getFloat("diemQT"),
                        rs.getFloat("diemGK"),
                        rs.getFloat("diemTH"),
                        rs.getFloat("diemCK"),
                        rs.getFloat("diemHP"),
                        rs.getFloat("diemThiLai"),
                        rs.getFloat("diemTB")
                );
                gradeList.add(grade);
            }

            return gradeList;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection(connection, ps, rs);
        }

        return null;
    }

    private static void closeConnection(Connection conn, PreparedStatement ps, ResultSet rs) {
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