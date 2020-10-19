import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DB{

    public static void Connection(){
        
        String url = "jdbc;ysql://localhost:3306/demo"; // 3306 ist der Port und Localhost = 127.0.0.1
        String userName = "root";
        String password = "PASSWORT_HERE";

        try {
            Connection myConnection = DriverManager.getConnection(url, userName, password);
            Statement statement = myConnection.createStatement();
            String sql = "INSERT INTO FEIERTAGE" + "(tag, monat, jahr)";
            statement.executeUpdate(sql);
        }
            
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}