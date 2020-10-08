  
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.json.*;


public class Handler {

    public List<LocalDate> getLocalDates(List<JSONObject> jsonyearList)
    {
        try {
            List<LocalDate> dateList = new ArrayList<>();
            for (int i = 0; i < jsonyearList.size(); i++) {
                JSONObject obj = jsonyearList.get(i);
                System.out.println("Loading... "+obj.getString("message"));
                JSONArray array = obj.getJSONArray("holidays");
                for (int j = 0; j < array.length(); j++)
                {
                  LocalDate date = getDateofJSON(array.getJSONObject(j));
                  dateList.add(date);
                }           
            }
            return dateList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
        
    }
    public LocalDate getDateofJSON(JSONObject obj)
    {
        try {
            JSONObject obj2 = obj.getJSONObject("holiday");
            return LocalDate.parse(obj2.getString("date"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }
}