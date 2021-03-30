package com.example.funaccount.util;

public class Date {
  int mYear;
  int mMonth;
  int mDay;

  public Date(int year, int month, int day) {
    setYear(year);
    setMonth(month);
    setDay(day);
  }

  public int getYear() {
    return mYear;
  }

  public int getMonth() {
    return mMonth;
  }

  public void setYear(int mYear) {
    this.mYear = mYear;
  }

  public void setMonth(int mMonth) {
    this.mMonth = mMonth;
  }

  public void setDay(int mDay) {
    this.mDay = mDay;
  }

  public int getDay() {
    return mDay;
  }

  public String toString() {
    return String.valueOf(mYear) + "年" + String.valueOf(mMonth) + "月" + String.valueOf(mDay) + "日";
  }
}
