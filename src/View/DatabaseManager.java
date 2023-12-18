
package View;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
public class DatabaseManager {
    private static Connection getConnection() throws
ClassNotFoundException, SQLException {
Class.forName("com.mysql.jdbc.Driver");
Connection 
conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/quanlydiemsinhvien","root","");
return conn;
    }
    
   public static boolean isValidLogin(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next(); // If there's a match, the result set will not be empty
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    } 
}
