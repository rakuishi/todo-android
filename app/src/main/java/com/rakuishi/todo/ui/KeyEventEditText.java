package com.rakuishi.todo.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

public class KeyEventEditText extends EditText implements TextView.OnEditorActionListener, TextWatcher {

    public interface KeyEventListener {
        void onEnterPressed();
        void onBackPressed();
        void onTextChanged();
    }

    public static final String TAG = KeyEventEditText.class.getSimpleName();
    private KeyEventListener keyEventListener;

    public KeyEventEditText(Context context) {
        super(context, null);
        initView();
    }

    public KeyEventEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public KeyEventEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public KeyEventEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }

    private void initView() {
        setImeOptions(EditorInfo.IME_ACTION_DONE);
        setOnEditorActionListener(this);
        addTextChangedListener(this);
    }

    public void setKeyEventListener(KeyEventListener listener) {
        keyEventListener = listener;
    }

    @Override
    public boolean onKeyPreIme(int keyCode, @NonNull KeyEvent event) {
        if (keyEventListener != null && keyCode == KeyEvent.KEYCODE_BACK) {
            keyEventListener.onBackPressed();
            return true;
        }
        return false;
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (keyEventListener != null && actionId == EditorInfo.IME_ACTION_DONE) {
            keyEventListener.onEnterPressed();
            return true;
        }
        return false;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

    @Override
    public void afterTextChanged(Editable editable) {
        if (keyEventListener != null) {
            keyEventListener.onTextChanged();
        }
    }
}
