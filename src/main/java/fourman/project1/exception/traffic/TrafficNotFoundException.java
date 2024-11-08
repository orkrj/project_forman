package fourman.project1.exception.traffic;

public class TrafficNotFoundException extends RuntimeException {

    public TrafficNotFoundException() {
        super("Traffic not found");
    }

    public TrafficNotFoundException(String message) {
        super(message);
    }
}
