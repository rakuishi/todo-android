package com.rakuishi.todo;

import android.app.Application;

public class App extends Application {

    private static AppComponent appComponent;

    public AppComponent getAppComponent() {
        if (appComponent == null) {
            appComponent = DaggerAppComponent.builder()
                    .appModule(new AppModule(this))
                    .build();
        }

        return appComponent;
    }
}
