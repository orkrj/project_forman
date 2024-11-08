package fourman.project1.exception.traffic;

public class TrafficK6CmdErrorException extends RuntimeException{

    public TrafficK6CmdErrorException(Throwable cause) {
        super(cause);
    }

    public TrafficK6CmdErrorException(String message) {
        super(message);
    }


    public TrafficK6CmdErrorException(String message, Throwable cause) {
        super(message, cause);
    }
}
