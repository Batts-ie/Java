import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.*;
import javafx.stage.Stage;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GUI extends Application{
    public Scanner Reader = new Scanner(System.in);
   @Override
   public void start(Stage s)
   {
       /*DB Klasse, WebRequest - Abfrage vom Symbol*/
       DB database = new DB();
       DS ds = new DS();
       WebRequest wr = new WebRequest();
       System.out.print("Welche Aktie wollen Sie aufrufen [TSLA][AAPL][AMZN]: ");
       String symbol = Reader.next().toUpperCase();
       database.CreateTable(symbol);

       /*Insert from APIHandler Data*/
       database.InsertStatement(ds.getDate(), symbol, ds.getValue());
       /*DB OUTPUT*/
       System.out.print("Wollen Sie die Datenbank ausgeben?[y,n]: ");
       char choice = Reader.next().toLowerCase().charAt(0);
       if(choice == 'y') database.SelectStatement(symbol);

       Dia(s, "Date", "Value", symbol, wr.GetCloseValues(wr.Request(wr.StringBuilder(symbol))));

   }
   public void Dia(Stage s, String xLabel, String yLabel, String symbol, ArrayList<DS>data)
   {
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel(xLabel);
        yAxis.setLabel(yLabel);
        final LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle(symbol);
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        Scene scene = new Scene(lineChart, 1080, 720);
        s.setScene(scene);
        for (DS d : data)
        {
            series.getData().add(new XYChart.Data<>(d.getDate(), d.getValue()));
        }
        lineChart.getData().add(series);
        s.show();
   }
   public static void main(String args[]){
      launch(args);
   }
}