package src.Client.Publisher;

import java.util.Map;

public class PublisherMain {
    
    static String user = "";
    static String role = "";

    public static void main(String[] args) {

        Publisher publisher = new Publisher();
        boolean loggedIn = false;

        while(!loggedIn) {
            Map<String, Object> loginData = publisher.userLogin();
            
            if(loginData!=null & loginData.containsKey("user") & loginData.containsKey("Role")) {
                user = loginData.get("user").toString();
                role = loginData.get("Role").toString().toLowerCase();

                if (role.equals("publisher")) {
                    System.out.println("\nHello " + user + ", welcome to the publisher portal..");
                    publisher.writeSong();
                    loggedIn = true;
                } else if (role.equals("not authenticated")) {
                    System.out.println("\nLogin attempt by the user " + user + " failed. Please try again...");
                } else {
                    System.out.println("Sorry, you are not authorized to enter the publish portal. \nYou only have client access.");
                    break;
                }
            } else {
                System.out.println("Failed to retrieve login data..\nPlease try again");
            }
        }
    }
}
