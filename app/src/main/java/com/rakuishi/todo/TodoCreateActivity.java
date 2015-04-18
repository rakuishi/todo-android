package com.rakuishi.todo;

import static com.rakuishi.todo.IntentCode.EXTRA_ID;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

/**
 * Created by rakuishi on 15/04/18.
 */
public class TodoCreateActivity extends ActionBarActivity {

    public static Intent createIntent(Context context) {
        return new Intent(context, TodoCreateActivity.class);
    }

    public static Intent createIntent(Context context, int id) {
        Intent intent = new Intent(context, TodoCreateActivity.class);
        intent.putExtra(EXTRA_ID, id);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todo_create);
    }
}
