package models;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.JSONObject.*;

public class WebRequest {

    private String _url;
    public WebRequest(String url) {
        this._url = url;
    }
    
    private JSONObject getWebRequest(int yearString){
        String _requestString = _url + yearString;
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
                    JSONObject obj = new JSONObject(response.toString());
                    return new JSONObject(response.toString());
                }

        } catch (Exception e) {
            System.out.println("Der requestString kann entweder nicht gefunden werden, oder nicht geöffnet werden");
            e.printStackTrace();
        }
    }
    public List<JSONObject> getFeirtagObject(String maxdate,String tstart)
    {
        //Start ist 2020
        int start = Integer.parseInt(tstart);
        List<JSONObject> yearListing = new ArrayList<>();
        for (int i = start; i <= Integer.parseInt(maxdate); i++) {
            yearListing.add(feiertagObject(i));
        }
        return yearListing;
    }
}