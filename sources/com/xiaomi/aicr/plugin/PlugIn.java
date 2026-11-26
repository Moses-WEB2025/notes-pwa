package com.xiaomi.aicr.plugin;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.IBinder;

/* JADX INFO: loaded from: classes4.dex */
public interface PlugIn {

    public interface PlugInManagerInterface {
        void onPlugInStop(IBinder iBinder, String str);

        IBinder queryBinder(String str, String str2, Bundle bundle);

        void releaseBinder(String str, String str2);
    }

    default boolean canStop() {
        return true;
    }

    IBinder getBinder();

    String getVersion();

    void init(Context context, PlugInManagerInterface plugInManagerInterface, Resources resources);

    default void release() {
    }

    default void restorePreviousState() {
    }
}
