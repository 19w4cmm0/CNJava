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

/**
 *
 * @author Admin
 */
public class FetchDataChuyenNganh {
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
List<ChuyenNganh> productList=new
ArrayList<ChuyenNganh>();
try {
cnn=this.getConnection();
String sql="Select * from chuyennganh";
ps=cnn.prepareStatement(sql);
rs=ps.executeQuery();
while(rs.next()) {
productList.add(new
ChuyenNganh(rs.getInt(1),rs.getString(2)));
}
return productList;
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
}
