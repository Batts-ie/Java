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
        String password = "PASSWORT";

        Connection conn;
		 
        try{
            Class.forName("com.mysql.jdbc.Driver");
        }
        catch(Exception e)
        {
            System.out.println("Treiber konnte nicht richtig geladen werden ... ");
            e.printStackTrace();
        }
        try {
               conn = DriverManager.getConnection("jdbc:mysql://"+hostname+"/"+"?user="+userName+"&password="+password+"&serverTimezone=UTC");
               Statement statement = conn.createStatement();
               ResultSet rS=statement.executeQuery("Select * from kalender");
               System.out.println("Zeit                                 Montag      Dienstag        Mittwoch        Donnerstag      Freitag     Samstag" +
                       "       Sonntag         Startjahr       Endjahr");
               while(rS.next()){
                   String zeit = rS.getString("Datum");
                   String Montag = rS.getString("Montag");
                   String Dienstag = rS.getString("Dienstag");
                   String Mittwoch = rS.getString("Mittwoch");
                   String Donnerstag = rS.getString("Donnerstag");
                   String Freitag = rS.getString("Freitag");
                   String Samstag = rS.getString("Samstag");
                   String Sonntag = rS.getString("Sonntag");
                   String startjahr = rS.getString("Startjahr");
                   String endjahr = rS.getString("Endjahr");


                   System.out.printf("%1s",zeit);
                   System.out.printf("%18s", Montag);
                   System.out.printf("%9s", Dienstag);
                   System.out.printf("%14s", Mittwoch);
                   System.out.printf("%15s", Donnerstag);
                   System.out.printf("%13s", Freitag);
                   System.out.printf("%10s", Samstag);
                   System.out.printf("%12s", Sonntag);
                   System.out.printf("%17s", startjahr);
                   System.out.printf("%15s", endjahr);
                   System.out.println();
               }
               conn.close();
           }
           catch (SQLException e) {
               e.printStackTrace();
           }
    }
}