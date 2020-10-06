package models;

import java.util.Scanner;

public class menuApp {
    static Scanner Reader = new Scanner(System.in);

    public static void welcomeScreen() {
        System.out.println("Willkommen im Feiertagsrechner");
        System.out.println("Dieser Rechner könnte noch Fehler beinhalten, die aber behoben werden nach der Zeit");
    }

    public static char menuPool() {
        char choice;
        System.out.println("Was wollen Sie tun?: ");
        System.out.println("Suche der Feiertage starten [y]:");
        System.out.println("Suche abbrechen und das Programm beenden [x]: ");
        System.out.print("Ihre Wahl: ");
        choice = Reader.next().charAt(0);
        return choice;
    }

    public static void ThreadSleeping(int milliseconds) {

        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
