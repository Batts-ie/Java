import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.*;
import javafx.scene.image.WritableImage;
import javafx.stage.Stage;
import org.json.JSONException;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;


public class GUI extends Application{
    public Scanner Reader = new Scanner(System.in);
    WebRequest wr = new WebRequest();
   @Override
   public void start(Stage s) throws IOException, JSONException {
       /*DB Klasse, WebRequest - Abfrage vom Symbol*/


       wr.CreateSTM();

       System.out.print("Welche Aktie wollen Sie aufrufen [TSLA][AAPL][AMZN]: ");
       String symbol = Reader.next().toUpperCase();
       /*Insert from APIHandler Data*/
       //database.InsertStatement(ds.getDate(), symbol, ds.getValue());
       /*DB OUTPUT*/
       wr.GetCloseValues(symbol);
       wr.CreateSTM();
       wr.UseSTM();
       wr.CreateTable(symbol);
       wr.InsertStatementClose(symbol);
       wr.getSplit(symbol);
       wr.selectInsertSplit(symbol);
       wr.split(symbol);
       wr.update(symbol);
       wr.SelectAVGStatement(symbol);
       wr.InsertStatementAvg(symbol);
       System.out.print("Wollen Sie die Datenbank ausgeben?[y,n]: ");
       char choice = Reader.next().toLowerCase().charAt(0);
       if (choice == 'y') {
           wr.ListNull();
           wr.selectAll(symbol);
       }
       // JAVAFX:
       try {
           final CategoryAxis xAxis = new CategoryAxis();
           final NumberAxis yAxis = new NumberAxis();
           yAxis.setAutoRanging(false);


           yAxis.setLowerBound(wr.getLowerBound(symbol));
           yAxis.setUpperBound(wr.getUpperBound(symbol));

           xAxis.setLabel("date");
           yAxis.setLabel("close-value");
           final LineChart<String, Number> lineChart = new LineChart<String, Number>(xAxis, yAxis);
           lineChart.setTitle("stock-price " + symbol);
           XYChart.Series<String, Number> closeStat = new XYChart.Series();
           closeStat.setName("close-value");
           for (int i = 0; i < wr.arrayListClose.size(); i++) {
               closeStat.getData().add(new XYChart.Data(wr.dateDB.get(i), wr.closeDB.get(i)));
           }
           XYChart.Series<String, Number> averageStat = new XYChart.Series();
           averageStat.setName("moving average");
           for (int i = 1; i < wr.arrayListAVG.size() - 1; i++) {
               averageStat.getData().add(new XYChart.Data(wr.dateDB.get(i), wr.avgDB.get(i)));
           }

           Scene scene = new Scene(lineChart, 1000, 600);
           lineChart.getData().add(closeStat);
           lineChart.getData().add(averageStat);

           lineChart.setCreateSymbols(false);
           s.setScene(scene);
           s.show();
           saveAsPng(lineChart, "C:\\Users\\Michael\\Desktop\\Schule\\4AHWII\\HTL-SWP_Java\\HTL-SWP_Java\\Aktien\\img\\chart-" + symbol + "-stocks.png");

       } catch (Exception e) {
           e.printStackTrace();
       }
   }
    public void saveAsPng(LineChart lineChart, String path) {
        WritableImage image = lineChart.snapshot(new SnapshotParameters(), null);
        File file = new File(path);
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
   public static void main(String args[]){
       launch(args);
   }
}