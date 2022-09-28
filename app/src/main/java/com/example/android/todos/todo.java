package com.example.android.todos;

public class todo {

    String newText;
    String reminderDate;
    String reminderTime;

    public todo( String newText, String date, String time) {

        this.newText = newText;
        this.reminderDate = date;
        this.reminderTime = time;
    }

    public todo(String newText) {
        this.newText = newText;
    }

    public todo() {
    }

    public String getNewText() {
        return newText;
    }

    public void setNewText(String newText) {
        this.newText = newText;
    }

    public String getReminderDate() {
        return reminderDate;
    }

    public void setReminderDate(String reminderDate) {
        this.reminderDate = reminderDate;
    }

    public String getReminderTime() {
        return reminderTime;
    }

    public void setReminderTime(String reminderTime) {
        this.reminderTime = reminderTime;
    }
}
