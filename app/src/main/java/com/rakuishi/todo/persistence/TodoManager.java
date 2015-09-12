package com.rakuishi.todo.persistence;

import java.util.List;

public interface TodoManager {
    Todo find(int id);
    List<Todo> findAll();
    void insert(String name, boolean completed);
    void update(int id, String name, boolean completed);
    void update(Todo todo, String name);
    void update(Todo todo, boolean completed);
    void deleteCompleted();
}
