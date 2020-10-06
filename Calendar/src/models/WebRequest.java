package models;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class WebRequest {
    String _requestString = "https://deutsche-feiertage-api.de/api/v1";
    public void getWebRequest(){
        
        HttpURLConnection connection = null;
        try {
            URL url = new URL(_requestString);
            connection =(HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.setDoOutput(true);
            connection.setRequestProperty("", "X-DFA-Token: dfa");

            // sending a request
            DataOutputStream dos = new DataOutputStream(connection.getOutputStream());
            dos.writeBytes(null);
            dos.close();

        } catch (Exception e) {
            System.out.println("Der requestString kann entweder nicht gefunden werden, oder nicht geöffnet werden");
            e.printStackTrace();
        }
    }
}