public class welcomeScreen {

    public static void welcomeOurGuest() {
        System.out.println("Hallo und damit Willkommen zum Feiertagsrechner");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            System.out.println("System konnte die Zeit nicht abwarten");
        }
    }
    
}
