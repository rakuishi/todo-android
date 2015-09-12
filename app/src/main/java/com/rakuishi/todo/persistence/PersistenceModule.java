package com.rakuishi.todo.persistence;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class PersistenceModule {

    @Provides @Singleton
    public TodoManager provideTodoManager(Context context) {
        return new TodoRealmManager(context);
    }
}
