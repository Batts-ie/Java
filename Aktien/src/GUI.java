import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.*;
import javafx.scene.chart.XYChart;
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
       WebRequest wr = new WebRequest();
       System.out.println("Welche Aktie wollen Sie aufrufen [TSLA][AAPL]: ");
       String symbol = Reader.next();
       database.CreateTable(symbol);
       /*Insert from WebRequest Data*/


   }
   public void Dia(){

   }
   public static void main(String args[]){
      launch(args);
   }
}