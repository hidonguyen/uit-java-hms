package vn.edu.uit.cntt.lt.e33.ie303.hms.util;

public class ApiException extends RuntimeException {
    
    private final Message error;

    public ApiException(String title, String code, String message) {
        super(message);
        this.error = new Message(title, code, message);
    }

    public ApiException(Message message) {
        super(message.getMessage());
        this.error = message;
    }

    public ApiException(Message message, Throwable cause) {
        super(message.getMessage(), cause);
        this.error = message;
    }

    public Message getError() {
        return error;
    }
}
