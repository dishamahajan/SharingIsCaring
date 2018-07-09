package com.example.md66805.sharingiscaring;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.v7.app.ActionBar;

public class Utility {

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static void ActionBarSetup(String racfId, ActionBar actionBar) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            ActionBar ab = actionBar;
            ab.setSubtitle("Hello, "+racfId);
        }
    }
}
