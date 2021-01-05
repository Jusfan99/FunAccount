package com.example.funaccount.util;

public class Date {
    int year;
    int month;
    int day;
    public Date(int year, int month, int day) {
        setYear(year);
        setMonth(month);
        setDay(day);
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getDay() {
        return day;
    }

    public String toString() {
        return String.valueOf(year) + "年" + String.valueOf(month) + "月" + String.valueOf(day) + "日";
    }
}
