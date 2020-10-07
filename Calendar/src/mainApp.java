
public class mainApp {
    public static void main(String[] args){


        Searcher searcher = new Searcher();
        Handler handler = new Handler();    
        String url ="https://deutsche-feiertage-api.de/api/v1"; 
        WebRequest webRequest = new WebRequest(url);
        menuApp.welcomeScreen();
        menuApp.ThreadSleeping(2000);
            

        char c = menuApp.menuPool();

            switch (c) {
                case 'x':
                    System.out.println("Programm wird nun geschlossen ...");
                    System.exit(0);
                    break;
                case 'y':
                // searcher.searcher(handler.dateList(WebRequest.getFeiertagObject()));
                break;
                default:
                System.out.println("Programm schließt nun, da sie eine falsche Taste gedrückt haben!");
                System.exit(0);
                    break;
            }
    }
}
