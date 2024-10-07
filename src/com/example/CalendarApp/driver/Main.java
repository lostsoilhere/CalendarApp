package com.example.CalendarApp.driver;

import com.example.CalendarApp.exception.IllegalSlotException;
import com.example.CalendarApp.model.Calendar;
import com.example.CalendarApp.model.*;
import com.example.CalendarApp.service.CalendarService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class Main {
    private static final Map<Long, Calendar> calendarMap = new HashMap<>();
    public static void main(String[] args) throws IllegalSlotException {
        Scanner scanner = new Scanner(System.in);
        CalendarService calendarService = new CalendarService();

        while (true) {
            System.out.println("1. Set Availability");
            System.out.println("2. Search Available Time Slots");
            System.out.println("3. Book Appointment");
            System.out.println("4. List Upcoming Appointments");
            System.out.println("5. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline

            switch (choice) {
                case 1:
                    setAvailability(scanner);
                    break;
                case 2:
                    searchAvailableTimeSlots(scanner, calendarService);
                    break;
                case 3:
                    bookAppointment(scanner, calendarService);
                    break;
                case 4:
                    listUpcomingAppointments(scanner, calendarService);
                    break;
                case 5:
                    System.out.println("Exiting...");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static User registerUser(Scanner scanner) {
        System.out.print("Enter Calendar Owner name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Calendar Owner email: ");
        String emailId = scanner.nextLine();
        return new User(name, emailId);
    }

    private static void setAvailability(Scanner scanner) {
        System.out.print("Enter Calendar Owner ID: ");
        Long id = scanner.nextLong();
        scanner.nextLine();
        System.out.print("Enter the start time for the Calendar Owner Availability (in HH:MM format) : ");
        String startTimeString = scanner.nextLine();
        LocalTime startTime = LocalTime.parse(startTimeString);
        System.out.print("Enter the end time for the Calendar Owner Availability (in HH:MM format) : ");
        String endTimeString = scanner.nextLine();
        LocalTime endTime = LocalTime.parse(endTimeString);
        Availability availability = new Availability(startTime, endTime);
        Calendar calendar = calendarMap.getOrDefault(id, null);
        if(calendar == null) {
            System.out.println("The user calendar doesn't exist in the system, please register the user");
            User user = registerUser(scanner);
            calendar = new Calendar(user, new HashMap<>(), availability);
            calendarMap.put(id, calendar);
        }
        else {
            calendar.setAvailability(availability);
        }

        System.out.println("Availability set for :" + calendar.getUser().toString());

    }

    private static void searchAvailableTimeSlots(Scanner scanner, CalendarService calendarService) {
        // get user calendar
        Calendar calendar = getUserCalendar(scanner);

        // get the date to search for available slots
        System.out.print("Enter the date to search time slots (in YYYY-MM-DD format) : ");
        String dateString = scanner.nextLine();
        LocalDate date = LocalDate.parse(dateString);

        // use calendar service to search for available time slots
        calendarService.searchAvailableTimeSlots(calendar, date).forEach(System.out::println);
    }

    private static Calendar getUserCalendar(Scanner scanner) {
        System.out.print("Enter Calendar Owner ID: ");
        Long id = scanner.nextLong();
        scanner.nextLine();
        return calendarMap.getOrDefault(id, null);
    }

    private static void bookAppointment(Scanner scanner, CalendarService calendarService) throws IllegalSlotException {
        // get user calendar
        Calendar calendar = getUserCalendar(scanner);

        // get the date for the appointment
        System.out.print("Enter the date for the appointment (in YYYY-MM-DD format) : ");
        String dateString = scanner.nextLine();
        LocalDate date = LocalDate.parse(dateString);

        // get the start time for the meeting
        System.out.print("Enter the start time for the meeting (in HH:MM format) : ");
        String startTimeString = scanner.nextLine();
        LocalTime startTime = LocalTime.parse(startTimeString);

        // get details of the invitee
        System.out.print("Enter Invitee name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Invitee email: ");
        String emailId = scanner.nextLine();
        System.out.print("Enter optional comment for the meeting (press enter if you don't want to add a comment): ");
        String comment = scanner.nextLine();

        Invitation invitation = new Invitation(new Invitee(name, emailId), comment);

        // use CalendarService to book appointment
        calendarService.bookAppointment(calendar, date, startTime, invitation);
    }

    private static void listUpcomingAppointments(Scanner scanner, CalendarService calendarService) {
        // get user calendar
        Calendar calendar = getUserCalendar(scanner);
        // use CalendarService to print upcoming booking.
        List<Appointment> appointments = calendarService.showUpcomingAppointments(calendar, LocalDate.now());
        Optional.ofNullable(appointments)
                .orElse(Collections.emptyList())
                .forEach(System.out::println);
        if(appointments == null || appointments.isEmpty()) System.out.println("No upcoming appointment for the user");
    }
}