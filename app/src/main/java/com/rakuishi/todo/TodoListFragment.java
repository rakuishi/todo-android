package com.rakuishi.todo;

import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

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
        mRealm.beginTransaction();
        Todo todo = mRealm.createObject(Todo.class);
        todo.setId((int)mRealm.where(Todo.class).maximumInt("id") + 1);
        todo.setName("Sample");
        todo.setCompleted(false);
        mRealm.commitTransaction();
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
