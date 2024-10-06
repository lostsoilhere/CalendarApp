package model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Appointment {
    private LocalTime startTime;
    private LocalTime endTime;
    private Invitation invitation;

    public Appointment(LocalTime startTime, LocalTime endTime, Invitation invitation) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.invitation = invitation;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "startTime=" + startTime +
                ", endTime=" + endTime +
                ", invitation=" + invitation +
                '}';
    }
}
