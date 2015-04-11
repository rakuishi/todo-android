package com.rakuishi.todo;

import android.util.Log;

import io.realm.RealmObject;

/**
 * Created by rakuishi on 15/04/05.
 */
public class Todo extends RealmObject {

    private String name;
    private boolean completed;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
