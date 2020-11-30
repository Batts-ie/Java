import java.util.Scanner;

import java.util.Scanner;

public class Options
{
    final Scanner Reader = new Scanner(System.in);
    /*ClassImports*/

    /*Code*/
    String url = "https://www.alphavantage.co/query?function=TIME_SERIES_MONTHLY_ADJUSTED&symbol=IBM&apikey=69WF7TQHE667NCEK";
    WebRequest request = new WebRequest(url);

    public void Startup()
    {
        System.out.println("Was wollen Sie tun?");
        System.out.println("Das Programm starten[y]:");
        System.out.println("Das Programm schließen[x]:");
        System.out.println("Auswahl angeben: ");
        char wahl = Reader.next().charAt(0);

        switch (wahl) {
            case 'x':
                System.out.print("Ok Chef, das Programm wird beendet! \n");
                System.exit(1);
                break;
            case 'y':
                /*Code*/
                break;
        
            default:
            System.out.print("Falsche Taste betätigt! \n");
            System.exit(0);
                break;
        }
    }
}