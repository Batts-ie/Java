import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.*;

public class WebRequest {
    private String _url;
    public WebRequest(String url){ // CTOR
        this._url = url;
    }
    public JSONObject getWebRequest(int monthsString){
        String _requestString = _url + monthsString;
        HttpURLConnection con = null;

        try 
        {
            URL = url = new URL(_requestString);
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setUseCaches(false);
            con.setRequestProperty("Content-Type", "application/json; utf-8");
            /*weiterer Code folgt*/ 
        } 
        catch (Exception e) 
        {
            System.out.print("Der requestString kann entweder nicht gefunden werden, oder nicht geöffnet werden");
            e.printStackTrace();
        }
        return null;
    }
}