package com.example.CalendarApp.model;

import java.time.LocalTime;

public record Availability(LocalTime startTime, LocalTime endTime) {
}
