package com.rakuishi.todo;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Checkable;
import android.widget.RelativeLayout;

/**
 * Created by rakuishi on 15/04/19.
 */
public class CheckableRelativeLayout extends RelativeLayout implements Checkable {

    private static final int[] CheckedStateSet = {android.R.attr.state_checked};
    private boolean checked = false;

    public CheckableRelativeLayout(Context context) {
        super(context, null);
    }

    public CheckableRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean b) {
        checked = b;
        refreshDrawableState();
        forceLayout();
    }

    public void toggle() {
        checked = !checked;
    }

    @Override
    protected int[] onCreateDrawableState(int extraSpace) {
        final int[] drawableState = super.onCreateDrawableState(extraSpace + 1);
        if (isChecked()) {
            mergeDrawableStates(drawableState, CheckedStateSet);
        }
        return drawableState;
    }
}
