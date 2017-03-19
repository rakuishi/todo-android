package com.rakuishi.todo.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;

import com.rakuishi.todo.R;
import com.rakuishi.todo.bus.TodoEvent;
import com.rakuishi.todo.persistence.Todo;
import com.rakuishi.todo.persistence.TodoManager;
import com.rakuishi.todo.util.IntentUtil;
import com.squareup.otto.Bus;

import javax.inject.Inject;

import static com.rakuishi.todo.Config.EXTRA_ID;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TodoCreateActivity extends BaseActivity implements KeyEventEditText.KeyEventListener {

    public static final String TAG = TodoCreateActivity.class.getSimpleName();
    private Todo todo;
    private MenuItem doneMenuItem;
    @Inject TodoManager todoManager;
    @Inject Bus bus;

    @Bind(R.id.todo_create_edittext) KeyEventEditText editText;

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
        appComponent().inject(this);
        setContentView(R.layout.activity_todo_create);
        ButterKnife.bind(this);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setTitle(R.string.todo_create);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        if (Intent.ACTION_VIEW.equals(intent.getAction())) {
            editText.setText(IntentUtil.getQueryParameter(intent, "text"));
        } else {
            int id = IntentUtil.getInt(intent, EXTRA_ID);
            if (id != 0) {
                todo = todoManager.find(id);
                editText.setText(todo.getName());
                getSupportActionBar().setTitle(R.string.todo_update);
            }
        }

        editText.setKeyEventListener(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.todo_create, menu);
        doneMenuItem = menu.findItem(R.id.action_done);
        updateDoneMenuItem();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_done:
                saveTodo();
            case android.R.id.home:
                finishTodoCreateActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onEnterPressed() {
        if (enableToSave()) {
            saveTodo();
            finishTodoCreateActivity();
        }
    }

    @Override
    public void onBackPressed() {
        finishTodoCreateActivity();
    }

    @Override
    public void onTextChanged() {
        updateDoneMenuItem();
    }

    private void saveTodo() {
        if (todo == null) {
            todoManager.insert(editText.getText().toString(), false);
            bus.post(new TodoEvent(TodoEvent.QUERY_INSERT));
        } else {
            todoManager.update(todo, editText.getText().toString());
            bus.post(new TodoEvent(TodoEvent.QUERY_UPDATE));
        }
    }

    private boolean enableToSave() {
        return editText != null && !TextUtils.isEmpty(editText.getText().toString());
    }

    private void updateDoneMenuItem() {
        if (enableToSave()) {
            doneMenuItem.setEnabled(true);
            doneMenuItem.getIcon().setAlpha(255);
        } else {
            doneMenuItem.setEnabled(false);
            doneMenuItem.getIcon().setAlpha(127);
        }
    }

    private void finishTodoCreateActivity() {
        finish();
        Intent intent = new Intent(this, TodoListActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
}
