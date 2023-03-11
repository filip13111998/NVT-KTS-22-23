package ftn.uns.ac.rs.NVTKTS20222023.exception;

public class TwoPasswordsNotSameException extends RuntimeException{

    public TwoPasswordsNotSameException() {
        super();
    }
    public TwoPasswordsNotSameException(String message, Throwable cause) {
        super(message, cause);
    }
    public TwoPasswordsNotSameException(String message) {
        super(message);
    }
    public TwoPasswordsNotSameException(Throwable cause) {
        super(cause);
    }

}
