package com.example.CalendarApp.model;

public class Invitee {
    String name;
    String emailId;

    public Invitee(String name, String emailId) {
        this.name = name;
        this.emailId = emailId;
    }

    @Override
    public String toString() {
        return "Invitee{" +
                "name='" + name + '\'' +
                ", emailId='" + emailId + '\'' +
                '}';
    }
}
