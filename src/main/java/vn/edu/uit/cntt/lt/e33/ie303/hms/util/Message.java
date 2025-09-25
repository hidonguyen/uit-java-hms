package vn.edu.uit.cntt.lt.e33.ie303.hms.util;

public class Message {
    private String title;
    private String code;
    private String message;

    public Message() {}

    public Message(String title, String code, String message) {
        this.title = title;
        this.code = code;
        this.message = message;
    }

    public String getTitle() {
        return title;
    }

    public Message setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getCode() {
        return code;
    }

    public Message setCode(String code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public Message setMessage(String message) {
        this.message = message;
        return this;
    }

    

}
