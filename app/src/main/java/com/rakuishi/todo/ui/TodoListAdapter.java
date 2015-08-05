package com.rakuishi.todo.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.rakuishi.todo.R;
import com.rakuishi.todo.io.Todo;

import butterknife.ButterKnife;
import butterknife.InjectView;
import io.realm.RealmBaseAdapter;
import io.realm.RealmResults;

/**
 * Created by rakuishi on 15/04/11.
 */
public class TodoListAdapter extends RealmBaseAdapter<Todo> implements ListAdapter {

    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public TodoListAdapter(Context context, RealmResults<Todo> realmResults, boolean automaticUpdate) {
        super(context, realmResults, automaticUpdate);
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.todo_list_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        Todo todo = realmResults.get(position);
        int textColor = todo.isCompleted() ? R.color.myDisabledTextColor : R.color.myTextColor;

        viewHolder.textView.setText(todo.getName());
        viewHolder.textView.setTextColor(mContext.getResources().getColor(textColor));

        return convertView;
    }

    public static class ViewHolder {
        @InjectView(R.id.todo_list_item_tv) TextView textView;

        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
