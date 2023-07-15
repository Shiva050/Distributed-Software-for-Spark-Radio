package src.Client.Subscriber;

public class SubscriberMain {
    public static void main(String[] args) {
        Subscriber subscriber = new Subscriber();
        if(subscriber.userLogIn().equalsIgnoreCase("Client")) {
            subscriber.searchSong();
        } else {
            System.out.println("You are not authorized to do any client operations");
        }
    }
    
}