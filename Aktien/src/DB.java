import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public class DB{

    public static void Connection()
    {
        String hostname = "localhost";
        String dBPort = "3306";
        String userName = "root";
        String password = "SHW_Destroyer";

        Connection con;
		 
        try
        {
            Class.forName("org.mariadb.jdbc.Driver");
        }
        catch(Exception e)
        {
            System.out.println("Treiber konnte nicht richtig geladen werden ... ");
            e.printStackTrace();
        }
        try
        {
               con = DriverManager.getConnection("jdbc:mariadb//"+hostname+":"+dBPort+"/"+"?user="+userName+"&password="+password+"&serverTimezone=UTC");
               Statement statement = con.createStatement();
             /*  ResultSet rS=statement.executeQuery("Select * from "); */

               con.close();
        }
        catch (SQLException e)
        {
               e.printStackTrace();
        }
    }

    public static void InsertStatement(Connection con, String key, String symbol, double number)
    {
        String insertInTable =  "INSERT OR REPLACE INTO "+symbol+" VALUES('"+key+"', "+number+");";

        try
        {
            Statement stm = con.createStatement();
            stm.execute(insertInTable);
        }
        catch (SQLException e)
        {
            System.out.println("Die Tabelle konnte die Values nicht aufnehmen");
            e.printStackTrace();
        }
    }

    public static void CreateTable(Connection con, String symbol)
    {
        String createTable = "CREATE TABLE IF NOT EXISTS"+symbol+"(DATE CHAR(10) PRIMARY KEY, VALUE DOUBLE);";

        try
        {
            Statement stm = con.createStatement();
            stm.execute(createTable);
        }
        catch (SQLException e)
        {
            System.out.println("Die Tabelle konnte nicht erzeugt werden");
            e.printStackTrace();
        }
    }
}