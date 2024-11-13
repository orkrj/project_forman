package fourman.project1.exception.user;

public class DeleteUserException extends Exception {

    public static class DeleteUserFailedException extends Throwable {
        public DeleteUserFailedException(String s, Exception e) {
            super(s, e);
        }
    }

}
