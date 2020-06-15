package mitchellbryantsoftware2;
import java.sql.*;
import javax.swing.*;


public class MySqlConnect {

static Connection conn = null;
public static Connection createConnection() {
    
    try {
        Class.forName("com.mysql.jdbc.Driver");
        conn = DriverManager.getConnection("jdbc:mysql://52.206.157.109:3306/U04N4F", "U04N4F", "53688287508");
        return conn;
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null,e);
        return conn;
    }
}
public static Connection get() {
    if (conn == null) {
        conn = createConnection();
    }
    return conn;
}
}