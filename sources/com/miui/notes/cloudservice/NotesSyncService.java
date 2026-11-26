package com.miui.notes.cloudservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/* JADX INFO: loaded from: classes4.dex */
public class NotesSyncService extends Service {
    private static NotesSyncAdapter sSyncAdapter;
    private static final Object sSyncAdapterLock = new Object();

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        synchronized (sSyncAdapterLock) {
            if (sSyncAdapter == null) {
                sSyncAdapter = new NotesSyncAdapter(getApplicationContext(), true);
            }
        }
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        IBinder syncAdapterBinder;
        synchronized (sSyncAdapterLock) {
            syncAdapterBinder = sSyncAdapter.getSyncAdapterBinder();
        }
        return syncAdapterBinder;
    }
}
