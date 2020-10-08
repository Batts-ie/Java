import java.util.HashMap;

import java.util.Scanner;


public class menuApp{

    Scanner Reader = new Scanner(System.in);

    private HashMap<String,String> calChoice = new HashMap<String,String>();
    private void fillCal()
    {
       calChoice.put("AUT", "de.austrian#holiday@group.v.calendar.google.com");
       calChoice.put("DE", "de.german#holiday@group.v.calendar.google.com");
    }
    public menuApp(){
        fillCal();
    }
    public String countryOptions()
    {
        String[] options = {"Österreich", "Deutschland"};
        String c;
        System.out.print("Landoptionen: ");
        for (int i = 0; i < options.length; i++) {
            System.out.print(" " + options[i] + ",");
        }
        System.out.println();
        switch (Reader.next()) {
            case "Österreich": c = "AUT";break;
            case "Deutschland": c = "DE";break;
            default: c = "AUT";
        }
        Reader.close();
        return calChoice.get(c);
    }
    public String maxDate()
    {
        System.out.print("Gebe das max. Jahr in Format <yyyy> ein:");
        String date = Reader.next();
        return date;
    }
    public String minDate()
    {
        System.out.print("Gebe das min. Jahr in Format <yyyy> ein:");
        String date = Reader.next();
        Reader.close();
        return date;
    }

    

}