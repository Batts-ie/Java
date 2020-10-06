import models.*;

public class mainApp {
    public static void main(String[] args){
            menuApp.welcomeScreen();
            menuApp.ThreadSleeping(2000);
            

            char c = menuApp.menuPool();

            switch (c) {
                case 'x':
                    System.out.println("Programm wird nun geschlossen ...");
                    System.exit(0);
                    break;
                case 'y':
                // Code here
                break;
                default:
                System.out.println("Programm schließt nun, da sie eine falsche Taste gedrückt haben!");
                System.exit(0);
                    break;
            }
    }
}
