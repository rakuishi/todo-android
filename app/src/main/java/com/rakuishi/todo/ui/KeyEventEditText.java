package com.rakuishi.todo.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by rakuishi on 15/04/20.
 */
public class KeyEventEditText extends EditText implements TextView.OnEditorActionListener {

    public static final String TAG = KeyEventEditText.class.getSimpleName();

    private KeyEventListener mKeyEventListener;

    public KeyEventEditText(Context context) {
        super(context, null);
        init();
    }

    public KeyEventEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        this.setImeOptions(EditorInfo.IME_ACTION_DONE);
        this.setOnEditorActionListener(this);
    }

    @Override
    public boolean onKeyPreIme(int keyCode, @NonNull KeyEvent event) {
        if (mKeyEventListener != null && keyCode == KeyEvent.KEYCODE_BACK) {
            mKeyEventListener.onBackPressed();
            return true;
        }
        return false;
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (mKeyEventListener != null && actionId == EditorInfo.IME_ACTION_DONE) {
            mKeyEventListener.onEnterPressed();
            return true;
        }
        return false;
    }

    public void setKeyEventListener(KeyEventListener listener) {
        mKeyEventListener = listener;
    }

    public interface KeyEventListener {
        void onEnterPressed();
        void onBackPressed();
    }
}
