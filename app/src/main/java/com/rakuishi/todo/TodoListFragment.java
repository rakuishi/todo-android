package com.rakuishi.todo;

import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import static android.app.Activity.RESULT_OK;
import static com.rakuishi.todo.ResultCode.TODO_CREATE;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.OnItemClick;
import butterknife.OnItemLongClick;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by rakuishi on 15/04/05.
 */
public class TodoListFragment extends Fragment {

    private Realm mRealm;
    private TodoListAdapter mAdapter;

    @InjectView(R.id.todo_list_lv) ListView mListView;
    @InjectView(R.id.todo_list_toolbar) Toolbar mToolbar;

    @OnClick(R.id.todo_list_add_ib)
    void onClickInsertButton() {
        startActivityForResult(TodoCreateActivity.createIntent(getActivity()), TODO_CREATE);
    }

    @OnItemClick(R.id.todo_list_lv)
    void onItemClick(int position) {
        Todo todo = mAdapter.getItem(position);

        mRealm.beginTransaction();
        todo.setCompleted(!todo.isCompleted());
        mRealm.commitTransaction();

        mAdapter.notifyDataSetChanged();
        mListView.invalidate();
    }

    @OnItemLongClick(R.id.todo_list_lv)
    boolean onItemLongClick(int position) {
        Todo todo = mAdapter.getItem(position);
        startActivityForResult(TodoCreateActivity.createIntent(getActivity(), todo.getId()), TODO_CREATE);
        return true;
    }

    public TodoListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.todo_list, container, false);
        ButterKnife.inject(this, rootView);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);

        mRealm = Realm.getInstance(getActivity());
        RealmQuery<Todo> query = mRealm.where(Todo.class);
        RealmResults<Todo> results = query.findAll();

        ActionBarActivity activity = (ActionBarActivity)getActivity();
        activity.setSupportActionBar(mToolbar);
        // activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // activity.getSupportActionBar().setHomeButtonEnabled(true);

        mAdapter = new TodoListAdapter(getActivity(), results, true);
        mListView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        mListView.invalidate();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.todo_list, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.m_delete:
                deleteCompletedTodo();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case TODO_CREATE:
                // RealmBaseAdapter watch Realm data changing.
                return;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void deleteCompletedTodo() {
        mRealm.beginTransaction();
        RealmResults<Todo> results = mRealm.where(Todo.class).equalTo("completed", true).findAll();
        results.clear();
        mRealm.commitTransaction();
    }
}
