package com.miui.notes.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import miuix.responsive.map.ScreenSpec;

/* JADX INFO: loaded from: classes4.dex */
public interface IFragmentController extends IFragmentDestroy {
    public static final int SPLIT_MODE_ALL = 0;
    public static final int SPLIT_MODE_NAVIGATION = 1;
    public static final int SPLIT_MODE_NOTE_EDIT = 2;
    public static final String TAG_CONTENT = "content";
    public static final String TAG_FOLDER = "folder";
    public static final String TAG_LIST = "list";
    public static final String TAG_NAVIGATION = "nav";

    void dispatchKeyEvent(KeyEvent keyEvent);

    void dispatchTouchEvent(MotionEvent motionEvent);

    boolean needLockInPrivate();

    boolean onActivityResult(int i, int i2, Intent intent);

    boolean onBackPressed();

    void onConfigurationChanged(Configuration configuration);

    void onCreate(Bundle bundle);

    void onNewIntent(Intent intent);

    void onPostCreate(Bundle bundle);

    void onResponsiveLayout(Configuration configuration, ScreenSpec screenSpec, boolean z);

    void onResume();

    boolean onSearchRequested();

    void onStop();

    default void onWindowFocusChanged(boolean z) {
    }

    void resetNeedLockInPrivate();
}
