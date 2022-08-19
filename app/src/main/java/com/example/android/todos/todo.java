package com.example.android.todos;

public class todo {

    String newText;
    boolean check;

    public todo(String newText, boolean check) {
        this.newText = newText;
        this.check = check;
    }

    public todo(String newText) {
        this.newText = newText;
    }

    public todo() {
    }
}
