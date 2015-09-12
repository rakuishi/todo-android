package com.rakuishi.todo;

import com.rakuishi.todo.io.PersistenceModule;
import com.rakuishi.todo.ui.TodoCreateActivity;
import com.rakuishi.todo.ui.TodoListActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(
    modules = {
        AppModule.class,
        PersistenceModule.class
    }
)
public interface AppComponent {
    void inject(TodoListActivity activity);
    void inject(TodoCreateActivity activity);
}
