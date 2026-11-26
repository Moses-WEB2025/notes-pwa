package com.miui.notes.basefeature;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

/* JADX INFO: loaded from: classes4.dex */
public interface IActivityController {
    boolean handleBackPress();

    void onConfigurationChanged(Configuration configuration);

    void onCreate();

    void onDestroy();

    void onMultiWindowModeChanged(boolean z);

    void onNewIntent(Intent intent);

    void onPostCreate(Bundle bundle);

    void onResume();

    void onWindowFocusChanged(boolean z);
}
