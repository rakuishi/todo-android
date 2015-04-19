package com.rakuishi.todo;

import static com.rakuishi.todo.NavigationDrawerItem.TYPE_SEPARATOR;
import static com.rakuishi.todo.NavigationDrawerItem.TYPE_CHECKABLE_ITEM;
import static com.rakuishi.todo.NavigationDrawerItem.TYPE_ITEM;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class NavigationDrawerAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private List<NavigationDrawerItem> mNavigationDrawerItemList;

    public NavigationDrawerAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mNavigationDrawerItemList = createNavigationDrawerItemList();
    }

    private List<NavigationDrawerItem> createNavigationDrawerItemList() {
        Resources resources = mContext.getResources();
        List<NavigationDrawerItem> list = new ArrayList<>();
        list.add(new NavigationDrawerItem(R.drawable.ic_check, resources.getString(R.string.drawer_item_todo), TYPE_CHECKABLE_ITEM));
        list.add(new NavigationDrawerItem(TYPE_SEPARATOR));
        list.add(new NavigationDrawerItem(R.drawable.ic_settings, resources.getString(R.string.drawer_item_settings), TYPE_ITEM));
        return list;
    }

    public boolean isCheckableItem(int position) {
        return getItem(position).getType() == TYPE_CHECKABLE_ITEM;
    }

    @Override
    public int getCount() {
        return mNavigationDrawerItemList.size();
    }

    @Override
    public long getItemId(int position) {
        return mNavigationDrawerItemList.get(position).getType();
    }

    @Override
    public NavigationDrawerItem getItem(int position) {
        return mNavigationDrawerItemList.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        NavigationDrawerItem item = getItem(position);

        switch (item.getType()) {
            case TYPE_ITEM:
            case TYPE_CHECKABLE_ITEM:
                convertView = mInflater.inflate(R.layout.navigation_drawer_list_item, parent, false);
                TextView textView = ButterKnife.findById(convertView, R.id.navigation_drawer_list_item_tv);
                ImageView imageView = ButterKnife.findById(convertView, R.id.navigation_drawer_list_item_iv);
                textView.setText(item.getName());
                imageView.setImageResource(item.getResId());
                break;
            default:
                convertView = mInflater.inflate(R.layout.navigation_drawer_list_item_separator, parent, false);
                convertView.setEnabled(false);
                convertView.setOnClickListener(null);
                break;
        }

        return convertView;
    }
}
