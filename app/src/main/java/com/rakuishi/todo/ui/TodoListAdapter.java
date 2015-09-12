package com.rakuishi.todo.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.rakuishi.todo.R;
import com.rakuishi.todo.persistence.Todo;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TodoListAdapter extends ArrayAdapter<Todo> implements ListAdapter {

    public TodoListAdapter(Context context, List<Todo> list) {
        super(context, 0, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_todo, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        Todo todo = getItem(position);
        int textColor = todo.isCompleted() ? R.color.myDisabledTextColor : R.color.myTextColor;

        viewHolder.textView.setText(todo.getName());
        viewHolder.textView.setTextColor(getContext().getResources().getColor(textColor));

        return convertView;
    }

    public static class ViewHolder {
        @Bind(R.id.item_todo_textview) TextView textView;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
