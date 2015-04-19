package com.rakuishi.todo;

import static com.rakuishi.todo.NavigationDrawerItem.TYPE_SEPARATOR;
import static com.rakuishi.todo.NavigationDrawerItem.TYPE_CHECKABLE_ITEM;
import static com.rakuishi.todo.NavigationDrawerItem.TYPE_ITEM;

import android.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ActionBarActivity implements NavigationDrawerCallbacks {

    private NavigationDrawerFragment mNavigationDrawerFragment;
    private NavigationDrawerAdapter mAdapter;
    private Fragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAdapter = new NavigationDrawerAdapter(this, createNavigationDrawerItemList());

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.fragment_drawer);

        mNavigationDrawerFragment.setup(
                R.id.fragment_drawer,
                (DrawerLayout) findViewById(R.id.drawer),
                mAdapter
        );
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        switch (mAdapter.getItem(position).getType()) {
            case TYPE_CHECKABLE_ITEM:
                if (mFragment == null) {
                    mFragment = new TodoListFragment();
                    addFragment(mFragment);
                } else {
                    replaceFragment(mFragment);
                }
                break;
            case TYPE_ITEM:
                // Such as Launching Activity Code
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (mNavigationDrawerFragment.isDrawerOpen()) {
            mNavigationDrawerFragment.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }

    private void addFragment(Fragment fragment) {
        getFragmentManager()
            .beginTransaction()
            .add(R.id.container, fragment)
            .commit();
    }

    private void replaceFragment(Fragment fragment) {
        getFragmentManager()
            .beginTransaction()
            .replace(R.id.container, fragment)
            .commit();
    }

    private List<NavigationDrawerItem> createNavigationDrawerItemList() {
        List<NavigationDrawerItem> list = new ArrayList<>();
        list.add(new NavigationDrawerItem(R.drawable.ic_check, getResources().getString(R.string.drawer_item_todo), TYPE_CHECKABLE_ITEM));
        list.add(new NavigationDrawerItem(TYPE_SEPARATOR));
        list.add(new NavigationDrawerItem(R.drawable.ic_settings, getResources().getString(R.string.drawer_item_settings), TYPE_ITEM));
        return list;
    }
}
