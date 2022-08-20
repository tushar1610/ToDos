package com.example.android.todos;

public class todo {

    String newText;
    String reminder;

    public todo(String newText, String reminder) {
        this.newText = newText;
        this.reminder = reminder;
    }

    public todo(String newText) {
        this.newText = newText;
    }

    public todo() {
    }
}
