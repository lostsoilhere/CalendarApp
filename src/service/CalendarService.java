package service;

import exception.InvalidAppointmentException;
import exception.UserNotRegisteredException;
import model.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CalendarService {
    private final Map<Long, Calendar> calendarMap = new HashMap<>();

    public void setAvailability(final Long userId, final String emailId, final String name, final LocalTime startTime, final LocalTime endTime) {
        final Calendar calendar = calendarMap.computeIfAbsent(userId, k -> new Calendar(new User(name, emailId), new HashMap<>(), new Availability(startTime, endTime)));
        System.out.println("Availability set successfully");
    }

    public void bookAppointment(final Long userId,
                                final LocalDate date,
                                final LocalTime startTime,
                                final Integer durationInHours,
                                final String inviteeEmailId,
                                final String inviteeName,
                                final String comment) throws UserNotRegisteredException, InvalidAppointmentException {
        final Calendar calendar = calendarMap.getOrDefault(userId, null);
        validateUser(calendar);
        validateAppointment(calendar.getAvailability(), startTime, durationInHours);
        final List<Appointment> calendarAppointment = calendar.getCalenderAppointmentsMap().computeIfAbsent(date, k -> new ArrayList<Appointment>());
        // write the code to book and appointment
    }

    private void validateAppointment(Availability availability, LocalTime startTime, Integer durationInHours) throws InvalidAppointmentException {
        LocalTime endTime = startTime.plusHours(durationInHours);
        if(startTime.isBefore(availability.getStartTime()) || endTime.isAfter(availability.getEndTime())) throw new InvalidAppointmentException();
    }

    private void validateUser(Calendar calendar) throws UserNotRegisteredException{
        if(calendar == null) throw new UserNotRegisteredException();
    }

}
