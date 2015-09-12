package com.rakuishi.todo.ui;

import android.support.v7.app.AppCompatActivity;

import com.rakuishi.todo.App;
import com.rakuishi.todo.AppComponent;

/**
 * Created by rakuishi on 15/09/12.
 */
public class BaseActivity extends AppCompatActivity {

    protected AppComponent appComponent() {
        return ((App) getApplication()).getAppComponent();
    }
}
