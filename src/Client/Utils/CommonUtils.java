package src.Client.Utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Map.Entry;
import src.Servant.RadioSparkApp;

public class CommonUtils {

    private static final String FILE_PATH = "../Database/userList.txt";
    private static final String DELIMITER = " ";

    public static String diaplayMenu(Map<String, Boolean> options) {
        int choice = 0;
        String option = null;

        System.out.println("Please choose one of the actions:");
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

    public static void byteArraytoMp3(byte[] song, String songName, String directoryPath) {
        // String directoryPath = "../Database/PurchasedSongs/";
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
            if (!directoryPath.contains("MusicServer")) {
                System.out.println("\nSong purchased successfully.\nSong has been added into your database directory..\n");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void formatObject(Map<String, Object>  songDetails) {
        if (songDetails != null) {
            System.out.println("\nSong details:");
            System.out.println("Song Title: "+songDetails.get("Name"));
            System.out.println("Album: "+songDetails.get("Album"));
            System.out.println("Artist: "+songDetails.get("Artist"));
            System.out.println("Duration: "+songDetails.get("Duration"));
            System.out.println("Credits: "+songDetails.get("Credits") +"\n");    
        } else {
            System.out.println("Sorry, No song found with the name provided");
        }
    }

    public static void updateSubscriberCredits(String userName, int newCredits) {
        try {
            Map<String, String[]> creditsMap = readCreditsFromFile();
            if (creditsMap == null) {
                System.out.println("Failed to read credits from the file.");
                return;
            }

            boolean success = updateCredits(userName, newCredits, creditsMap);
            if (success) {
                if (writeCreditsToFile(creditsMap)) {
                    System.out.println("Credits updated and saved to the file successfully.");
                } else {
                    System.out.println("Failed to write credits to the file.");
                }
            } else {
                System.out.println("Failed to update credits for the user.");
            }
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }


    private static Map<String, String[]> readCreditsFromFile() {
        Map<String, String[]> creditsMap = new HashMap<>();

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("../Database/userList.txt"));
            reader.readLine();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] columns = line.split(" ");
                if (columns.length == 4) {
                    
                    String userName = columns[0].trim();
                    String password = columns[1].trim();
                    String role = columns[2].trim();
                    String credits = columns[3];
                    creditsMap.put(userName, new String[]{password, role, credits});
                } else {
                    System.out.println("Invaild line: " + line);
                }
            }
        } catch (IOException e) {
            System.out.print("File Reader: " + e.getMessage());
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch(IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        return creditsMap;
    }

    private static boolean updateCredits(String username, int newCredits, Map<String, String[]> creditsMap) {
        if (creditsMap.containsKey(username)) {
            creditsMap.put(username, new String[]{creditsMap.get(username)[0], creditsMap.get(username)[1], Integer.toString(newCredits)});
            return true;
        } else {
            System.out.println("User not found.");
            return false;
        }
    }

    private static boolean writeCreditsToFile(Map<String, String[]> creditsMap) {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(FILE_PATH));
            writer.write("Username Password Role Credits");
            for (Map.Entry<String, String[]> entry : creditsMap.entrySet()) {
                String username = entry.getKey();
                String[] values = entry.getValue();
                String line = username + DELIMITER;
            
                for (String value : values) {
                    line += value + DELIMITER;
                }
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing credits to the file: " + e.getMessage());
            return false;
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        return true;
    }
}