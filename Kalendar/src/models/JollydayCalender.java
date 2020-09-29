/*package models;
import de.jollyday.*;

/*import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Set;

public class JollydayCalender {

    public DayOfWeek cal(HolidayCalendar state, int years){
        years = Math.abs(years);
        // Array erstellen für die Tage
        Holiday[] holidays = new Holiday[]{};
        HolidayManager man = HolidayManager.getInstance(ManagerParameters.create(state));
        // von jetzt bis die Jahre, die oben als Parameter benötigt werden
        holidays = man.getHolidays(LocalDate.now(), LocalDate.now().plusYears(years)).toArray(new Holiday[0]);

        return DayOfWeek.SUNDAY;
    }
}*/
