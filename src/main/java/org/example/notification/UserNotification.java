package org.example.notification;

public class UserNotification {
    private String email;
    private String operation;

    public UserNotification() {
    }

    public UserNotification(String email, String operation) {
        this.email = email;
        this.operation = operation;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public void sendEmail(String email, String s) {
    }
}
