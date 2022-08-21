package com.example.android.todos;

public class todo {

    String newText;
    String reminderDate;
    String reminderTime;

    public todo(String newText, String date, String time) {
        this.newText = newText;
        this.reminderDate = date;
        this.reminderTime = time;
    }

    public todo(String newText) {
        this.newText = newText;
    }

    public todo() {
    }
}
