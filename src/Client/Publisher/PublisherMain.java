package src.Client.Publisher;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import src.Client.Utils.CommonUtils;

public class PublisherMain {

    static String option = "";

    public static void main(String[] args) {

        Map<String, Boolean> menuOptions = new HashMap<>();
        Publisher publisher = new Publisher();
        boolean loggedIn = false;
        BufferedReader input;

        menuOptions.put("Login", true);
        menuOptions.put("Write a Song", false);
        menuOptions.put("Delete a Song", false);
        menuOptions.put("Retrieve songs", false);
        menuOptions.put("Logout", false);
        menuOptions.put("Exit", true);

        do {
            // Display Menu and get the selected option
            option = CommonUtils.diaplayMenu(menuOptions);

            switch (option) {
                case "Login":
                    System.out.println("\nUser Login");
                    loggedIn = publisher.userLogin("publisher");
                    if (loggedIn) {
                        menuOptions.put("Login", false);
                        menuOptions.put("Write a Song", true);
                        menuOptions.put("Delete a Song", true);
                        menuOptions.put("Retrieve songs", true);
                        menuOptions.put("Logout", true);
                    } else {
                        System.out.println("Unable to Login....");
                    }
                    break;

                case "Write a Song":
                    if (loggedIn) {
                        publisher.writeSong();
                    } else {
                        System.out.println("Sorry..you are not logged in. Please log in to write a song\n");
                    }
                    break;

                case "Delete a Song":
                    if (loggedIn) {
                        try {
                            input = new BufferedReader(new InputStreamReader(System.in));
                            System.out.print("Enter the name of the song you want to delete:");
                            String songName = input.readLine();
                            publisher.removeSong(songName);
                        } catch (IOException e) {
                            System.out.println(e.getMessage());
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    } else {
                        System.out.println("Sorry..you are not logged in. Please log in to delete a song\n");
                    }
                    break;

                case "Retrieve songs":
                    publisher.retrieveSongs();
                    break;

                case "Logout":
                    loggedIn = false;
                    publisher.userSignOut("Publisher");
                    menuOptions.put("Login", true);
                    menuOptions.put("Write a Song", false);
                    menuOptions.put("Delete a Song", false);
                    menuOptions.put("Retrieve songs", false);
                    menuOptions.put("Logout", false);
                    break;

                case "Exit":
                    System.out.println("\nThanks for Using SparkRadio....\n");
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid option");
                    break;
            }
        } while (true);
    }
}
