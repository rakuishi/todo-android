package com.rakuishi.todo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import io.realm.RealmBaseAdapter;
import io.realm.RealmResults;

/**
 * Created by rakuishi on 15/04/11.
 */
public class TodoAdapter extends RealmBaseAdapter<Todo> implements ListAdapter {

    private LayoutInflater mLayoutInflater;

    public TodoAdapter(Context context, RealmResults<Todo> realmResults, boolean automaticUpdate) {
        super(context, realmResults, automaticUpdate);
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.list_item_todo, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        Todo todo = realmResults.get(position);

        if (todo.isCompleted()) {
            viewHolder.textView.setText("✓ " + todo.getName());
        } else {
            viewHolder.textView.setText(todo.getName());
        }

        return convertView;
    }

    public static class ViewHolder {
        @InjectView(R.id.list_item_todo_textview) TextView textView;

        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
