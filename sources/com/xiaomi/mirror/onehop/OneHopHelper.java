package com.xiaomi.mirror.onehop;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import java.lang.ref.WeakReference;

/* JADX INFO: loaded from: classes4.dex */
public class OneHopHelper {
    public static final boolean ABORT_BROADCAST = true;
    public static final String ACTION_ONEHOP_MIRROR = "com.miui.onehop.action.MIRROR";
    public static final String EXTRA_ABILITY_LYRA = "_ability_lyra";
    public static final String EXTRA_BT_MAC = "_bt_mac";
    public static final String EXTRA_DEVICE_TYPE = "_device_type";
    public static final int FILESHARE_PRIORITY = 1000;
    public static final String LOG_TAG = "OneHopHelper";
    public static final String METADATA_KEY_LYRA = "com.xiaomi.continuity.VERSION_NAME";
    public static final String METADATA_KEY_MIRROR = "com.xiaomi.mirror.nfc";
    public static final String METADATA_KEY_MISHARE = "com.miui.mishare.connectivity.nfc";
    public static final boolean NOT_ABORT_BROADCAST = false;
    public static final int ONE_HOP_VERSION_INVALID = -1;
    public static final int ONE_HOP_VERSION_MIRROR = 0;
    public static final int ONE_HOP_VERSION_MISHARE = 1;
    public static final String PACKAGE_LYRA = "com.xiaomi.mi_connect_service";
    public static final String PACKAGE_MIRROR = "com.xiaomi.mirror";
    public static final String PACKAGE_MISHARE = "com.miui.mishare.connectivity";
    public static final String PERMISSION_ONEHOP_MIRROR = "com.miui.onehop.permission.MIRROR";
    public static volatile OneHopHelper sManager;
    public boolean mHasInitialize;
    public BroadcastReceiver mOneHopShareReceiver;
    public boolean mSelfIsSupportLyra;

    public static class Holder {
        public static final OneHopHelper sInstance = new OneHopHelper();
    }

    public interface OneHopShareCallback {
        boolean onOneHopShare(int i, String str, int i2);
    }

    public OneHopHelper() {
        this.mSelfIsSupportLyra = false;
        this.mHasInitialize = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean checkAbilitySupport(Context context, String str, String str2) {
        Bundle metaData = getMetaData(context, str);
        if (metaData == null) {
            return false;
        }
        return metaData.getBoolean(str2, false);
    }

    private boolean checkIsSupportLyra(Context context) {
        Bundle metaData = getMetaData(context, PACKAGE_LYRA);
        return (metaData == null || metaData.getString(METADATA_KEY_LYRA, null) == null) ? false : true;
    }

    public static OneHopHelper getInstance() {
        return Holder.sInstance;
    }

    private Bundle getMetaData(Context context, String str) {
        ApplicationInfo applicationInfo;
        try {
            applicationInfo = context.getPackageManager().getApplicationInfo(str, 128);
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(LOG_TAG, "name not found", e);
            applicationInfo = null;
        }
        if (applicationInfo != null) {
            return applicationInfo.metaData;
        }
        return null;
    }

    public void registerOneHopShareReceiver(Context context, OneHopShareCallback oneHopShareCallback) {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            Log.w(LOG_TAG, "register oneHopShareReceiver must in mainThread");
            return;
        }
        if (!this.mHasInitialize) {
            this.mSelfIsSupportLyra = checkIsSupportLyra(context);
            this.mHasInitialize = true;
        }
        Log.d(LOG_TAG, "self is support lyra: " + this.mSelfIsSupportLyra);
        if (this.mOneHopShareReceiver != null) {
            unregisterOneHopShareReceiver(context);
        }
        final WeakReference weakReference = new WeakReference(oneHopShareCallback);
        this.mOneHopShareReceiver = new BroadcastReceiver() { // from class: com.xiaomi.mirror.onehop.OneHopHelper.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                if (OneHopHelper.ACTION_ONEHOP_MIRROR.equals(intent.getAction())) {
                    int intExtra = intent.getIntExtra(OneHopHelper.EXTRA_DEVICE_TYPE, -1);
                    String stringExtra = intent.getStringExtra(OneHopHelper.EXTRA_BT_MAC);
                    int i = 0;
                    boolean booleanExtra = intent.getBooleanExtra(OneHopHelper.EXTRA_ABILITY_LYRA, false);
                    Log.d(OneHopHelper.LOG_TAG, "mSelfIsSupportLyra: " + OneHopHelper.this.mSelfIsSupportLyra + ", abilityLyra: " + booleanExtra);
                    if (OneHopHelper.this.mSelfIsSupportLyra && booleanExtra) {
                        if (!OneHopHelper.this.checkAbilitySupport(context2, "com.miui.mishare.connectivity", OneHopHelper.METADATA_KEY_MISHARE)) {
                            Log.w(OneHopHelper.LOG_TAG, "mishare version not support one hop share");
                            return;
                        }
                        i = 1;
                    } else if (!OneHopHelper.this.checkAbilitySupport(context2, "com.xiaomi.mirror", OneHopHelper.METADATA_KEY_MIRROR)) {
                        Log.w(OneHopHelper.LOG_TAG, "miui plus version not support one hop share");
                        return;
                    }
                    if (intExtra < 0 || TextUtils.isEmpty(stringExtra)) {
                        Log.e(OneHopHelper.LOG_TAG, "OneHop receive, params is illegal: deviceType:" + intExtra + " btMac:" + stringExtra);
                        return;
                    }
                    WeakReference weakReference2 = weakReference;
                    if (weakReference2 == null || weakReference2.get() == null || !((OneHopShareCallback) weakReference.get()).onOneHopShare(intExtra, stringExtra, i)) {
                        return;
                    }
                    abortBroadcast();
                }
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.setPriority(1000);
        intentFilter.addAction(ACTION_ONEHOP_MIRROR);
        context.getApplicationContext().registerReceiver(this.mOneHopShareReceiver, intentFilter, PERMISSION_ONEHOP_MIRROR, null);
    }

    public void unregisterOneHopShareReceiver(Context context) {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            Log.w(LOG_TAG, "unregister oneHopShareReceiver must in mainThread");
        } else if (this.mOneHopShareReceiver != null) {
            context.getApplicationContext().unregisterReceiver(this.mOneHopShareReceiver);
            this.mOneHopShareReceiver = null;
        }
    }
}
