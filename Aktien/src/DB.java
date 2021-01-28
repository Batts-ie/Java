/*import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.time.LocalDate;

public class DB
{
    static WebRequest wr = new WebRequest();
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

 /*   public static void CreateSTM()
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
    public static void CreateTable(String symbol)
    {
        String createTable = "CREATE TABLE IF NOT EXISTS "+symbol+"(DATE CHAR(10) PRIMARY KEY, VALUE DOUBLE, AVERAGE DOUBLE);";

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
    public static void InsertStatementClose(String symbol)
    {
        String insertInTable =  "REPLACE Into" + symbol + "(Date,VALUE) + value  ('?',?);";

        try
        {
            con = DriverManager.getConnection("jdbc:mysql://"+hostname+"/"+dbName+"?user="+userName+"&password="+password);
            Statement stm = con.createStatement();
            for(int i = 0; i< wr.arrayListClose.size();i++)
            {
                insertInTable = "insert into "+ symbol + "(date,value) values (\""+ wr.arrayListDate.get(i).toString() +"\"," + wr.arrayListClose.get(i) + ");";
                stm.execute(insertInTable);
            }
            System.out.println(wr.arrayListClose);
        }
        catch (SQLException e)
        {
            System.out.println("Die Tabelle konnte die Values nicht aufnehmen");
            e.printStackTrace();
        }
    }
    // Select Statement weiterschreiben
    public static void SelectAVGStatement(String symbol) {
        try
        {
            Connection con = DriverManager.getConnection("jdbc:mysql://" + hostname + "/" + dbName + "?user=" + userName + "&password=" + password);
            Statement stmt = con.createStatement();
            for (LocalDate avg : wr.arrayListDate)
            {
                String selectAVGCMD = "Select avg(value) from " + symbol + " where (date < '" + avg.toString() + "') and (datum >= '" + avg.minusDays(200).toString() + "') order by datum desc;";
                ResultSet rs = stmt.executeQuery(selectAVGCMD);
                while(rs.next())
                {
                    for(int i = 0; i<rs.getMetaData().getColumnCount();i++)
                    {
                        wr.arrayListAVG.add(rs.getDouble(i+1));
                    }
                }
            }
            System.out.println(wr.arrayListDate);
            System.out.println(wr.arrayListAVG);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public static void InsertStatementAvg(String symbol)
    {
        String insertInTableAVG = "Replace into "+symbol + "(Date,Average) + values ('?',?);";
        try
        {
            con = DriverManager.getConnection("jdbc:mysql://"+hostname+"/"+dbName+"?user="+userName+"&password="+password);
            Statement stm = con.createStatement();
            for(int i = 0; i<wr.arrayListAVG.size();i++)
            {
                insertInTableAVG = "insert into " +symbol+ "(date, AVERAGE) values (\"" + wr.arrayListDate.get(i).toString() + "\"," + wr.arrayListAVG.get(i) + ");";
                stm.execute(insertInTableAVG);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
    public static void SelectStatement(String symbol){
        String selectCMD = "SELECT * FROM " + symbol+ " order by date desc;";
        try
        {
            con = DriverManager.getConnection("jdbc:mysql://"+hostname+"/"+dbName+"?user="+userName+"&password="+password);
            Statement stm = con.createStatement();
            ResultSet selection = stm.executeQuery(selectCMD);
            stm.execute(selectCMD);
            System.out.println("   Datum         Wert");
            while(selection.next())
            {
                System.out.println(
                    selection.getString("date") + "\t \t \t \t" +
                    selection.getDouble("value") + "\t \t \t \t" +
                    selection.getDouble("average")
                );
                Double avgTemp = selection.getDouble("average");
                wr.dateDB.add(selection.getString("date"));
                wr.closeDB.add(selection.getDouble("value"));
                wr.avgDB.add(avgTemp == 0 ? null : avgTemp);
            }
            wr.dateDB.sort(null);
        }
        catch (SQLException e)
        {
            System.out.println("Konnte keine Select Abfrage durchführen");
            e.printStackTrace();
        }
    }

    /*public HashMap<LocalDate, Double> getAVGByClosingVals(HashMap<LocalDate, Double> closingVals, String symbol)
    {
        HashMap<LocalDate, Double> avgClosing = new HashMap<>();
        for(LocalDate key : closingVals.keySet())
        {
            avgClosing.put(key, SelectAVGStatement(symbol, key));
        }
        return avgClosing;
    }
}*/
