import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
public class Searcher{
    public int _monday;
    public int _tuesday;
    public int _wednesday;
    public int _thursday;
    public int _friday;

    public HashMap<String,Integer> searcher(List<LocalDate> listing)
    {
        for (int i = 0; i < listing.size(); i++) 
        {
            LocalDate tag = listing.get(i);
            switch (tag.getDayOfWeek()) {
                case MONDAY: _monday++; break;
                case TUESDAY: _tuesday++; break;
                case WEDNESDAY: _wednesday++; break;
                case THURSDAY: _thursday++; break;
                case FRIDAY: _friday++; break;
                case SATURDAY: break;
                case SUNDAY: break;
            }
        }
        HashMap<String,Integer> Wochentage = new HashMap<>();
        
        Wochentage.put("Montag", _monday);
        Wochentage.put("Dienstag", _tuesday);
        Wochentage.put("Mittwoch", _wednesday);
        Wochentage.put("Donnerstag", _thursday);
        Wochentage.put("Freitag", _friday);


        System.out.println("Montag: "+ _monday);
        System.out.println("Dienstag: "+ _tuesday);
        System.out.println("Mittwoch: "+ _wednesday);
        System.out.println("Donnerstag: "+ _thursday);
        System.out.println("Freitag: "+ _friday);
        
        return Wochentage;
    }
}
