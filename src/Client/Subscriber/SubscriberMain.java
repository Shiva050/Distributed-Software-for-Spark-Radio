package src.Client.Subscriber;

import java.util.Map;

public class SubscriberMain {

    static String user = "";
    static String role = "";

    public static void main(String[] args) {

        Subscriber subscriber = new Subscriber();
        boolean loggedIn = false;

        while(!loggedIn) {
            Map<String, Object> loginData = subscriber.userLogin();
            
            if(loginData!=null & loginData.containsKey("user") & loginData.containsKey("Role")) {
                user = loginData.get("user").toString();
                role = loginData.get("Role").toString().toLowerCase();

                if (role.equals("client")) {
                    System.out.println("\nHello " + user + ", welcome to the client portal..");
                    // Operations
                    subscriber.searchSong();
                    loggedIn = true;
                } else if (role.equals("not authenticated")) {
                    System.out.println("\nLogin attempt by the user " + user + " failed. Please try again...");
                    break;
                } else {
                    System.out.println("Sorry, you are not authorized to enter the client portal. \nYou only have Publisher access.");
                }
            } else {
                System.out.println("Failed to retrieve login data..\nPlease try again");
            }
        }
    }
}