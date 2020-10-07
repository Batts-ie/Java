import org.json.*;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

public class Handler {

    public List<LocalDate> getLocalDates(List<JSONObject> jsonYearList){

        try {
            List<LocalDate> dateList = new ArrayList<>();
            for (int i = 0; i < jsonyearList.size(); i++) {
                JSONObject obj = jsonyearList.get(i);
                System.out.println("Loading... " + obj.getString("message"));
                JSONArray arr= obj.getJSONArray("holidays");
                for (int j = 0; j < arr.length(); j++)
                {
                  LocalDate localDate = getDateofJSON(arr.getJSONObject(j));
                  dateList.add(localDate);
                }                        
            }
            return dList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public LocalDate getDate(JSONObject obj){

        try 
        { 
            JSONObject obj2 = obj.getJSONObject("holiday");
            return LocalDate.parse(obj2.getString("date"));
        }
        catch (JSONException e){
            e.printStackTrace();
        }

        return null;
    }
    
}
