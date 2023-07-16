package src.Client.Utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Map.Entry;

import src.Client.Publisher.Publisher;
public class CommonUtils {

    public static String diaplayMenu(Map<String, Boolean> options) {
        int choice = 0;
        String option = null;

        System.out.println("Menu:");
        ArrayList<String> selectedMenus = new ArrayList<>();
        int i = 0;

        try {
            Iterator<Entry<String, Boolean> > menu_Iterator = options.entrySet().iterator();
            while (menu_Iterator.hasNext()) {
                // i++;
                i = selectedMenus.size();
                Map.Entry<String, Boolean> menu = (Map.Entry<String, Boolean>) menu_Iterator.next();
                if (menu.getValue()) {
                    selectedMenus.add(menu.getKey());
                    System.out.println((i+1) + ". " + menu.getKey());
                }
            }
        
            Scanner scanner = new Scanner(System.in);
            do {
                System.out.println("Enter your choice (1-" + selectedMenus.size() +  "):");
                choice = scanner.nextInt();
                option = selectedMenus.get(choice);
                scanner.close();
            } while (choice < 1 || choice > selectedMenus.size());
        } catch(Exception e) {
            e.printStackTrace();
        }
        return option;
    }

    public static boolean userLogIn(Publisher publisher, String calledRole) {
        
        boolean loggedIn = false;
        String user = "";
        String role = "";

        while(!loggedIn) {
            Map<String, Object> loginData = publisher.userLogin();
            
            if(loginData!=null & loginData.containsKey("user") & loginData.containsKey("Role")) {
                user = loginData.get("user").toString();
                role = loginData.get("Role").toString().toLowerCase();

                if (role.equals(calledRole)) {
                    System.out.println("\nHello " + user + ", welcome to the publisher portal..");
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

        return loggedIn;
    }
}
