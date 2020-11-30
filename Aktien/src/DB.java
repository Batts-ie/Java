import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DB {
    public static void Connection()
    {
        String hostname = "localhost";
        String dbPort = "3306";
        String user = "root";
        String pw = "SHW_Destroyer";

        Connection con;
    
        try 
        {
            Class.forName("org.maria.jdbc.Driver");
        } 
        catch (Exception e) 
        {
            System.out.print("Treiber konnte nicht richtig geladen werden");
            e.printStackTrace();
        }
        try 
        {
            con = DriverManager.getConnection("jdbc:mariadb//"+hostname+":"+dbPort+"/"+"?user="+user+"&password="+pw+"&serverTimezone=UTC");   
            Statement stm = con.createStatement();
            ResultSet rS = stm.executeQuery("SELECT * FROM AKTIE");

            con.close();
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }
}