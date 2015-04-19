package com.rakuishi.todo;

import android.graphics.drawable.Drawable;

/**
 * Created by rakuishi on 15/04/19.
 */
public class NavigationItem {
    private String mText;

    public NavigationItem(String text) {
        mText = text;
    }

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        mText = text;
    }
}
