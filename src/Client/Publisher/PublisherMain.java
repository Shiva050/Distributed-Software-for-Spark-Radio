package src.Client.Publisher;


import java.util.HashMap;
import java.util.Map;
import src.Client.Utils.CommonUtils;

public class PublisherMain {
    
    static String user = "";
    static String role = "";

    public static void main(String[] args) {

        Map<String, Boolean> menuOptions = new HashMap<>();
        Publisher publisher = new Publisher();
        boolean loggedIn = false;

        menuOptions.put("Login", true);
        menuOptions.put("Write a Song", false);
        menuOptions.put("Delete a Song", false);
        menuOptions.put("Logout", false);
        menuOptions.put("Exit", true);

        int choice = CommonUtils.diaplayMenu(menuOptions);
        
        switch (choice) {
            case 1: 
                if (!loggedIn) {
                    loggedIn = CommonUtils.userLogIn(publisher, "publisher");
                    menuOptions.put("Login", false);
                    menuOptions.put("Write a Song", true);
                    menuOptions.put("Delete a Song", true);
                    menuOptions.put("Logout", true);
                }
        }
    }
}
