package com.rakuishi.todo;

import static com.rakuishi.todo.NavigationDrawerItem.TYPE_SEPARATOR;
import static com.rakuishi.todo.NavigationDrawerItem.TYPE_CHECKABLE_ITEM;
import static com.rakuishi.todo.NavigationDrawerItem.TYPE_ITEM;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ActionBarActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    private NavigationDrawerFragment mNavigationDrawerFragment;
    private NavigationDrawerAdapter mAdapter;

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
        if (mAdapter.getItem(position).getType() == TYPE_SEPARATOR) {
            return;
        }

        switch (position) {
            case 0:
                replaceFragment(new TodoListFragment());
                break;
            case 2:
                openUri("https://github.com/rakuishi/Todo-Android/");
                break;
            case 3:
                openUri("https://github.com/rakuishi/Todo-Android/blob/master/ATTRIBUTIONS.md");
                break;
            case 4:
                openUri("https://github.com/rakuishi/Todo-Android/issues");
                break;
        }
    }

    private void openUri(String uri) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        if (mNavigationDrawerFragment.isDrawerOpen()) {
            mNavigationDrawerFragment.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }

    private void replaceFragment(Fragment fragment) {
        getFragmentManager()
            .beginTransaction()
            .replace(R.id.container, fragment)
            .commit();
    }

    private List<NavigationDrawerItem> createNavigationDrawerItemList() {
        List<NavigationDrawerItem> list = new ArrayList<>();
        list.add(new NavigationDrawerItem(R.drawable.ic_inbox, getResources().getString(R.string.drawer_item_inbox), TYPE_CHECKABLE_ITEM));
        list.add(new NavigationDrawerItem(TYPE_SEPARATOR));
        list.add(new NavigationDrawerItem(R.drawable.ic_github, getResources().getString(R.string.drawer_item_github), TYPE_ITEM));
        list.add(new NavigationDrawerItem(R.drawable.ic_info, getResources().getString(R.string.drawer_item_attributions), TYPE_ITEM));
        list.add(new NavigationDrawerItem(R.drawable.ic_help, getResources().getString(R.string.drawer_item_help), TYPE_ITEM));
        return list;
    }
}
