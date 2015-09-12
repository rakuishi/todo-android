package com.rakuishi.todo.ui;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.rakuishi.todo.R;
import com.rakuishi.todo.bus.TodoEvent;
import com.rakuishi.todo.persistence.Todo;
import com.rakuishi.todo.persistence.TodoManager;
import com.rakuishi.todo.utils.IntentUtils;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import butterknife.OnItemLongClick;

public class TodoListActivity extends BaseActivity {

    public static final String TAG = TodoListActivity.class.getSimpleName();
    private TodoListAdapter mAdapter;
    private List<Todo> mList = new ArrayList<>();
    @Inject TodoManager mTodoManager;
    @Inject Bus mBus;

    @Bind(R.id.todo_list_listview) ListView mListView;
    @Bind(R.id.todo_list_empty_view) TextView mEmptyTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appComponent().inject(this);
        setContentView(R.layout.activity_todo_list);
        ButterKnife.bind(this);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        mBus.register(this);

        mList.addAll(mTodoManager.findAll());
        mAdapter = new TodoListAdapter(this, mList);
        mListView.setAdapter(mAdapter);
        mListView.setEmptyView(mEmptyTextView);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBus.unregister(this);
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
                mBus.post(new TodoEvent(TodoEvent.QUERY_DELETE));
                break;
            case R.id.action_github:
                IntentUtils.openUri(this, "https://github.com/rakuishi/Todo-Android/");
                break;
            case R.id.action_attributions:
                IntentUtils.openUri(this, "https://github.com/rakuishi/Todo-Android/blob/master/ATTRIBUTIONS.md");
                break;
            case R.id.action_help:
                IntentUtils.openUri(this, "https://github.com/rakuishi/Todo-Android/issues");
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.todo_list_add_imagebutton)
    void onClickInsertButton() {
        startActivity(TodoCreateActivity.createIntent(this));
    }

    @OnItemClick(R.id.todo_list_listview)
    void onItemClick(int position) {
        Todo todo = mAdapter.getItem(position);
        mTodoManager.update(todo, !todo.isCompleted());
        mBus.post(new TodoEvent(TodoEvent.QUERY_UPDATE));
    }

    @OnItemLongClick(R.id.todo_list_listview)
    boolean onItemLongClick(int position) {
        Todo todo = mAdapter.getItem(position);
        startActivity(TodoCreateActivity.createIntent(this, todo.getId()));
        return true;
    }

    @Subscribe
    public void onTodoEvent(TodoEvent event) {
        mList.clear();
        mList.addAll(mTodoManager.findAll());
        mAdapter.notifyDataSetChanged();
    }
}
