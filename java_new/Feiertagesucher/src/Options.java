import java.util.HashMap;
import java.util.Scanner;

public class Options{
    final Scanner Reader = new Scanner(System.in);
    Searcher searcher = new Searcher();
    Handler handler = new Handler();
    String url = "https://deutsche-feiertage-api.de/api/v1/";
    WebRequest request = new WebRequest(url);
    menuApp menu = new menuApp();

    public void Startup() {

        System.out.println("Was wollen Sie tun: ");
        System.out.println("Das Programm starten: [y]");
        System.out.println("Das Programm beenden: [x]");
        System.out.print("Geben Sie ihre Wahl ein: ");
        char wahl = Reader.next().charAt(0);

        switch (wahl) {
            case 'y':
                GUI gui = new GUI();
                HashMap<String, Integer> feiertage = searcher.searcher(handler.getLocalDates(request.getFeiertagObject(menu.maxDate(), menu.minDate())));
                //DB.Connection();
                gui.Dia(feiertage);
                break;
            case 'x':
                System.out.print("Ich wünsche einen schönen Tag");
                System.out.print(".");
                WaitASec(1000);
                System.out.print(".");
                WaitASec(1000);
                System.out.print(".");
                WaitASec(1000);
                System.exit(0);
                break;
            default:
                System.out.println("Falsche Taste gedrückt!");
                System.exit(0);
        }
    }

    public void WaitASec(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            System.out.println("Das System konnte die Zeit nicht abwarten");
        }
    }
    
}
