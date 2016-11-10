package edu.classm8web.exception;

public class DatabaseException extends Exception {
    private StringBuilder message;

    private DatabaseException() {
    }

    public DatabaseException(StringBuilder message) {
        this.message = message;
    }
    public DatabaseException(String message) {
        this.message = new StringBuilder(message);
    }

    @Override
    public String getMessage() {
        return message.toString();
    }

    public void setMessage(StringBuilder message) {
        this.message = message;
    }
    public void setMessage(String message){
        this.message = new StringBuilder(message);
    }
    public  void addToMessage(String message){
        this.message.append(message+ ";");
    }


}
