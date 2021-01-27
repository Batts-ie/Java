import java.sql.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class DB
{
    static Connection con;
    private static String hostname = "localhost";
    private static String dbName = "Aktien";
    private String dBPort = "3306";
    private static String userName = "root";
    private static String password = "SHW_Destroyer";
    /*public static void Connection()
    {
        String hostname = "localhost";
        String dbName = "Aktien";
        String dBPort = "3306";
        String userName = "root";
        String password = "SHW_Destroyer";
		 
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
               con = DriverManager.getConnection("jdbc:mysql://"+hostname+"/"+dbName+"?user="+userName+"&password="+password);
               Statement statement = con.createStatement();
               //ResultSet rS=statement.executeQuery("Select * from ");

               con.close();
        }
        catch (SQLException e)
        {
               e.printStackTrace();
        }
    }*/

    public static void InsertStatement(String symbol, String date, double number)
    {

        String insertInTable =  "REPLACE INTO "+symbol+" VALUES('"+date+"', "+number+");";

        try
        {
            con = DriverManager.getConnection("jdbc:mysql://"+hostname+"/"+dbName+"?user="+userName+"&password="+password);
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
        String createTable = "CREATE TABLE IF NOT EXISTS "+symbol+"(DATE CHAR(10) PRIMARY KEY, VALUE DOUBLE);";

        try
        {
            con = DriverManager.getConnection("jdbc:mysql://"+hostname+"/"+dbName+"?user="+userName+"&password="+password);
            Statement stm = con.createStatement();
            stm.execute(createTable);
        }
        catch (SQLException e)
        {
            System.out.println("Die Tabelle konnte nicht erzeugt werden");
            e.printStackTrace();
        }
    }
    public static void SelectStatement(String symbol){
        String selectCMD = "SELECT * FROM " + symbol+ ";";
        try
        {
            con = DriverManager.getConnection("jdbc:mysql://"+hostname+"/"+dbName+"?user="+userName+"&password="+password);
            Statement stm = con.createStatement();
            ResultSet selection = stm.executeQuery(selectCMD);
            stm.execute(selectCMD);
            System.out.println("   Datum         Wert");
            while(selection.next())
            {
                String date = selection.getString("Date");
                String val = selection.getString("Value");

                System.out.printf("%1s", date);
                System.out.printf("%12s", val);
                System.out.println();
            }
        }
        catch (SQLException e)
        {
            System.out.println("Konnte keine Select Abfrage durchführen");
            e.printStackTrace();
        }
    }
    // Select Statement weiterschreiben
    public double SelectAVGStatement(String symbol, LocalDate avgDate){
        String selectAVGCMD = "SELECT AVG(value) FROM" + symbol + "WHERE date BETWEEN DATE_SUB(CURRENT_DATE(), INTERVAL 200 DAY) AND CURRENT_DATE();";

        double avg = 0;
        try
        {
            con = DriverManager.getConnection("jdbc:mysql://"+hostname+"/"+dbName+"?user="+userName+"&password="+password);
            PreparedStatement stm = con.prepareStatement(selectAVGCMD);
            stm.setDate(1, java.sql.Date.valueOf(String.valueOf(avgDate)));
            stm.setDate(2, java.sql.Date.valueOf(String.valueOf(avgDate)));
            ResultSet rs = stm.executeQuery();
            while(rs.next())
            {
                avg = rs.getDouble("AVG(value)");
            }
        }
        catch (SQLException e)
        {
            System.out.println("Konnte keine Select Abfrage durchführen");
            e.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return avg;
    }

    public HashMap<LocalDate, Double> getAVGByClosingVals(HashMap<LocalDate, Double> closingVals, String symbol)
    {
        HashMap<LocalDate, Double> avgClosing = new HashMap<>();
        for(LocalDate key : closingVals.keySet())
        {
            avgClosing.put(key, SelectAVGStatement(symbol, key));
        }
        return avgClosing;
    }

    public static void CreateSTM()
    {
        String createDatabase = "CREATE DATABASE IF NOT EXISTS "+dbName;
        try {
                con = DriverManager.getConnection("jdbc:mysql://"+hostname+"/"+dbName+"?user="+userName+"&password="+password);
                Statement stm = con.createStatement();
                stm.execute(createDatabase);
            }
        catch (SQLException e)
                 {
                    System.out.println("Die Datenbank "+dbName+" konnte nicht erstellt werden");
                    e.printStackTrace();
                 }
    }
    public static void UseSTM()
    {
        String useDatabase = "USE "+dbName;
        try
        {
            con = DriverManager.getConnection("jdbc:mysql://"+hostname+"/"+dbName+"?user="+userName+"&password="+password);
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