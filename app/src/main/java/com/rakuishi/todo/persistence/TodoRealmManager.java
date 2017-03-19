package com.rakuishi.todo.persistence;

import android.content.Context;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class TodoRealmManager implements TodoManager {

    private Realm realm;

    public TodoRealmManager(Context context) {
        realm = Realm.getInstance(context);
    }

    @Override
    public Todo find(int id) {
        return realm.where(Todo.class).equalTo("id", id).findFirst();
    }

    @Override
    public List<Todo> findAll() {
        RealmResults<Todo> results = realm.where(Todo.class).findAll();
        return results.subList(0, results.size());
    }

    @Override
    public void insert(String name, boolean completed) {
        realm.beginTransaction();
        Todo todo = realm.createObject(Todo.class);
        todo.setId((int) realm.where(Todo.class).maximumInt("id") + 1);
        todo.setName(name);
        todo.setCompleted(completed);
        realm.commitTransaction();
    }

    @Override
    public void update(int id, String name, boolean completed) {
        realm.beginTransaction();
        Todo todo = find(id);
        todo.setName(name);
        todo.setCompleted(completed);
        realm.commitTransaction();
    }

    @Override
    public void update(Todo todo, String name) {
        realm.beginTransaction();
        todo = realm.copyToRealm(todo);
        todo.setName(name);
        realm.commitTransaction();
    }

    @Override
    public void update(Todo todo, boolean completed) {
        realm.beginTransaction();
        todo.setCompleted(completed);
        realm.commitTransaction();
    }

    @Override
    public void deleteCompleted() {
        realm.beginTransaction();
        RealmResults<Todo> results = realm.where(Todo.class).equalTo("completed", true).findAll();
        results.clear();
        realm.commitTransaction();
    }
}
