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

    private Todo mTodo;
    private Realm mRealm;

    @InjectView(R.id.todo_create_et) EditText mEditText;

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
        getSupportActionBar().setTitle(R.string.todo_create);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int id = (extras.containsKey(EXTRA_ID)) ? extras.getInt(EXTRA_ID) : -1;
            if (id != -1) {
                mTodo = mRealm.where(Todo.class).equalTo("id", id).findAll().first();
                mEditText.setText(mTodo.getName());
                getSupportActionBar().setTitle(R.string.todo_update);
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
                mRealm.beginTransaction();
                if (mTodo == null) {
                    mTodo = mRealm.createObject(Todo.class);
                    mTodo.setId((int)mRealm.where(Todo.class).maximumInt("id") + 1);
                    mTodo.setCompleted(false);
                }
                mTodo.setName(mEditText.getText().toString());
                mRealm.commitTransaction();
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
