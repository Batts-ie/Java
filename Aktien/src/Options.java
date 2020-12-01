import java.util.HashMap;
import java.util.Scanner;

public class Options{
    final static Scanner Reader = new Scanner(System.in);

    public static void Startup(){

        System.out.println("Was wollen Sie tun?: ");
        System.out.println("Programm starten [y]: ");
        System.out.println("Programm schließen [y]: ");
        char wahl = Reader.next().toLowerCase().charAt(0);


        switch (wahl){
            case 'y':
                /*Programmcode*/
                break;
            case 'x':
                System.out.println("Ich wünsche einen schönen Tag! ");
                System.exit(0);
                break;
            default:
                System.out.println("Falsche Taste gedrückt");
                break;
        }
    }
}
