package com.rakuishi.todo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.EditText;

/**
 * Created by rakuishi on 15/04/20.
 */
public class KeyBackEditText extends EditText {

    private KeyBackListener mKeyBackListener;

    public KeyBackEditText(Context context) {
        super(context, null);
    }

    public KeyBackEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onKeyPreIme(int keyCode, @NonNull KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mKeyBackListener != null)
                mKeyBackListener.onKeyBackPressed();
            return true;
        }
        return false;
    }

    public void setKeyBackListener(KeyBackListener listener) {
        mKeyBackListener = listener;
    }

    public interface KeyBackListener {
        void onKeyBackPressed();
    }
}
