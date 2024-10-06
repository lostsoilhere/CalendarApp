package service;

import exception.IllegalSlotException;
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
        System.out.println("Availability set successfully\n");
    }

    public void bookAppointment(final Long userId,
                                final LocalDate date,
                                final LocalTime startTime,
                                final String inviteeEmailId,
                                final String inviteeName,
                                final String comment) throws UserNotRegisteredException, InvalidAppointmentException, IllegalSlotException {
        final Calendar calendar = calendarMap.getOrDefault(userId, null);
        final LocalTime endTime = startTime.plusHours(1);
        // validating that the request is valid
        validateUser(calendar);
        validateStartTime(startTime);
        validateAppointment(calendar.getAvailability(), startTime, endTime);

        final List<Appointment> calendarAppointments = calendar.getCalenderAppointmentsMap().computeIfAbsent(date, k -> new ArrayList<Appointment>());
        List<LocalTime> availableSlots = searchAvailableTimeSlots(userId, date);
        if (!availableSlots.contains(startTime)) {
            throw new IllegalSlotException();
        }
        final Invitee invitee = new Invitee(inviteeName, inviteeEmailId);
        final Invitation invitation = new Invitation(invitee, comment);
        Appointment appointment = new Appointment(startTime, endTime, invitation);
        System.out.println(appointment);
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

    private void validateUser(Calendar calendar) throws UserNotRegisteredException{
        if(calendar == null) throw new UserNotRegisteredException();
    }

    public void showUpcomingAppointments(Long userId, LocalDate date) throws UserNotRegisteredException, InvalidAppointmentException {
        final Calendar calendar = calendarMap.getOrDefault(userId, null);
        validateUser(calendar);
        final List<Appointment> appointments = calendar.getCalenderAppointmentsMap().getOrDefault(date, null);
        if(appointments == null) throw  new InvalidAppointmentException();
        appointments.forEach(System.out::println);
    }

    public List<LocalTime> searchAvailableTimeSlots(Long userId, LocalDate date) {
        final Calendar calendar = calendarMap.getOrDefault(userId, null);
        validateUser(calendar);
        Availability availability = calendar.getAvailability();
        List<LocalTime> allSlots = generateAllSlots(availability.startTime(), availability.endTime());
        List<Appointment> appointments = calendar.getCalenderAppointmentsMap().get(date);

        List<LocalTime> slots = allSlots.stream()
                .filter(slot -> isSlotAvailable(slot, appointments))
                .toList();
        slots.forEach(System.out::println);
        return slots;
    }

    private boolean isSlotAvailable(LocalTime slot, List<Appointment> appointments) {
        return appointments.stream()
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
