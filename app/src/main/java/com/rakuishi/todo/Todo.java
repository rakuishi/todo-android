package com.rakuishi.todo;

/**
 * Created by rakuishi on 15/04/05.
 */
public class Todo {

    private String text;
    private boolean isChecked;
    private long timestamp;

    public Todo(String text) {
        this.text = text;
        this.isChecked = false;
        this.timestamp = System.currentTimeMillis() / 1000;
    }

    public Todo(String text, boolean isChecked) {
        this.text = text;
        this.isChecked = isChecked;
        this.timestamp = System.currentTimeMillis() / 1000;
    }

    public Todo(String text, boolean isChecked, int timestamp) {
        this.text = text;
        this.isChecked = isChecked;
        this.timestamp = timestamp;
    }

    public String getText() {
        return this.text;
    }

    public boolean getIsChecked() {
        return this.isChecked;
    }
}
