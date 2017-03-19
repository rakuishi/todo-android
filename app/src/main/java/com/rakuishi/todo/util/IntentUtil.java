package com.rakuishi.todo.util;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;

public class IntentUtil {

    private IntentUtil() {}

    public static void openUri(Activity activity, String uri) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
            activity.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    @NonNull
    public static String getQueryParameter(Intent intent, String key) {
        Uri uri = intent.getData();
        if (uri != null) {
            String param = uri.getQueryParameter(key);
            return TextUtils.isEmpty(param) ? "" : param;
        }

        return "";
    }

    public static int getInt(Intent intent, String key) {
        Bundle extras = intent.getExtras();
        if (extras != null && extras.containsKey(key)) {
            return extras.getInt(key);
        }

        return 0;
    }
}
