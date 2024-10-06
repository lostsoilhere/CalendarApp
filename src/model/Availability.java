package model;

import java.time.LocalTime;

public class Availability {
    private final LocalTime startTime;
    private final LocalTime endTime;
    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public Availability(LocalTime startTime, LocalTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
