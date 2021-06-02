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
import java.sql.SQLException;
import java.util.Scanner;


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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;


public class GUI extends Application{
    WebRequest wr = new WebRequest();
    ArrayList<String> stonks = new ArrayList<String>();

    @Override
    public void start(Stage s) throws IOException, JSONException, SQLException {
        /*DB Klasse, WebRequest - Abfrage vom Symbol*/

        wr.CreateSTM();
        readFile();
        for(int i = 0; i<stonks.size();i++) {
            String symbol = stonks.get(i);
            System.out.println(symbol);
            /*Insert from APIHandler Data*/
            //database.InsertStatement(ds.getDate(), symbol, ds.getValue());
            /*DB OUTPUT*/
            if (!check(symbol)) {
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
                wr.createTradingTable(symbol);
                wr.trading200(symbol);
                wr.buyandHold(symbol);
                wr.ListNull();
                wr.selectAll(symbol);

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
                    for (int k = 0; k < wr.arrayListClose.size(); k++) {
                        closeStat.getData().add(new XYChart.Data(wr.dateDB.get(k), wr.closeDB.get(k)));
                    }
                    XYChart.Series<String, Number> averageStat = new XYChart.Series();
                    averageStat.setName("moving average");
                    for (int j = 1; j < wr.arrayListAVG.size() - 1; j++) {
                        averageStat.getData().add(new XYChart.Data(wr.dateDB.get(j), wr.avgDB.get(j)));
                    }

                    Scene scene = new Scene(lineChart, 1000, 600);
                    lineChart.getData().add(closeStat);
                    lineChart.getData().add(averageStat);

                    lineChart.setCreateSymbols(false);
                    s.setScene(scene);
                    s.show();
                    saveAsPng(lineChart, "C:\\Users\\Michi\\Desktop\\Schule\\4AHWII\\SWP Java\\HTL-SWP_Java\\Aktien\\img\\chart-" + symbol +  "-stocks.png");
                    System.exit(0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    void readFile() throws FileNotFoundException
    {
        Scanner reader = new Scanner(new File ("C:\\Users\\Michi\\Desktop\\Schule\\4AHWII\\SWP Java\\HTL-SWP_Java\\Aktien\\src\\Stonks"));
        while(reader.hasNextLine())
        {
            stonks.add(reader.nextLine());
        }
    }
    public static boolean check (String symbol)
    {
        File file = new File ("C:\\Users\\Michi\\Desktop\\Schule\\4AHWII\\SWP Java\\HTL-SWP_Java\\Aktien\\img\\chart-" + symbol +  "-stocks.png");
        return file.exists();
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
