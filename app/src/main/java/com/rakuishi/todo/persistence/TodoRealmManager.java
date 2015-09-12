package com.rakuishi.todo.persistence;

import android.content.Context;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class TodoRealmManager implements TodoManager {

    private Realm mRealm;

    public TodoRealmManager(Context context) {
        mRealm = Realm.getInstance(context);
    }

    @Override
    public Todo find(int id) {
        return mRealm.where(Todo.class).equalTo("id", id).findAll().first();
    }

    @Override
    public List<Todo> findAll() {
        RealmResults<Todo> results = mRealm.where(Todo.class).findAll();
        return results.subList(0, results.size());
    }

    @Override
    public void insert(String name, boolean completed) {
        mRealm.beginTransaction();
        Todo todo = mRealm.createObject(Todo.class);
        todo.setId((int)mRealm.where(Todo.class).maximumInt("id") + 1);
        todo.setName(name);
        todo.setCompleted(completed);
        mRealm.commitTransaction();
    }

    @Override
    public void update(int id, String name, boolean completed) {
        mRealm.beginTransaction();
        Todo todo = find(id);
        todo.setName(name);
        todo.setCompleted(completed);
        mRealm.commitTransaction();
    }

    @Override
    public void update(Todo todo, String name) {
        mRealm.beginTransaction();
        todo = mRealm.copyToRealm(todo);
        todo.setName(name);
        mRealm.commitTransaction();
    }

    @Override
    public void update(Todo todo, boolean completed) {
        mRealm.beginTransaction();
        todo.setCompleted(completed);
        mRealm.commitTransaction();
    }

    @Override
    public void deleteCompleted() {
        mRealm.beginTransaction();
        RealmResults<Todo> results = mRealm.where(Todo.class).equalTo("completed", true).findAll();
        results.clear();
        mRealm.commitTransaction();
    }
}
