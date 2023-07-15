package src.Client.Publisher;

public class PublisherMain {
    public static void main(String[] args) {
        Publisher publisher = new Publisher();
        if(publisher.userLogin().equalsIgnoreCase("Publisher")) {
            publisher.writeSong();
        } else {
            System.out.println("You are not authorized to publish the song");
        }
        
    }
}
