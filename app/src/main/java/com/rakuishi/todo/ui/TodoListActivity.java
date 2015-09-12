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
import com.rakuishi.todo.io.Todo;
import com.rakuishi.todo.io.TodoManager;

import javax.inject.Inject;

import static com.rakuishi.todo.Config.CODE_TODO_CREATE;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.OnItemClick;

public class TodoListActivity extends BaseActivity {

    @Inject TodoManager mTodoManager;
    private TodoListAdapter mAdapter;

    @InjectView(R.id.todo_list_lv) ListView mListView;
    @InjectView(R.id.todo_list_toolbar) Toolbar mToolbar;
    @InjectView(R.id.todo_list_empty_tv) TextView mEmptyTextView;

    @OnClick(R.id.todo_list_add_ib)
    void onClickInsertButton() {
        startActivityForResult(TodoCreateActivity.createIntent(this), CODE_TODO_CREATE);
    }

    @OnItemClick(R.id.todo_list_lv)
    void onItemClick(int position) {
        Todo todo = mAdapter.getItem(position);
        mTodoManager.update(todo, !todo.isCompleted());
        mAdapter.notifyDataSetChanged();
        mListView.invalidate();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appComponent().inject(this);
        setContentView(R.layout.todo_list);
        ButterKnife.inject(this);
        setSupportActionBar(mToolbar);

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

    private void openUri(String uri) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        startActivity(intent);
    }
}
