package com.rakuishi.todo;

import static com.rakuishi.todo.IntentCode.EXTRA_ID;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by rakuishi on 15/04/18.
 */
public class TodoCreateActivity extends ActionBarActivity implements KeyBackEditText.KeyBackListener, TextWatcher {

    public static final String TAG = ActionBarActivity.class.getSimpleName();

    private Todo mTodo;
    private TodoManager mTodoManager;
    private MenuItem mDoneMenuItem;

    @InjectView(R.id.todo_create_et) KeyBackEditText mEditText;

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

        mTodoManager = new TodoManager(this);

        setSupportActionBar((android.support.v7.widget.Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setTitle(R.string.todo_create);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        String action = intent.getAction();
        if (Intent.ACTION_VIEW.equals(action)) {
            Uri uri = intent.getData();
            if (uri != null) {
                mEditText.setText(uri.getQueryParameter("text"));
            }
        } else {
            Bundle extras = intent.getExtras();
            if (extras != null) {
                int id = (extras.containsKey(EXTRA_ID)) ? extras.getInt(EXTRA_ID) : -1;
                if (id != -1) {
                    mTodo = mTodoManager.find(id);
                    mEditText.setText(mTodo.getName());
                    getSupportActionBar().setTitle(R.string.todo_update);
                }
            }
        }

        mEditText.setKeyBackListener(this);
        mEditText.addTextChangedListener(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.todo_create, menu);
        mDoneMenuItem = (MenuItem) menu.findItem(R.id.m_done);
        updateDoneMenuItem(mEditText.getText().toString());
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.m_done:
                if (mTodo == null) {
                    mTodoManager.insert(mEditText.getText().toString(), false);
                } else {
                    mTodoManager.update(mTodo, mEditText.getText().toString());
                }
            case android.R.id.home:
                finishTodoCreateActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void finishTodoCreateActivity() {
        finish();
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    @Override
    public void onKeyBackPressed() {
        finishTodoCreateActivity();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        updateDoneMenuItem(s.toString());
    }

    private void updateDoneMenuItem(String string) {
        if (string.length() > 0) {
            mDoneMenuItem.setEnabled(true);
            mDoneMenuItem.getIcon().setAlpha(255);
        } else {
            mDoneMenuItem.setEnabled(false);
            mDoneMenuItem.getIcon().setAlpha(127);
        }
    }
}
