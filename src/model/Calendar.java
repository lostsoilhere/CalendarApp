package model;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class Calendar {
    private final User user;

    private Availability availability;
    private final Map<LocalDate, List<Appointment>> calenderAppointmentsMap;

    public Map<LocalDate, List<Appointment>> getCalenderAppointmentsMap() {
        return calenderAppointmentsMap;
    }

    public Availability getAvailability() {
        return availability;
    }

    public void setAvailability(Availability availability) {
        this.availability = availability;
    }

    public Calendar(User user, Map<LocalDate, List<Appointment>> calenderAppointmentsMap, Availability availability) {
        this.user = user;
        this.calenderAppointmentsMap = calenderAppointmentsMap;
        this.availability = availability;
    }
}