package models;

import java.net.HttpURLConnection;
import java.net.URL;

public class WebRequest {

    public void getWebRequest(){
        String requestString = "";
        HttpURLConnection connection = null;
        try {
            URL url = new URL(requestString);
            connection =(HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.setDoOutput(true);

        } catch (Exception e) {
            System.out.println("Der requestString kann entweder nicht gefunden werden, oder nicht geöffnet werden");
            e.printStackTrace();
        }
    }
}