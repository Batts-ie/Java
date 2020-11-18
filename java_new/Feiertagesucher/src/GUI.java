import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.*;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GUI extends Application{
   Searcher searcher = new Searcher();
   //private HashMap<String,Integer> feiertage = new HashMap<>();
   WebRequest request = new WebRequest("https://deutsche-feiertage-api.de/api/v1/");
   @Override
   public void start(Stage stage) {
      Options options = new Options();
      welcomeScreen.welcomeOurGuest();
      options.Startup();
      /* try {
         // Angeben wie die Achsen sein sollen
         final CategoryAxis xAxis = new CategoryAxis();
         final NumberAxis yAxis = new NumberAxis();
         final BarChart<String, Number> barChart = new BarChart<String, Number>(xAxis, yAxis);
         _wochentage.add("Montag");
         _wochentage.add("Dienstag");
         _wochentage.add("Mittwoch");
         _wochentage.add("Donnerstag");
         _wochentage.add("Freitag");
         barChart.setTitle("Anzahl der Feiertage pro Woche");
         xAxis.setLabel("Wochentage");
         yAxis.setLabel("Anzahl");
         XYChart.Series series1 = new XYChart.Series();
         for (int i = 0 ;i<_wochentage.size();i++)
         {
            series1.getData().add(new XYChart.Data(_wochentage.get(i),feiertage.get(_wochentage.get(i))));
         }

         Scene scene = new Scene(barChart, 960, 540);
         barChart.getData().addAll(series1);
         stage.setScene(scene);
         stage.show();
      } catch(Exception e) {
         e.printStackTrace();
      }*/

   }
   public void Dia(HashMap<String,Integer> feiertage){
      try {
         Stage stage = new Stage();
         List<String> _wochentage = new ArrayList<String>();
         // Angeben wie die Achsen sein sollen
         final CategoryAxis xAxis = new CategoryAxis();
         final NumberAxis yAxis = new NumberAxis();
         final BarChart<String, Number> barChart = new BarChart<String, Number>(xAxis, yAxis);
         _wochentage.add("Montag");
         _wochentage.add("Dienstag");
         _wochentage.add("Mittwoch");
         _wochentage.add("Donnerstag");
         _wochentage.add("Freitag");
         barChart.setTitle("Anzahl der Feiertage");
         xAxis.setLabel("Wochentage");
         yAxis.setLabel("Anzahl");
         XYChart.Series series1 = new XYChart.Series();
         for (int i = 0 ;i<_wochentage.size();i++)
         {
            series1.getData().add(new XYChart.Data(_wochentage.get(i),feiertage.get(_wochentage.get(i))));
         }

         Scene scene = new Scene(barChart, 960, 540);
         barChart.getData().addAll(series1);
         stage.setScene(scene);
         stage.show();
      } catch(Exception e) {
         e.printStackTrace();
      }
   }
   public static void main(String args[]){
      launch(args);
   }
}