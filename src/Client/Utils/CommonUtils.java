package src.Client.Utils;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Map.Entry;

import src.DataObject.SongObject;
import src.Servant.RadioSparkApp;

public class CommonUtils {

    public static String diaplayMenu(Map<String, Boolean> options) {
        int choice = 0;
        String option = null;

        System.out.println("Please choose one of the actions::");
        ArrayList<String> selectedMenus = new ArrayList<>();
        int i = 0;

        try {
            Iterator<Entry<String, Boolean> > menu_Iterator = options.entrySet().iterator();
            while (menu_Iterator.hasNext()) {
                i = selectedMenus.size();
                Map.Entry<String, Boolean> menu = menu_Iterator.next();
                if (menu.getValue()) {
                    selectedMenus.add(menu.getKey());
                    System.out.println((i+1) + ". " + menu.getKey());
                }
            }
        
            Scanner scanner = new Scanner(System.in);
            do {
                System.out.print("Enter your choice (1-" + selectedMenus.size() +  "):");
                choice = scanner.nextInt();
                option = selectedMenus.get(choice-1);
            } while (choice < 1 || choice > selectedMenus.size());
            
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        return option;
    }

    public static boolean userLogIn(RadioSparkApp servant, String calledRole) {
        
        boolean loggedIn = false;
        String user = "";
        String role = "";
        BufferedReader input;

        try {
            input = new BufferedReader(new InputStreamReader(System.in));
            while(!loggedIn) {
                Map<String, Object> loginData = null;
    
                System.out.print("Enter your username: ");
                String userName = input.readLine();
    
                System.out.print("Enter your password: ");
                String password = input.readLine();
    
                loginData = servant.userSignIn(userName, password);
                
                if(loginData!=null & loginData.containsKey("user") & loginData.containsKey("Role")) {
                    user = loginData.get("user").toString();
                    role = loginData.get("Role").toString().toLowerCase();
    
                    if (role.equals(calledRole)) {
                        System.out.println("\nHello " + user + ", welcome to the " + calledRole + " portal..");
                        loggedIn = true;
                    } else if (role.equals("not authenticated")) {
                        System.out.println("\nLogin attempt by the user " + user + " failed. Please try again...");
                    } else {
                        System.out.println("Sorry, you are not authorized to enter the " + calledRole + " portal. \nYou only have client access.");
                        break;
                    }
                } else {
                    System.out.println("Failed to retrieve login data..\nPlease try again");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return loggedIn;
    }

    public static void byteArraytoMp3(byte[] song, String songName) {
        String directoryPath = "../Database/PurchasedSongs/";
        String fileName = songName+".mp3";

        // Create the directory if it doesn't exist
        try {
            Path directory = Paths.get(directoryPath);
            if (!Files.exists(directory)) {
                Files.createDirectories(directory);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return;
        }

        // Write the byte array to an MP3 file
        try (FileOutputStream fos = new FileOutputStream(directoryPath + fileName)) {
            fos.write(song);
            System.out.println("\nSong purchased successfully.\nSong has been added into your database directory..\n");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void formatObject(Object songDetails) {
        String name = songDetails
    }
}