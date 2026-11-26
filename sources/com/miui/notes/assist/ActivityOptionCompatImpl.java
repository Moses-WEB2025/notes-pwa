package com.miui.notes.assist;

import android.app.ActivityOptions;
import android.os.Bundle;
import androidx.core.app.ActivityOptionsCompat;

/* JADX INFO: loaded from: classes4.dex */
public class ActivityOptionCompatImpl extends ActivityOptionsCompat {
    private final ActivityOptions mActivityOptions;

    public ActivityOptionCompatImpl(ActivityOptions activityOptions) {
        this.mActivityOptions = activityOptions;
    }

    @Override // androidx.core.app.ActivityOptionsCompat
    public Bundle toBundle() {
        return this.mActivityOptions.toBundle();
    }
}
