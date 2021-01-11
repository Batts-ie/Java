import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public class DB
{
   static Connection con;

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
               /*Statement statement = */con.createStatement();
               //ResultSet rS=statement.executeQuery("Select * from ");

               //con.close();
        }
        catch (SQLException e)
        {
               e.printStackTrace();
        }
    }

    public static void InsertStatement(String key, String symbol, double number)
    {
        String insertInTable =  "INSERT OR UPDATE INTO "+symbol+" VALUES('"+key+"', "+number+");";

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

    public static void CreateTable(String symbol)
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

    public static void CreateSTM(String dbName)
    {
        String createDatabase = "CREATE DATABASE IF NOT EXISTS "+dbName;
        try {
                Statement stm = con.createStatement();
                stm.execute(createDatabase);
            }
        catch (SQLException e)
                 {
                    System.out.println("Die Datenbank "+dbName+" konnte nicht erstellt werden");
                    e.printStackTrace();
                 }
    }
    public static void UseSTM(String dbName)
    {
        String useDatabase = "USE "+dbName;
        try
        {
            Statement stm = con.createStatement();
            stm.execute(useDatabase);
        }
        catch (SQLException e)
        {
            System.out.println("Konnte die Datenbank "+dbName+" nicht festlegen");
            e.printStackTrace();
        }
    }
}