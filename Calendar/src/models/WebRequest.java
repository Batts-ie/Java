package models;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.JSONObject.*;

public class WebRequest {
    String _requestString = "https://deutsche-feiertage-api.de/api/v1";
    public void getWebRequest(){
        
        HttpURLConnection connection = null;
        //HttpClient client = HttpClient.newHttpClient();
        try {
            URL url = new URL(_requestString);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.setRequestProperty("Content-Type", "application/json; uft-8");
            connection.setRequestProperty("X-DFA-Token", "dfa");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);
            // sending request
            try (BufferedReader br = new BufferedReader(
                new InputStreamReader(connection.getInputStream(), "utf-8"))) {
                    StringBuilder response = new StringBuilder();
                    String responseLine = null;
                    while((responseLine = br.readLine()) != null){
                        response.append(responseLine.trim());
                    }
                    System.out.println(response.toString());
                    JSONObject = new JSONObject(response.toString());
                }

        } catch (Exception e) {
            System.out.println("Der requestString kann entweder nicht gefunden werden, oder nicht geöffnet werden");
            e.printStackTrace();
        }
    }
}