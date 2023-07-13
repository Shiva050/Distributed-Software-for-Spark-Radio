package src.Client.Publisher;

public class PublisherMain {
    public static void Main(String[] args) {
        Publisher publisher = new Publisher();
        publisher.authentication();
        publisher.writeSong();
    }
}
