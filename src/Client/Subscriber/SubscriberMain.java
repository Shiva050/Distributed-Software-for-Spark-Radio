package src.Client.Subscriber;


import java.util.HashMap;
import java.util.Map;
import src.Client.Utils.CommonUtils;

public class SubscriberMain {

    static String option = "";

    public static void main(String[] args) {

        Map<String, Boolean> menuOptions = new HashMap<>();
        Subscriber subscriber = new Subscriber();
        boolean loggedIn = false;

        menuOptions.put("Login", true);
        menuOptions.put("Get the Song Details", false);
        menuOptions.put("Get the Songs collection", false);
        menuOptions.put("Purchase the Songs", false);
        menuOptions.put("Logout", false);
        menuOptions.put("Exit", true);

        // Display Menu and get the selcted option 
        option = CommonUtils.diaplayMenu(menuOptions);
        
        switch (option) {
            case "Login": 
                System.out.println("\nUser Login");
                loggedIn = subscriber.userLogin("subscriber");
                if(loggedIn) {
                    menuOptions.put("Login", false);
                    menuOptions.put("Get the Song Details", true);
                    menuOptions.put("Get the Songs collection", true);
                    menuOptions.put("Purchase the Songs", true);
                    menuOptions.put("Logout", true);
                    option = CommonUtils.diaplayMenu(menuOptions);
                } else {
                    System.out.println("Unable to Login....");
                }
                break;
        }
    }
}
