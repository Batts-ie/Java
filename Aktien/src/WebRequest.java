import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import org.json.*;
import org.apache.commons.io.IOUtils;

import javax.swing.plaf.nimbus.State;

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
                arrayListClose.add(json.getJSONObject(LocalDate.parse((CharSequence) json.names().get(i)).toString()).getDouble("4. close"));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public void CreateSTM()
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
    public void CreateTable(String symbol)
    {
        String dropTable = "drop table if exists " + symbol;
        String createTable = "CREATE TABLE IF NOT EXISTS "+symbol+" (datum DATE PRIMARY KEY unique, close DOUBLE);";
        String dropTableavg = "drop table if exists " + symbol+"avg ;";
        String createTableavg = "CREATE TABLE IF NOT EXISTS "+symbol+"avg (datum DATE PRIMARY KEY unique, AVERAGE DOUBLE);";
        String dropTableC = "drop table if exists " + symbol+"spcorrected ;";
        String cmdC = "CREATE TABLE IF NOT EXISTS " + symbol + "spcorrected (datum DATE PRIMARY KEY unique , close DOUBLE, CORRECTED DOUBLE);";

        try
        {
            con = DriverManager.getConnection("jdbc:mysql://"+hostname+"/"+dbName+"?user="+userName+"&password="+password);
            Statement stm = con.createStatement();
            stm.execute(dropTable);
            stm.execute(createTable);
            stm.execute(dropTableavg);
            stm.execute(createTableavg);
            stm.execute(dropTableC);
            stm.execute(cmdC);
        }
        catch (SQLException e)
        {
            System.out.println("Die Tabelle konnte nicht erzeugt werden");
            e.printStackTrace();
        }
    }
    public void InsertStatementClose(String symbol)
    {
        String sql = "insert into " + symbol + " (datum, close ) VALUES('?', ?);";
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://" + hostname + "/" + dbName + "?user=" + userName + "&password=" + password);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            for (int i = 0; i < arrayListDate.size(); i++) {
                sql = "insert into " + symbol + " (datum , close) VALUES(\""+ arrayListDate.get(i).toString()+"\","+ arrayListClose.get(i)+");";
                pstmt.execute(sql);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    // Select Statement weiterschreiben
    public void SelectAVGStatement(String symbol) {
        try
        {
            Connection con = DriverManager.getConnection("jdbc:mysql://" + hostname + "/" + dbName + "?user=" + userName + "&password=" + password);
            Statement stmt = con.createStatement();
            for (LocalDate avg : arrayListDate)
            {
                String selectAVGCMD = "Select avg(close) from " + symbol + " where (datum < '" + avg.toString() + "') and (datum >= '" + avg.minusDays(200).toString() + "') order by datum desc;";
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
    public void InsertStatementAvg(String symbol)
    {
        String insertInTableAVG;
        try
        {
            con = DriverManager.getConnection("jdbc:mysql://"+hostname+"/"+dbName+"?user="+userName+"&password="+password);
            Statement stm = con.createStatement();
            for(int i = 0; i<arrayListAVG.size();i++)
            {
                insertInTableAVG = "insert into " +symbol+ "avg (datum, AVERAGE) values (\"" + arrayListDate.get(i).toString() + "\"," + arrayListAVG.get(i) + ");";
                stm.execute(insertInTableAVG);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
    public void selectAll(String symbol) {
        String sql = "SELECT * FROM "+ symbol +" order by datum;";
        String sqlAVG = "SELECT * FROM "+ symbol +"AVG order by datum;";
        try {
            con = DriverManager.getConnection("jdbc:mysql://"+hostname+"/"+dbName+"?user="+userName+"&password="+password);
            Statement stmt = con.createStatement();
            Statement stmtAVG  = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            ResultSet rsAVG = stmtAVG.executeQuery(sqlAVG);

            System.out.println("Datum               Close Werte             Durchschnitt");
            while (rs.next() && rsAVG.next()) {
                System.out.println(
                        rs.getString("datum")  + "\t \t \t \t" +
                                rs.getDouble("close") + "\t \t \t \t" +
                                rsAVG.getDouble("Average")
                );
                Double avgTemp = rsAVG.getDouble("Average");
                dateDB.add(rsAVG.getString("datum"));
                closeDB.add(rs.getDouble("close"));
                avgDB.add(avgTemp == 0 ? null : avgTemp);
            }
            dateDB.sort(null);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public double getLowerBound(String symbol){
        String minCMD = "SELECT MIN(close) FROM "+symbol+";";
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
        String maxCMD = "SELECT MAX(close) FROM "+symbol+";";
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
    public void ListNull()
    {
        dateDB = new ArrayList<>();
        closeDB = new ArrayList<>();
        avgDB = new ArrayList<>();
    }
    public void getSplit(String symbol) throws JSONException, IOException {
        try {
            String url = "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY_ADJUSTED&symbol=" + symbol + "&outputsize=full&apikey=1AD6CE6LV8OFT02F";
            JSONObject json = new JSONObject(IOUtils.toString(new URL(url), Charset.forName("UTF-8")));
            json = json.getJSONObject("Time Series (Daily)");
            for (int i = 0; i < json.names().length(); i++) {
                arrayListDate.add(LocalDate.parse((CharSequence) json.names().get(i)));
                splitList.add(json.getJSONObject(LocalDate.parse((CharSequence) json.names().get(i)).toString()).getDouble("8. split coefficient"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void selectInsertSplit(String symbol){
        String sqlI = "Insert ignore into " + symbol + "spcorrected (datum,close,Corrected) values ('?',?,?);";
        try {
            con = DriverManager.getConnection("jdbc:mysql://" + hostname + "/" + dbName + "?user=" + userName + "&password=" + password);
            PreparedStatement pstm = con.prepareStatement(sqlI);
            for(int i = 0; i < splitList.size(); i++){
                sqlI = "insert into "+ symbol + "spcorrected (datum, close, CORRECTED) VALUES(\""+ arrayListDate.get(i).toString() + "\"," +
                        arrayListClose.get(i) + "," + splitList.get(i) + ");";
                pstm.execute(sqlI);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public void split(String symbol){
        String cmd = "SELECT * FROM " + symbol + "spcorrected ORDER BY datum DESC;";
        try {
            con = DriverManager.getConnection("jdbc:mysql://" + hostname + "/" + dbName + "?user=" + userName + "&password=" + password);
            arrayListDate = new ArrayList<>();
            splitList = new ArrayList<>();
            arrayListClose = new ArrayList<>();
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery(cmd);
            while(rs.next()){
                rs.getString("datum");
                rs.getDouble("close");
                rs.getDouble("CORRECTED");
                arrayListDate.add(LocalDate.parse(rs.getString("datum")));
                arrayListClose.add(rs.getDouble("close"));
                splitList.add(rs.getDouble("CORRECTED"));
            }
            double div = 1;
            for (int i = 0; i < splitList.size(); i++){
                splitCorrected.add(arrayListClose.get(i) / div);
                div = div * splitList.get(i);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void update(String symbol){
        String cmd;
        try{
            con = DriverManager.getConnection("jdbc:mysql://" + hostname + "/" + dbName + "?user=" + userName + "&password=" + password);
            Statement stm = con.createStatement();
            for (int i = 0; i < arrayListClose.size(); i++){
                cmd = "UPDATE "+ symbol + " SET close = " + splitCorrected.get(i) + " WHERE datum = \"" + arrayListDate.get(i).toString() + "\";";
                stm.execute(cmd);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private LocalDate currentdate = LocalDate.of(2010, 1, 1);
    int count = 0;
    boolean bought = false;
    double money = 100000;


    public void createTradingTable(String symbol){

        String cmdC;
        String cmdD;
        try{
            con = DriverManager.getConnection("jdbc:mysql://" + hostname + "/" + dbName + "?user=" + userName + "&password=" + password);
            cmdC = "CREATE TABLE if not exists " + symbol + "trading (currentDate DATE NOT NULL PRIMARY KEY, bought tinyint, count int, money int);";
            cmdD = "CREATE TABLE if not exists " + symbol + "bh (currentDate DATE NOT NULL PRIMARY KEY, bought tinyint, count int, money int);";
            Statement stm = con.createStatement();
            stm.execute(cmdC);
            stm.execute(cmdD);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /*public void getTradingVals(String symbol){
        String cmdC;
        try{
            con = DriverManager.getConnection("jdbc:mysql://" + hostname + "/" + dbName + "?user=" + userName + "&password=" + password);
            cmdC = "SELECT * FROM " + symbol + "trading";
            Statement stm = con.createStatement();
            stm.execute(cmdC);
        }catch (Exception e){
            e.printStackTrace();
        }
    }*/
    ArrayList<LocalDate> dateTradeList = new ArrayList<>();
    ArrayList<Double> closeTradeList = new ArrayList<>();
    ArrayList<Double> averageTradeList = new ArrayList<>();
    LocalDate current = LocalDate.now();
    // Trading 200er Strategy
    public void insertStartTrade(String symbol, String endung) {
        String sql = "insert into " + symbol + endung +" (currentdate, bought, count, money) values ('?',?,?,?);";
        try {
            con = DriverManager.getConnection("jdbc:mysql://" + hostname + "/" + dbName + "?user=" + userName + "&password=" + password);
            PreparedStatement ptsmt = con.prepareStatement(sql);
            sql = "insert into " + symbol + endung + " (currentdate, bought, count, money) values " +
                    "(\'" + currentdate.minusDays(1) + "\','" + symbol + "','s',0," + money + ");";
            ptsmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void fillDateTradeList(String symbol) {
        dateTradeList = new ArrayList<LocalDate>();
        closeTradeList = new ArrayList<Double>();
        averageTradeList = new ArrayList<Double>();
        String sql = "select datum,close from " + symbol + " where datum between \'" + currentdate + "\' AND \'" + current.minusDays(1) + "\' ;";
        String sqlAvg = "select AVERAGE from " + symbol + "avg where datum between \'" + currentdate + "\' " +
                "AND \'" + current.minusDays(1) + "\';";

        try {
            con = DriverManager.getConnection("jdbc:mysql://" + hostname + "/" + dbName + "?user=" + userName + "&password=" + password);
            Statement smt = con.createStatement();
            Statement stmtAvg = con.createStatement();
            ResultSet rs = smt.executeQuery(sql);
            ResultSet rsA = stmtAvg.executeQuery(sqlAvg);
            while (rs.next() && rsA.next()) {
                rs.getString("datum");
                rs.getDouble("close");
                rsA.getDouble("AVERAGE");
                dateTradeList.add(LocalDate.parse(rs.getString("datum")));
                closeTradeList.add(rs.getDouble("close"));
                averageTradeList.add(rsA.getDouble("gleitenderDurchschnitt"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void trading200(String symbol) throws SQLException {
        String flag = null;
        int anzahl = 0;
        int depot=0;
        String endung = "trading";
        insertStartTrade(symbol,endung);
        System.out.println("Trading with _200");
        for (int i = 0; i < dateTradeList.size(); i++) {
            int rest = 0;
            flag = null;
            anzahl = 0;
            depot = 0;
            String sqlFlag = "select * from " + symbol + "trade order by datum desc limit 1";
            Connection con = null;
            try {
                con = DriverManager.getConnection("jdbc:mysql://" + hostname + "/" + dbName + "?user=" + userName + "&password=" + password);
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(sqlFlag);
                while (rs.next()) {
                    flag = rs.getString("flag");
                    anzahl = rs.getInt("number");
                    depot = rs.getInt("depot");
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
            finally {
                con.close();
            }

            if (flag.equals("s")) {
                if (!dateTradeList.get(i).getDayOfWeek().equals(DayOfWeek.SATURDAY)
                        || (!dateTradeList.get(i).getDayOfWeek().equals(DayOfWeek.SUNDAY))) {
                    if (closeTradeList.get(i) > averageTradeList.get(i)) {
                        anzahl = (int) (depot / (closeTradeList.get(i)));
                        rest = (int) (anzahl * closeTradeList.get(i));
                        depot = (depot - rest);
                        flag = "b";

                        insertTradeIntoDB(symbol,(LocalDate) dateTradeList.get(i),bought,endung, count, money);
                        //System.out.println("bought");
                        //System.out.println(anzahl + " number of stocks");
                    }
                }
            } else if (flag.equals("b")) {
                if (!dateTradeList.get(i).getDayOfWeek().equals(DayOfWeek.SATURDAY)
                        || (!dateTradeList.get(i).getDayOfWeek().equals(DayOfWeek.SUNDAY))) {
                    if (closeTradeList.get(i) < averageTradeList.get(i)) {
                        depot = (int) ((anzahl * closeTradeList.get(i)) + depot);
                        flag = "s";
                        anzahl = 0;
                        insertTradeIntoDB(symbol,(LocalDate) dateTradeList.get(i),bought,endung, count, money);
                        //System.out.println("sold");
                        //System.out.println(depot + " money in depot");
                    }
                }
                if(dateTradeList.get(i) == dateTradeList.get(dateTradeList.size()-1))
                {
                    double tempClose = arrayListClose.get(dateTradeList.size() - 1);
                    lastSale(tempClose, flag, depot, anzahl);
                    insertTradeIntoDB(symbol,(LocalDate) dateTradeList.get(i),bought,endung, count, money);
                }
            }
            else
            {
                System.out.println("Datenbankfehler");
            }
        }

        System.out.println(symbol);
        depot = (int) (depot - money);
        System.out.println(depot + " money in depot");
        System.out.println(((depot/money)*100.00) + " prozentueller Gewinn");
    }
    public void insertTradeIntoDB (String symbol,LocalDate dateTrading, boolean bought, String end, int count, double money) throws SQLException
    {
        String insertFlag = "insert into " + symbol + end +" (datum, bought,  count, money) values ('?',?,?,?,?);";
        try {
            con = DriverManager.getConnection("jdbc:mysql://" + hostname + "/" + dbName + "?user=" + userName + "&password=" + password);
            PreparedStatement ptsmt = con.prepareStatement(insertFlag);
            insertFlag = "insert into " + symbol + end +" (datum, ticker, flag, number, depot) values " +
                    "(\'" + dateTrading + "\','" + bought +"'," + count + "," + money + ");";
            ptsmt.execute(insertFlag);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    // Buy and Hold Stragedy
    public void buyandHold (String symbol) throws SQLException {
        String flag = null;
        int anzahl = 0;
        int depot = (int) money;
        String endung = "bh";
        insertStartTrade(symbol,endung);
        System.out.println("Buy and Hold");
        for ( int i = 0; i<dateTradeList.size(); i++) {
            int rest = 0;
            flag = null;
            anzahl = 0;
            depot = 0;
            String sqlFlag = "select * from " + symbol + "bh order by datum desc limit 1";
            Connection con = null;
            try {
                con = DriverManager.getConnection("jdbc:mysql://" + hostname + "/" + dbName + "?user=" + userName + "&password=" + password);
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(sqlFlag);
                while (rs.next())
                {
                    flag = rs.getString("flag");
                    anzahl = rs.getInt("number");
                    depot = rs.getInt("depot");
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            } finally {
                con.close();
            }
            if(dateTradeList.get(i) == dateTradeList.get(0))
            {
                anzahl = (int) (depot / (closeTradeList.get(i)));
                rest = (int) (anzahl * closeTradeList.get(i));
                depot = (depot - rest);
                flag = "b";
                insertTradeIntoDB(symbol,(LocalDate) dateTradeList.get(i),bought,endung, count, money);
                //System.out.println("bought");
                //System.out.println(anzahl + " number of stocks");
            }
            else if(dateTradeList.get(i) == dateTradeList.get(dateTradeList.size()-1))
            {
                depot = (int) ((anzahl * closeTradeList.get(i)) + depot);
                flag = "s";
                anzahl = 0;
                insertTradeIntoDB(symbol,(LocalDate) dateTradeList.get(i),bought,endung, count, money);
                //System.out.println("sold");
                //System.out.println(depot + " money in depot");
            }
        }
        System.out.println(symbol);
        depot = (int) (depot - money);
        System.out.println(depot + " money in depot");
        System.out.println(((depot/money)*100.00) + " prozentueller Gewinn");
    }
    /*public void trading200With3(String symbol) throws SQLException {
        String flag = null;
        int anzahl = 0;
        int depot = 0;
        String endung = "trade3";
        insertStartTrade(endung);
        System.out.println("Trading with _200 plus 3%");
        for (int i = 0; i < dateTradeList.size(); i++) {
            int rest = 0;
            flag = null;
            anzahl = 0;
            depot = 0;
            String sqlFlag = "select * from " + stock + "trade3 order by datum desc limit 1";
            Connection con = null;
            try {
                con = DriverManager.getConnection("jdbc:mysql://" + hostname + "/" + dbName + "?user=" + userName + "&password=" + password);
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(sqlFlag);
                while (rs.next()) {
                    flag = rs.getString("flag");
                    anzahl = rs.getInt("number");
                    depot = rs.getInt("depot");
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
            finally {
                if(conn != null){
                    con.close();
                }
            }
            if (flag.equals("s")) {
                if (!dateTradeList.get(i).getDayOfWeek().equals(DayOfWeek.SATURDAY)
                        || (!dateTradeList.get(i).getDayOfWeek().equals(DayOfWeek.SUNDAY))) {
                    if ((closeTradeList.get(i)*1.03) > averageTradeList.get(i)) {
                        anzahl = (int) (depot / ((closeTradeList.get(i)*1.03)));
                        rest = (int) (anzahl * (closeTradeList.get(i)*1.03));
                        depot = (depot - rest);
                        flag = "b";
                        insertTradeIntoDB((LocalDate) dateTradeList.get(i), stock, endung, flag, anzahl, depot);
                        //System.out.println("bought");
                        //System.out.println(anzahl + " number of stocks");
                    }
                }
            } else if (flag.equals("b")) {
                if (!dateTradeList.get(i).getDayOfWeek().equals(DayOfWeek.SATURDAY)
                        || (!dateTradeList.get(i).getDayOfWeek().equals(DayOfWeek.SUNDAY))) {
                    if ((closeTradeList.get(i)*1.03) < averageTradeList.get(i)) {
                        depot = (int) ((anzahl * (closeTradeList.get(i)*1.03)) + depot);
                        flag = "s";
                        anzahl = 0;
                        insertTradeIntoDB((LocalDate) dateTradeList.get(i),stock, endung, flag, anzahl, depot);
                        //System.out.println("sold");
                        //System.out.println(depot + " money in depot");
                    }
                }
                {
                    double tempClose = closeValue.get(dateTradeList.size() - 1);
                    lastSale(tempClose, flag, depot, anzahl);
                    insertTradeIntoDB((LocalDate) dateTradeList.get(i), stock, endung, flag, anzahl, depot);
                }
            }
            else
            {
                System.out.println("Datenbankfehler");
            }
        }
        System.out.println(stock);
        depot = (int) (depot - startKapital);
        System.out.println(depot + " money in depot");
        System.out.println(((depot/startKapital)*100.00) + " prozentueller Gewinn");
    }*/
    public void lastSale(double core,String flag, int depot, int anzahl)
    {
        if(flag.equals("b"))
        {
            depot = (int) ((anzahl *core) + depot);
            flag = "s";
            anzahl = 0;
        }
    }
}
