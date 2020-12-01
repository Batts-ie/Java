
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.json.*;

public class WebRequest {

    private final String key="&apikey=69WF7TQHE667NCEK";
    private String requestString = "https://www.alphavantage.co/query?";
    private String function = "?function=";
    private String symbol = "&symbol=";

    private JSONObject Request(String urlString){


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

            try(BufferedReader br = new BufferedReader((new InputStreamReader(con.getInputStream(),StandardCharsets.UTF_8)))){
                StringBuilder response = new StringBuilder();
                String responseLine;
                while((responseLine = br.readLine()) != null){
                    response.append(responseLine.trim());
                }
                System.out.println(response.toString());
                return new JSONObject(response.toString());
            }


        }

         catch (Exception e) {
            System.out.println("Der requestString kann entweder nicht gefunden werden, oder nicht geöffnet werden");
            e.printStackTrace();
        }
        return null;
    }

    public JSONObject Builder(String fnct){
        /*weiterer Code*/
    }

}