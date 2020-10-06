package models;

import java.time.LocalDateTime;

import java.util.ArrayList;

public class Searcher {
    
    
    public int _monday, _tuesday, _wednesday, _thursday, _friday;

    public void searcher(ArrayList listing)
    {
        for (int i = 0; i < listing.size(); i++) 
        {
            LocalDateTime tag = (LocalDateTime) listing.get(i);
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
        System.out.println("Montag: " + _monday);
        System.out.println("Dienstag: " + _tuesday);
        System.out.println("Mittwoch: " + _wednesday);
        System.out.println("Donnerstag: " + _thursday);
        System.out.println("Freitag: " + _friday);
    }

}
