package com.miui.mishare.tapshare;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/* JADX INFO: loaded from: classes3.dex */
public class NfcTouchManager {
    public static final boolean ABORT_BROADCAST = true;
    private static final String ACTION_MISHARE_ONEHOP = "com.miui.mishare.action.NFC_TOUCH_TRANSFER";
    private static final String ACTION_ONEHOP_MIRROR = "com.miui.onehop.action.MIRROR";
    static final String ARG_BT_MAC = "btMac";
    static final String ARG_DEVICE_TYPE = "deviceType";
    static final String ARG_FILE_LIST = "fileList";
    static final String ARG_ONE_HOP_SHARE_PACKAGE = "oneHopSharePackage";
    static final String CALL_PROVIDER_AUTHORITY = "com.xiaomi.mirror.callprovider";
    private static final String EXTRA_ABILITY_LYRA = "_ability_lyra";
    private static final String EXTRA_BT_MAC = "_bt_mac";
    private static final String EXTRA_DEVICE_TYPE = "_device_type";
    private static final int FILESHARE_PRIORITY = 1000;
    private static final String METADATA_KEY_LYRA = "com.xiaomi.continuity.VERSION_NAME";
    private static final String METADATA_KEY_MIRROR = "com.xiaomi.mirror.nfc";
    private static final String METADATA_KEY_MISHARE = "com.miui.mishare.connectivity.nfc";
    private static final String METHOD_SEND_FILE_BY_BT_MAC = "sendFileByBtMac";
    public static final int ONE_HOP_VERSION_MIRROR = 0;
    public static final int ONE_HOP_VERSION_MISHARE = 1;
    private static final String PACKAGE_LYRA = "com.xiaomi.mi_connect_service";
    private static final String PACKAGE_MIRROR = "com.xiaomi.mirror";
    private static final String PACKAGE_MISHARE = "com.miui.mishare.connectivity";
    private static final String PERMISSION_ONEHOP_MIRROR = "com.miui.onehop.permission.MIRROR";
    public static final int SYNERGY_OK = 0;
    private static final String TAG = "NfcTouchManager";
    private static volatile NfcTouchManager instance;
    private BroadcastReceiver mOneHopShareReceiver;
    private boolean mSelfIsSupportLyra = false;
    private boolean mHasInitialize = false;
    private final Executor mExecutor = Executors.newSingleThreadExecutor();

    public interface OneHopShareCallback {
        boolean onOneHopShare(int i, String str, int i2);
    }

    public static NfcTouchManager getInstance() {
        if (instance == null) {
            synchronized (NfcTouchManager.class) {
                if (instance == null) {
                    instance = new NfcTouchManager();
                }
            }
        }
        return instance;
    }

    private NfcTouchManager() {
    }

    protected void registerOneHopShareReceiver(Context context, final OneHopShareCallback oneHopShareCallback) {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            Log.w(TAG, "register oneHopShareReceiver must in mainThread");
            return;
        }
        if (!this.mHasInitialize) {
            this.mSelfIsSupportLyra = checkIsSupportLyra(context);
            this.mHasInitialize = true;
        }
        if (this.mOneHopShareReceiver != null) {
            Log.w(TAG, "had register nfc touch broadcast, not register again!");
            return;
        }
        this.mOneHopShareReceiver = new BroadcastReceiver() { // from class: com.miui.mishare.tapshare.NfcTouchManager.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                if ("com.miui.onehop.action.MIRROR".equals(intent.getAction())) {
                    int intExtra = intent.getIntExtra("_device_type", -1);
                    String stringExtra = intent.getStringExtra("_bt_mac");
                    int i = 0;
                    boolean booleanExtra = intent.getBooleanExtra("_ability_lyra", false);
                    Log.d(NfcTouchManager.TAG, "received nfc touch broadcast, mSelfIsSupportLyra: " + NfcTouchManager.this.mSelfIsSupportLyra + ", abilityLyra: " + booleanExtra);
                    if (!NfcTouchManager.this.mSelfIsSupportLyra || !booleanExtra) {
                        if (!NfcTouchManager.this.checkAbilitySupport(context2, "com.xiaomi.mirror", "com.xiaomi.mirror.nfc")) {
                            Log.w(NfcTouchManager.TAG, "miui plus version not support one hop share");
                            return;
                        }
                    } else {
                        if (!NfcTouchManager.this.checkAbilitySupport(context2, "com.miui.mishare.connectivity", "com.miui.mishare.connectivity.nfc")) {
                            Log.w(NfcTouchManager.TAG, "mishare version not support one hop share");
                            return;
                        }
                        i = 1;
                    }
                    if (intExtra >= 0 && !TextUtils.isEmpty(stringExtra)) {
                        Log.i(NfcTouchManager.TAG, "notify mishare handle nfc touch event!");
                        boolean zOnOneHopShare = oneHopShareCallback.onOneHopShare(intExtra, stringExtra, i);
                        Log.i(NfcTouchManager.TAG, "is need abort broadcast: " + zOnOneHopShare);
                        if (zOnOneHopShare) {
                            Log.i(NfcTouchManager.TAG, "handler by mishare, abort broadcast!");
                            abortBroadcast();
                            return;
                        }
                        return;
                    }
                    Log.e(NfcTouchManager.TAG, "OneHop receive, params is illegal: deviceType:" + intExtra + " btMac:" + stringExtra);
                }
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.setPriority(1000);
        intentFilter.addAction("com.miui.onehop.action.MIRROR");
        if (Build.VERSION.SDK_INT >= 33) {
            context.getApplicationContext().registerReceiver(this.mOneHopShareReceiver, intentFilter, "com.miui.onehop.permission.MIRROR", null, 2);
        } else {
            context.getApplicationContext().registerReceiver(this.mOneHopShareReceiver, intentFilter, "com.miui.onehop.permission.MIRROR", null);
        }
        Log.i(TAG, "register nfc touch broadcast!");
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
        Bundle metaData = getMetaData(context, "com.xiaomi.mi_connect_service");
        return (metaData == null || metaData.getString("com.xiaomi.continuity.VERSION_NAME", null) == null) ? false : true;
    }

    private Bundle getMetaData(Context context, String str) {
        ApplicationInfo applicationInfo;
        try {
            applicationInfo = context.getPackageManager().getApplicationInfo(str, 128);
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "name not found", e);
            applicationInfo = null;
        }
        if (applicationInfo != null) {
            return applicationInfo.metaData;
        }
        return null;
    }

    protected int sendFileToDeviceByBtMac(Context context, String str, int i, ArrayList<Uri> arrayList, int i2) {
        if (i2 == 0) {
            Log.d(TAG, "oneHopSendFileByMirror");
            final Bundle bundle = new Bundle();
            bundle.putString("btMac", str);
            bundle.putInt("deviceType", i);
            bundle.putParcelableArrayList("fileList", arrayList);
            final ContentResolver contentResolver = context.getContentResolver();
            this.mExecutor.execute(new Runnable() { // from class: com.miui.mishare.tapshare.NfcTouchManager.2
                @Override // java.lang.Runnable
                public void run() {
                    NfcTouchManager.this.doCall(contentResolver, "sendFileByBtMac", null, bundle);
                }
            });
            return 0;
        }
        if (i2 != 1) {
            return 0;
        }
        Log.d(TAG, "oneHopSendFileByMiShare");
        String packageName = context.getApplicationContext().getPackageName();
        Intent intent = new Intent("com.miui.mishare.action.NFC_TOUCH_TRANSFER");
        intent.setPackage("com.miui.mishare.connectivity");
        intent.setFlags(1);
        Bundle bundle2 = new Bundle();
        bundle2.putString("btMac", str);
        Iterator<Uri> it = arrayList.iterator();
        while (it.hasNext()) {
            context.grantUriPermission("com.miui.mishare.connectivity", it.next(), 1);
        }
        bundle2.putParcelableArrayList("fileList", arrayList);
        bundle2.putInt("deviceType", i);
        bundle2.putString("oneHopSharePackage", packageName);
        intent.putExtras(bundle2);
        context.startForegroundService(intent);
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Bundle doCall(ContentResolver contentResolver, String str, String str2, Bundle bundle) {
        try {
            return contentResolver.call("com.xiaomi.mirror.callprovider", str, str2, bundle);
        } catch (IllegalArgumentException | NullPointerException e) {
            e.printStackTrace();
            return null;
        }
    }
}
