package com.rakuishi.todo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.OnItemClick;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by rakuishi on 15/04/05.
 */
public class TodoListFragment extends Fragment {

    private Realm mRealm;
    private TodoAdapter mAdapter;

    @InjectView(R.id.todo_listview) ListView mListView;
    @InjectView(R.id.todo_insert_button) ImageButton mImageButton;

    @OnClick(R.id.todo_insert_button)
    void onClickInsertButton() {
        mRealm.beginTransaction();
        Todo todo = mRealm.createObject(Todo.class);
        todo.setName("Sample");
        todo.setCompleted(false);
        mRealm.commitTransaction();
    }

    @OnItemClick(R.id.todo_listview)
    void onItemClick(int position) {
        Todo todo = mAdapter.getItem(position);

        mRealm.beginTransaction();
        todo.setCompleted(!todo.isCompleted());
        mRealm.commitTransaction();

        mAdapter.notifyDataSetChanged();
        mListView.invalidate();
    }

    public TodoListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_todo_list, container, false);
        ButterKnife.inject(this, rootView);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mRealm = Realm.getInstance(getActivity());
        RealmQuery<Todo> query = mRealm.where(Todo.class);
        RealmResults<Todo> results = query.findAll();

        mAdapter = new TodoAdapter(getActivity(), results, true);
        mListView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        mListView.invalidate();
    }
}
