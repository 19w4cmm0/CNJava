package dao;

import Model.KetQuaHocTap;
import Model.SinhVien;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DAOGrade {
    private static Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/quanlydiemsinhvien", "root", "");
    }

    public List<SinhVien> getAllStudents() throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<SinhVien> studentList = new ArrayList<>();

        try {
            conn = getConnection();
            String sql = "SELECT * FROM sinhvien";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                studentList.add(new SinhVien(rs.getString("maSV"), rs.getString("maLop"), rs.getString("tenSV"),
                        rs.getString("gioiTinh"), rs.getString("ngaySinh"), rs.getString("queQuan"),
                        rs.getString("sDTSV"), rs.getString("heDaoTao"), rs.getString("bacDaoTao")));
            }

            return studentList;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection(conn, ps, rs);
        }

        return null;
    }

    public List<KetQuaHocTap> getGradesByStudent(String maSV) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<KetQuaHocTap> gradeList = new ArrayList<>();

        try {
            conn = getConnection();
            String sql = "SELECT * FROM ketquahoctap WHERE maSV=?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, maSV);
            rs = ps.executeQuery();

            while (rs.next()) {
               gradeList.add(new KetQuaHocTap(
    rs.getString("maSV"),
    rs.getString("maHP"),
    rs.getString("hocKy"),
    rs.getFloat("diemQT"),
    rs.getFloat("diemGK"),
    rs.getFloat("diemTH"),
    rs.getFloat("diemCK"),
    rs.getFloat("diemHP"),
    rs.getFloat("diemThiLai"),
    rs.getFloat("diemTB")));


            }

            return gradeList;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection(conn, ps, rs);
        }

        return null;
    }

    // Các phương thức khác (insert, update, delete) có thể được thêm vào nếu cần.

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
      public int insertGrade(KetQuaHocTap ketQuaHocTap) throws SQLException {
    Connection cnn = null;
    PreparedStatement ps = null;
    int row = 0;

    try {
        cnn = this.getConnection();
        ps = cnn.prepareStatement("INSERT INTO ketquahoctap (maSV, maHP, hocKy, diemQT, diemGK, diemTH, diemCK, diemHP, diemThiLai, diemTB) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        ps.setString(1, ketQuaHocTap.getMaSV());
        ps.setString(2, ketQuaHocTap.getMaHP());
        ps.setString(3, ketQuaHocTap.getHocKy());
        ps.setFloat(4, ketQuaHocTap.getDiemQT());
        ps.setFloat(5, ketQuaHocTap.getDiemGK());
        ps.setFloat(6, ketQuaHocTap.getDiemTH());
        ps.setFloat(7, ketQuaHocTap.getDiemCK());
        ps.setFloat(8, ketQuaHocTap.getDiemHP());
        ps.setFloat(9, ketQuaHocTap.getDiemThiLai());
        ps.setFloat(10, ketQuaHocTap.getDiemTB());

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


    public int updateGrade(KetQuaHocTap ketQuaHocTap) throws SQLException {
        Connection cnn = null;
        PreparedStatement ps = null;
        int row = 0;

        try {
            cnn = this.getConnection();
            ps = cnn.prepareStatement("UPDATE ketquahoctap SET hocKy=?, diemQT=?,diemGK=?,diemTH=?,diemCK=?,diemHP=?,diemThiLai=?,diemTB=? WHERE maSV=? AND maHP=?");            
            ps.setString(1, ketQuaHocTap.getMaSV());
            ps.setString(2, ketQuaHocTap.getMaHP());
            ps.setString(3, ketQuaHocTap.getHocKy());
            ps.setFloat(4, ketQuaHocTap.getDiemQT());
            ps.setFloat(5, ketQuaHocTap.getDiemGK());
            ps.setFloat(6, ketQuaHocTap.getDiemTH());
            ps.setFloat(7, ketQuaHocTap.getDiemCK());
            ps.setFloat(8, ketQuaHocTap.getDiemHP());
            ps.setFloat(9, ketQuaHocTap.getDiemThiLai());
            ps.setFloat(10, ketQuaHocTap.getDiemTB());

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

    public int deleteGrade(String maHP) throws SQLException {
        Connection cnn = null;
        PreparedStatement ps = null;
        int row = 0;

        try {
            cnn = this.getConnection();
            ps = cnn.prepareStatement("DELETE FROM ketquahoctap WHERE maHP=?");
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
    public List<KetQuaHocTap> searchGrades(Connection connection, String searchText) throws SQLException {
    List<KetQuaHocTap> grades = new ArrayList<>();

    // Thực hiện truy vấn hoặc các bước tìm kiếm tương ứng dựa trên searchText
    // Ví dụ:
    String query = "SELECT * FROM ketquahoctap WHERE maSV LIKE ?";
    try (PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setString(1, "%" + searchText + "%");

        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            // Đọc dữ liệu từ ResultSet và tạo đối tượng KetQuaHocTap
            KetQuaHocTap grade = new KetQuaHocTap(
                    resultSet.getString("maSV"),
                    resultSet.getString("maHP"),
                    resultSet.getString("hocKy"),
                    resultSet.getFloat("diemQT"),
                    resultSet.getFloat("diemGK"),
                    resultSet.getFloat("diemTH"),
                    resultSet.getFloat("diemCK"),
                    resultSet.getFloat("diemHP"),
                    resultSet.getFloat("diemThiLai"),
                    resultSet.getFloat("diemTB")
            );

            grades.add(grade);
        }
    }

    return grades;
}
}
