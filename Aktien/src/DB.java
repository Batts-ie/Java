import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public class DB{

    public static void Connection(){
        String hostname = "localhost";
        String dBPort = "3306";
        String userName = "root";
        String password = "SHW_Destroyer";

        Connection conn;
		 
        try{
            Class.forName("org.mariadb.jdbc.Driver");
        }
        catch(Exception e)
        {
            System.out.println("Treiber konnte nicht richtig geladen werden ... ");
            e.printStackTrace();
        }
        try {
               conn = DriverManager.getConnection("jdbc:mariadb//"+hostname+":"+dBPort+"/"+"?user="+userName+"&password="+password+"&serverTimezone=UTC");
               Statement statement = conn.createStatement();
               ResultSet rS=statement.executeQuery("Select * from ");

               conn.close();
           }
           catch (SQLException e) {
               e.printStackTrace();
           }
    }
}