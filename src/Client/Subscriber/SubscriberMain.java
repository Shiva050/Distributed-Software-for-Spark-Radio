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
        menuOptions.put("Get song details", false);
        menuOptions.put("Purchase Song", false);
        menuOptions.put("Retrieve songs", false);
        menuOptions.put("Look up song", false);
        menuOptions.put("Logout", false);
        menuOptions.put("Exit", true);

        do {
            // Display Menu and get the selected option
            option = CommonUtils.diaplayMenu(menuOptions);

            switch (option) {
                case "Login":
                    System.out.println("\nUser Login");
                    loggedIn = subscriber.userLogin("subscriber");
                    if (loggedIn) {
                        menuOptions.put("Login", false);
                        menuOptions.put("Get the song details", true);
                        menuOptions.put("Purchase Song", true);
                        menuOptions.put("Retrieve songs", true);
                        menuOptions.put("Look up song", true);
                        menuOptions.put("Logout", true);
                    } else {
                        System.out.println("Unable to Login....");
                    }
                    break;

                case "Get the song details":
                    if (loggedIn) {
                        subscriber.getSongDetails();
                    } else {
                        System.out.println("Sorry..you are not logged in. Please log in to write a song\n");
                    }
                    break;

                case "Retrieve songs":
                    subscriber.retrieveSongs();
                    break;

                case "Logout":
                    loggedIn = false;
                    subscriber.userSignOut();
                    menuOptions.put("Login", true);
                    menuOptions.put("Get the song details", false);
                    menuOptions.put("Purchase Song", false);
                    menuOptions.put("Retrieve songs", false);
                    menuOptions.put("Look up song", false);
                    menuOptions.put("Logout", false);
                    break;

                case "Purchase Song":
                    subscriber.purchaseSong();
                    break;

                case "Look up song":
                    subscriber.lookUpSong();
                    break;

                case "Exit":
                    System.out.println("\nThanks for Using SparkRadio....\nHave a good day :)\n");
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid option");
                    break;
            }
        } while (true);
    }
}
