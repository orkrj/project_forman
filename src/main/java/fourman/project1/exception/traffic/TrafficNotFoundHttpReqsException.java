package fourman.project1.exception.traffic;

public class TrafficNotFoundHttpReqsException extends RuntimeException {

    public TrafficNotFoundHttpReqsException() {
        super("Traffic not found Http_reqs");
    }
}
