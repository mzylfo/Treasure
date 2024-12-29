package com.example.treasure.model;

public class Clock {
    public int year;
    public int month;
    public int day;
    public String dayWeek;
    public int hour;
    public int minute;
    public String timeZone;

    public Clock(int year, int month, int day, String dayWeek, int hour, int minute, String timeZone) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.dayWeek = dayWeek;
        this.hour = hour;
        this.minute = minute;
        this.timeZone = timeZone;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public String getDayWeek() {
        return dayWeek;
    }

    public void setDayWeek(String dayWeek) {
        this.dayWeek = dayWeek;
    }
}
