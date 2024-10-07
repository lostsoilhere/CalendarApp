package com.example.CalendarApp.service;

import com.example.CalendarApp.exception.IllegalSlotException;
import com.example.CalendarApp.exception.InvalidAppointmentException;
import com.example.CalendarApp.exception.UserNotRegisteredException;
import com.example.CalendarApp.model.Appointment;
import com.example.CalendarApp.model.Availability;
import com.example.CalendarApp.model.Calendar;
import com.example.CalendarApp.model.Invitation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class CalendarService {

    public void bookAppointment(final Calendar calendar,
                                final LocalDate date,
                                final LocalTime startTime,
                                final Invitation invitation) throws UserNotRegisteredException, InvalidAppointmentException, IllegalSlotException {
        final LocalTime endTime = startTime.plusHours(1);
        // validating that the request is valid
        validateUserCalendar(calendar);
        validateStartTime(startTime);
        validateAppointment(calendar.getAvailability(), startTime, endTime);

        final List<Appointment> calendarAppointments = calendar.getCalenderAppointmentsMap().computeIfAbsent(date, k -> new ArrayList<>());
        List<LocalTime> availableSlots = searchAvailableTimeSlots(calendar, date);
        if (!availableSlots.contains(startTime)) {
            throw new IllegalSlotException();
        }
        Appointment appointment = new Appointment(startTime, endTime, invitation);
        calendarAppointments.add(appointment);

    }

    private void validateStartTime(LocalTime startTime) throws IllegalSlotException {
        if (!isValidSlot(startTime)) {
            throw new IllegalSlotException();
        }
    }

    private void validateAppointment(Availability availability, LocalTime startTime, LocalTime endTime) throws InvalidAppointmentException {
        if(startTime.isBefore(availability.startTime()) || endTime.isAfter(availability.endTime())) throw new InvalidAppointmentException();
    }

    private void validateUserCalendar(Calendar calendar) throws UserNotRegisteredException {
        if(calendar == null) throw new UserNotRegisteredException();
    }

    public List<Appointment> showUpcomingAppointments(Calendar calendar, LocalDate date) throws UserNotRegisteredException, InvalidAppointmentException {
        validateUserCalendar(calendar);
        return calendar.getCalenderAppointmentsMap().getOrDefault(date, null);
    }

    public List<LocalTime> searchAvailableTimeSlots(Calendar calendar, LocalDate date) {
        validateUserCalendar(calendar);
        Availability availability = calendar.getAvailability();
        List<LocalTime> allSlots = generateAllSlots(availability.startTime(), availability.endTime());
        List<Appointment> appointments = calendar.getCalenderAppointmentsMap().get(date);

        return allSlots.stream()
                .filter(slot -> isSlotAvailable(slot, appointments))
                .toList();
    }

    private boolean isSlotAvailable(LocalTime slot, List<Appointment> appointments) {
        return Optional.ofNullable(appointments)
                .orElse(Collections.emptyList()) // Empty list if appointments is null
                .stream()
                .noneMatch(appointment -> appointment.getStartTime().equals(slot));
    }

    private List<LocalTime> generateAllSlots(LocalTime start, LocalTime end) {
        List<LocalTime> slots = new ArrayList<>();
        LocalTime current = start;
        while (current.isBefore(end)) {
            slots.add(current);
            current = current.plusHours(1);
        }
        return slots;
    }

    private boolean isValidSlot(LocalTime time) {
        return time.getMinute() == 0;
    }

}
