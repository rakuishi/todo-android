package com.rakuishi.todo.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.rakuishi.todo.R;
import com.rakuishi.todo.persistence.Todo;
import com.rakuishi.todo.persistence.TodoManager;

import javax.inject.Inject;

import static com.rakuishi.todo.Config.CODE_TODO_CREATE;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import butterknife.OnItemLongClick;

public class TodoListActivity extends BaseActivity {

    public static final String TAG = TodoListActivity.class.getSimpleName();
    private TodoListAdapter mAdapter;
    @Inject TodoManager mTodoManager;

    @Bind(R.id.todo_list_listview) ListView mListView;
    @Bind(R.id.todo_list_empty_view) TextView mEmptyTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appComponent().inject(this);
        setContentView(R.layout.activity_todo_list);
        ButterKnife.bind(this);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        mAdapter = new TodoListAdapter(this, mTodoManager.findAll(), true);
        mListView.setAdapter(mAdapter);
        mListView.setEmptyView(mEmptyTextView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.todo_list, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete:
                mTodoManager.deleteCompleted();
                break;
            case R.id.action_github:
                openUri("https://github.com/rakuishi/Todo-Android/");
                break;
            case R.id.action_attributions:
                openUri("https://github.com/rakuishi/Todo-Android/blob/master/ATTRIBUTIONS.md");
                break;
            case R.id.action_help:
                openUri("https://github.com/rakuishi/Todo-Android/issues");
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case CODE_TODO_CREATE:
                // RealmBaseAdapter watch Realm data changing.
                return;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @OnClick(R.id.todo_list_add_imagebutton)
    void onClickInsertButton() {
        startActivityForResult(TodoCreateActivity.createIntent(this), CODE_TODO_CREATE);
    }

    @OnItemClick(R.id.todo_list_listview)
    void onItemClick(int position) {
        Todo todo = mAdapter.getItem(position);
        mTodoManager.update(todo, !todo.isCompleted());
        mAdapter.notifyDataSetChanged();
        mListView.invalidate();
    }

    @OnItemLongClick(R.id.todo_list_listview)
    boolean onItemLongClick(int position) {
        Todo todo = mAdapter.getItem(position);
        startActivityForResult(TodoCreateActivity.createIntent(this, todo.getId()), CODE_TODO_CREATE);
        return true;
    }

    private void openUri(String uri) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        startActivity(intent);
    }
}
