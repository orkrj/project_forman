package fourman.project1.exception.traffic;

public class TrafficAccessDeniedException extends RuntimeException{

    public TrafficAccessDeniedException() {
        super("Access denied for traffic data");
    }
}
