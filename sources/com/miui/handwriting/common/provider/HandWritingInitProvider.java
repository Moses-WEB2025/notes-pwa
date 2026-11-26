package com.miui.handwriting.common.provider;

import android.app.Application;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import com.miui.handwriting.common.HandWritingViewApplication;
import com.miui.handwriting.common.taskservice.CreationServiceManager;

/* JADX INFO: loaded from: classes3.dex */
public class HandWritingInitProvider extends ContentProvider {
    private static final String TAG = "HandWritingInitProvider";

    @Override // android.content.ContentProvider
    public int delete(Uri uri, String str, String[] strArr) {
        return 0;
    }

    @Override // android.content.ContentProvider
    public Uri insert(Uri uri, ContentValues contentValues) {
        return null;
    }

    @Override // android.content.ContentProvider
    public Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        return null;
    }

    @Override // android.content.ContentProvider
    public int update(Uri uri, ContentValues contentValues, String str, String[] strArr) {
        return 0;
    }

    @Override // android.content.ContentProvider
    public boolean onCreate() {
        Context applicationContext = getContext().getApplicationContext();
        if (!(applicationContext instanceof Application)) {
            return false;
        }
        HandWritingViewApplication.INSTANCE.init((Application) applicationContext);
        CreationServiceManager.INSTANCE.bindCreationTaskService(applicationContext);
        Log.d(TAG, "onCreate: bindCreationTaskService");
        return false;
    }

    @Override // android.content.ContentProvider
    public String getType(Uri uri) {
        return "";
    }
}
