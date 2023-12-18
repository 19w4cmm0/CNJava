/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import Model.ChuyenNganh;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
public class DAOManagerment {
    
    private static Connection getConnection() throws
ClassNotFoundException, SQLException {
Class.forName("com.mysql.jdbc.Driver");
Connection 
conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/quanlydiemsinhvien","root","");
return conn;
}
public List<ChuyenNganh> getChuyenNganh() throws
SQLException{
Connection cnn=null;
PreparedStatement ps=null;
ResultSet rs=null;
List<ChuyenNganh> chuyennganhList=new ArrayList<ChuyenNganh>();
try {
cnn=this.getConnection();
String sql="Select * from hanghoa";
ps=cnn.prepareStatement(sql);
rs=ps.executeQuery();
while(rs.next()) {
chuyennganhList.add(new ChuyenNganh(rs.getInt(1),rs.getString(2)));
}
return chuyennganhList;
}catch(Exception e) {
System.out.print(e);
}
finally {
if(ps!=null)
ps.close();
if(cnn!=null)
cnn.close();
}
return null;
}
public int insertChuyenNganh(ChuyenNganh cn) {
Connection cnn=null;
PreparedStatement ps=null;
int row=0;
try {
cnn=this.getConnection();
ps=cnn.prepareStatement("insert into ChuyenNganh values(?,?)");
ps.setInt(1,cn.getMaNganh());
ps.setString(2, cn.getTenNganh());
row=ps.executeUpdate();
} catch (ClassNotFoundException e) {
// TODO Auto-generated catch block
e.printStackTrace();
} catch (SQLException e) {
// TODO Auto-generated catch block
e.printStackTrace();
}
return row;
}
public int updateChuyenNganh(ChuyenNganh cn) {
    Connection cnn = null;
    PreparedStatement ps = null;
    int row = 0;
    try {
        cnn = this.getConnection();
        ps = cnn.prepareStatement("update ChuyenNganh set tenNganh=? where maNganh=?");  

        // Chắc chắn rằng các giá trị đang đúng
        System.out.println("Updating ChuyenNganh: " + cn.getMaNganh() + ", " + cn.getTenNganh());

        ps.setString(1, cn.getTenNganh());
        ps.setInt(2, cn.getMaNganh());

        row = ps.executeUpdate();
        System.out.println("Rows updated: " + row);
    } catch (ClassNotFoundException | SQLException e) {
        // In ra thông báo lỗi cụ thể
        e.printStackTrace();
    } finally {
        // Đóng tài nguyên
        try {
            if (ps != null)
                ps.close();
            if (cnn != null)
                cnn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    return row;
}
public int deleteChuyenNganh(ChuyenNganh cn) {
Connection cnn=null;
PreparedStatement ps=null;
int row=0;
try {
cnn=this.getConnection();
ps=cnn.prepareStatement("Delete From chuyennganh where maNganh=?");
ps.setInt(1,cn.getMaNganh());
row=ps.executeUpdate();
} catch (ClassNotFoundException e) {
// TODO Auto-generated catch block
e.printStackTrace();
} catch (SQLException e) {
// TODO Auto-generated catch block
e.printStackTrace();
}
return row;
}






}
