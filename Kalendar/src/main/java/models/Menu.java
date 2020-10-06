package models;

import java.util.Scanner;

public class Menu {

    public static Scanner Reader = new Scanner(System.in);

    public static void main(String[] args) {

        char wahl = SelectionPool();

        switch (wahl){
            case 'y':
                /*case 'O':
                    //execution of O here
                    break;
                case 'D' :
                    //execution of D here
                    break;
                case 'S':
                    //execution of S here
                    break;*/
                break;
            case 'x':
                System.out.println("Programm wird wieder geschlossen, schönen Tag noch! ");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    System.out.println("Programm konnte keine 2 sec warten");
                }
                System.exit(0);
                break;
            default:
                System.out.println("Falsche Taste betätigt, bitte das Programm neustarten und aufpassen");
        }

    }
    public static char SelectionPool(){
        char c;
        System.out.println("Was wollen Sie tun?: ");
        System.out.println("Öffne Kalender [y]: ");
        System.out.println("Schließen [x]: ");
        c = Reader.next().charAt(0);
        return c;
    }
   /* public static char CountryPool(){
        char c;
        System.out.println("Geben Sie ein Land ein, aber aufpassen, Buchstaben müssen groß sein: ");
        System.out.println("Österreich [O]: ");
        System.out.println("Deutschland [D]: ");
        System.out.println("Schweiz [S]: ");
        c = Reader.next().charAt(0);
        return c;
    }*/

}
