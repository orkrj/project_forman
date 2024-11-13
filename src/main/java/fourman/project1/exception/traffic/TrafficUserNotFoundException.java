package fourman.project1.exception.traffic;

public class TrafficUserNotFoundException extends RuntimeException{

    public TrafficUserNotFoundException() {
        super("User not found for the requested traffic test");
    }
}
