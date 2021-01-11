import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.*;

public class APIHandler
{
    static JSONObject json;
    String _symbol;
    LocalDate _time;
    double _close;
    int _volume;

    /*Bei TIME_SERIES_DAILY die Close Werte speichern*/

    /*private static double getValueClose(String key) throws JSONException
    {
        JSONObject jsonObject = (JSONObject) json.get(key);
        String value = jsonObject.getString("4. close");
        return Double.parseDouble(value);
    }*/
    // ctor
    public APIHandler(String symbol, LocalDate time, double close, int volume)
    {
        this._symbol = symbol; this._time = time; this._close = close; this._volume = volume;
    }
}
