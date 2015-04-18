package com.rakuishi.todo;

import static com.rakuishi.todo.IntentCode.EXTRA_ID;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import butterknife.ButterKnife;
import butterknife.InjectView;
import io.realm.Realm;

/**
 * Created by rakuishi on 15/04/18.
 */
public class TodoCreateActivity extends ActionBarActivity {

    private int mId;
    private Realm mRealm;

    @InjectView(R.id.todo_create_et)
    EditText mEditText;

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
        ButterKnife.inject(this);
        mRealm = Realm.getInstance(this);

        setSupportActionBar((android.support.v7.widget.Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            mId = (extras.containsKey(EXTRA_ID)) ? extras.getInt(EXTRA_ID) : -1;
            if (mId != -1) {
                Todo todo = mRealm.where(Todo.class).equalTo("id", mId).findAll().first();
                mEditText.setText(todo.getName());
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu options) {
        getMenuInflater().inflate(R.menu.todo_create, options);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.m_done:
                return true;
            case android.R.id.home:
                finish();
                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
