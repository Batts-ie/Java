import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.json.*;

public class APIHandler {

    String _symbol;
    LocalDate _time;
    double _close;
    int _volume;

    /*Bei TIME_SERIES_DAILY die Close und Volume Werte speichern*/

    public APIHandler(String symbol, LocalDate time, double close, int volume)
    {
        this._symbol = symbol; this._time = time; this._close = close; this._volume = volume;
    }


}
