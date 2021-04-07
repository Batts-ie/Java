import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

import org.json.*;
import org.apache.commons.io.IOUtils;
import org.w3c.dom.ls.LSOutput;

public class WebRequest
{
    public JSONObject json;

    public static ArrayList<Double> arrayListClose = new ArrayList<>();
    public static ArrayList<LocalDate> arrayListDate = new ArrayList<>();
    public static ArrayList<Double> arrayListAVG = new ArrayList<>();
    public static ArrayList<Double> avgDB = new ArrayList<>();
    public static ArrayList<Double> closeDB = new ArrayList<>();
    public static ArrayList<String> dateDB = new ArrayList<>();

    /*
    new Arraylist for corrected
     */
    public static ArrayList<Double> splitList = new ArrayList<>();
    public static ArrayList<Double> splitCorrected = new ArrayList<>();


    private final String key="&apikey=1AD6CE6LV8OFT02F";
    private String requestString = "https://www.alphavantage.co/query?";
    private String function = "function=TIME_SERIES_DAILY_ADJUSTED&";
    private String prefsymbol = "&symbol=";

    public static Connection con;
    private static String hostname = "localhost";
    private static String dbName = "Aktien";
    private String dBPort = "3306";
    private static String userName = "root";
    private static String password = "SHW_Destroyer";

    public JSONObject Request(String urlString)
    {


        HttpURLConnection con;
        try
        {
            URL url = new URL(urlString);
            con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            con.setUseCaches(false);
            con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);

            try(BufferedReader br = new BufferedReader((new InputStreamReader(con.getInputStream(),StandardCharsets.UTF_8))))
            {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while((responseLine = br.readLine()) != null)
                {
                    response.append(responseLine.trim());
                }
                System.out.println(response.toString());
                return new JSONObject(response.toString());
            }
        }catch (Exception e)
         {
            System.out.println("Der requestString kann entweder nicht gefunden werden, oder nicht geöffnet werden");
            e.printStackTrace();
         }
        return null;
    }
    public String StringBuilder(String symbol)
    {
        String s = requestString+function+prefsymbol+symbol+key;

        return s;
    }

    public void GetCloseValues(String symbol) throws JSONException, IOException
    {
        try
        {
            String url = "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol="+ symbol + "&outputsize=full&apikey=1AD6CE6LV8OFT02F";
            JSONObject json = new JSONObject(IOUtils.toString(new URL(url), Charset.forName("UTF-8")));
            json = json.getJSONObject("Time Series (Daily)");
            for(int i = 0; i< json.names().length();i++)
            {
                arrayListDate.add(LocalDate.parse((CharSequence) json.names().get(i)));
                arrayListDate.sort(null);
                arrayListClose.add(json.getJSONObject(LocalDate.parse((CharSequence) json.names().get(i)).toString()).getDouble("4. close"));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
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
    public static void CreateTable(String symbol)
    {
        String createTable = "CREATE TABLE IF NOT EXISTS "+symbol+" (DATE CHAR(10) PRIMARY KEY, VALUE DOUBLE, AVERAGE DOUBLE);";
        String createTableavg = "CREATE TABLE IF NOT EXISTS "+symbol+"avg (DATE CHAR(10) PRIMARY KEY, AVERAGE DOUBLE);";

        try
        {
            con = DriverManager.getConnection("jdbc:mysql://"+hostname+"/"+dbName+"?user="+userName+"&password="+password);
            Statement stm = con.createStatement();
            stm.execute(createTable);
            stm.execute(createTableavg);
        }
        catch (SQLException e)
        {
            System.out.println("Die Tabelle konnte nicht erzeugt werden");
            e.printStackTrace();
        }
    }
    public static void InsertStatementClose(String symbol)
    {
        String sql = "replace into " + symbol + "(date, value ) VALUES('?', ?);";
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://" + hostname + "/" + dbName + "?user=" + userName + "&password=" + password);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            for (int i = 0; i < arrayListClose.size(); i++) {
                sql = "replace into " + symbol + "(date , value) VALUES(\""+ arrayListDate.get(i).toString()+"\","+ arrayListClose.get(i)+");";
                pstmt.execute(sql);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    // Select Statement weiterschreiben
    public static void SelectAVGStatement(String symbol) {
        try
        {
            Connection con = DriverManager.getConnection("jdbc:mysql://" + hostname + "/" + dbName + "?user=" + userName + "&password=" + password);
            Statement stmt = con.createStatement();
            for (LocalDate avg : arrayListDate)
            {
                String selectAVGCMD = "Select avg(value) from " + symbol + " where (date < '" + avg.toString() + "') and (date >= '" + avg.minusDays(200).toString() + "') order by date desc;";
                ResultSet rs = stmt.executeQuery(selectAVGCMD);
                while(rs.next())
                {
                    for(int i = 0; i<rs.getMetaData().getColumnCount();i++)
                    {
                        arrayListAVG.add(rs.getDouble(i+1));
                    }
                }
            }

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
            for(int i = 0; i<arrayListAVG.size();i++)
            {
                insertInTableAVG = "replace into " +symbol+ "avg (date, AVERAGE) values (\"" + arrayListDate.get(i).toString() + "\"," + arrayListAVG.get(i) + ");";
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
        String selectCMDAVG = "SELECT * FROM " + symbol+ "avg order by date desc";
        try
        {
            con = DriverManager.getConnection("jdbc:mysql://"+hostname+"/"+dbName+"?user="+userName+"&password="+password);
            Statement stm = con.createStatement();
            ResultSet selection = stm.executeQuery(selectCMD);
            ResultSet selectionAVG = stm.executeQuery(selectCMDAVG);
            stm.execute(selectCMD);
            stm.execute(selectCMDAVG);
            System.out.println("   Datum         Wert");
            while(selection.next() && selectionAVG.next())
            {
                System.out.println(
                        selection.getString("date") + "\t \t \t \t" +
                                selection.getDouble("value") + "\t \t \t \t" +
                                selectionAVG.getDouble("average")
                );
                Double avgTemp = selectionAVG.getDouble("average");
                dateDB.add(selectionAVG.getString("date"));
                closeDB.add(selection.getDouble("VALUE"));
                avgDB.add(avgTemp == 0 ? null : avgTemp);
            }
            dateDB.sort(null);
        }
        catch (SQLException e)
        {
            System.out.println("Konnte keine Select Abfrage durchführen");
            e.printStackTrace();
        }
    }
    public double getLowerBound(String symbol){
        String minCMD = "SELECT MIN(value) FROM "+symbol+";";
        double min = 0;
        try {
            con = DriverManager.getConnection("jdbc:mysql://" + hostname + "/" + dbName + "?user=" + userName + "&password=" + password);
            Statement stm = con.createStatement();
            ResultSet rsmin = stm.executeQuery(minCMD);
            while(rsmin.next()){
                min = rsmin.getDouble(1);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return min;
    }
    public double getUpperBound(String symbol){
            String maxCMD = "SELECT MAX(value) FROM "+symbol+";";
            double max = 0;
            try {
                con = DriverManager.getConnection("jdbc:mysql://" + hostname + "/" + dbName + "?user=" + userName + "&password=" + password);
                Statement stm = con.createStatement();
                ResultSet rsmax = stm.executeQuery(maxCMD);
                while (rsmax.next()){
                    max = rsmax.getDouble(1);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            return max;
    }
    public static void getSplit(String symbol) throws JSONException, IOException {
        try {
            String url = "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY_ADJUSTED&symbol=" + symbol + "&outputsize=full&apikey=1AD6CE6LV8OFT02F";
            JSONObject json = new JSONObject(IOUtils.toString(new URL(url), Charset.forName("UTF-8")));
            json = json.getJSONObject("Time Series (Daily)");
            for (int i = 0; i < json.names().length(); i++) {
                arrayListDate.add(LocalDate.parse((CharSequence) json.names().get(i)));
                arrayListDate.sort(null);
                splitList.add(json.getJSONObject(LocalDate.parse((CharSequence) json.names().get(i)).toString()).getDouble("8. split coefficient"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void split(String symbol){
        String cmd = "SELECT * FROM " + symbol + "spcorrected ORDER BY DATE DESC;";
        try {
            con = DriverManager.getConnection("jdbc:mysql://" + hostname + "/" + dbName + "?user=" + userName + "&password=" + password);
            arrayListDate = new ArrayList<>();
            splitList = new ArrayList<>();
            arrayListClose = new ArrayList<>();
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery(cmd);
            while(rs.next()){
                rs.getString("DATE");
                rs.getDouble("CLOSE");
                rs.getDouble("CORRECTED");
                arrayListDate.add(LocalDate.parse(rs.getString("DATE")));
                arrayListClose.add(rs.getDouble("CLOSE"));
                splitList.add(rs.getDouble("CORRECTED"));
            }
            double div = 1;
            for (int i = 0; i < splitList.size(); i++){
                splitCorrected.add(arrayListClose.get(i) / div);
                div *= splitList.get(i);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void selectInsertSplit(String symbol){
        String cmdC = "CREATE TABLE IF NOT EXISTS" + symbol + "spcorrected (DATE DATE, DECIMAL CLOSE, DECIMAL CORRECTED, PRIMARY KEY(DATE));";
        String cmdI = "INSERT INTO " + symbol + "spcorrected (DATE, CLOSE, CORRECTED) VALUE('?',?,?);";

        try {
            con = DriverManager.getConnection("jdbc:mysql://" + hostname + "/" + dbName + "?user=" + userName + "&password=" + password);
            Statement stm = con.createStatement();
            stm.execute(cmdC);
            for(int i = 0; i < splitList.size(); i++){
                cmdI = "INSERT INTO "+ symbol + "spcorrected (DATE, CLOSE, CORRECTED) VALUES(\""+ arrayListDate.get(i).toString() + "\"," +
                        arrayListClose.get(i) + "," + splitList.get(i) + ";";
                stm.execute(cmdI);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}