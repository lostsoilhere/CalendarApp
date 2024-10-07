package com.example.CalendarApp.model;

public class Invitation {
    private final Invitee invitee;
    private final String comment;

    public Invitation(Invitee invitee, String comment) {
        this.invitee = invitee;
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Invitation{" +
                "invitee=" + invitee +
                ", comment='" + comment + '\'' +
                '}';
    }
}
