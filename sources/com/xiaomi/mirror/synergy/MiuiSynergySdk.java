package com.xiaomi.mirror.synergy;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.ParcelFileDescriptor;
import android.text.TextUtils;
import android.util.Log;
import com.xiaomi.mirror.IMirrorAdvConnectionCallback;
import com.xiaomi.mirror.IMirrorShareCallback;
import com.xiaomi.mirror.ISameAccountApCallback;
import com.xiaomi.mirror.MirrorDesktopInfo;
import com.xiaomi.mirror.RemoteDeviceInfo;
import com.xiaomi.mirror.SynergyDeviceInfo;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/* JADX INFO: loaded from: classes4.dex */
public class MiuiSynergySdk {
    public static final String ACTION_MIRROR_DEVICE_CHANGED = "miui.intent.action.MIRROR_DEVICE_CHANGED";
    public static final String ACTION_MISHARE_ONEHOP = "com.miui.mishare.action.NFC_TOUCH_TRANSFER";
    public static final String EXTRA_DEVICE_ID = "device_id";
    public static final String EXTRA_DEVICE_MANUFACTURER = "device_manufacturer";
    public static final String EXTRA_DEVICE_STATE = "device_state";
    public static final String PACKAGE_MISHARE = "com.miui.mishare.connectivity";
    public static final int SEND_APP_FAIL_APP_SENDING = -2;
    public static final int SEND_APP_FAIL_DEVICE_DISCONNECT = -5;
    public static final int SEND_APP_FAIL_PARAMETER_ERROR = -1;
    public static final int SEND_APP_FAIL_REMOTE_ADV_ERROR = -7;
    public static final int SEND_APP_FAIL_REMOTE_BUSY_MIPLAY = -103;
    public static final int SEND_APP_FAIL_REMOTE_BUSY_MIRROR = -102;
    public static final int SEND_APP_FAIL_REMOTE_BUSY_MISHARE = -101;
    public static final int SEND_APP_FAIL_REMOTE_BUSY_OTHERS = -100;
    public static final int SEND_APP_FAIL_REMOTE_BUSY_WORLD = -104;
    public static final int SEND_APP_FAIL_REMOTE_MAX_SCREEN_COUNT = -10;
    public static final int SEND_APP_FAIL_REMOTE_PKG_ERROR = -9;
    public static final int SEND_APP_FAIL_REMOTE_SYNC_ERROR = -8;
    public static final int SEND_APP_FAIL_REMOTE_UNKNOWN = -6;
    public static final int SEND_APP_FAIL_SELF_DEVICE = -3;
    public static final int SEND_APP_FAIL_UNKNOWN = 0;
    public static final int SEND_APP_FAIL_WAIT_TIMEOUT = -4;
    public static final int SEND_APP_SUCCESS = 1;
    public static final int SYNERGY_ERR = -1;
    public static final int SYNERGY_OK = 0;
    public static final int SYNERGY_SOFTAP_ALREADY_CONNECTED = 2;
    public static final int SYNERGY_SOFTAP_ALREADY_CONNECTING = 1;
    public static final String TAG = "MiuiSynergy";
    public static final int VALUE_DEVICE_STATE_EXIT = 2;
    public static final int VALUE_DEVICE_STATE_JOIN = 0;
    public static final int VALUE_DEVICE_STATE_UPDATE = 1;
    public BroadcastReceiver mReceiver;
    public CopyOnWriteArrayList<IRemoteDeviceListener> mRemoteDeviceListeners;
    public SameAccountApCallback mSameAccountApCallback;
    public WeakReference<MirrorShareCallback> mShareCallback;
    public TakingPhotoManager mTakingPhotoManager;
    public final Map<String, RelayAppCallback> mRelayAppCallbackMap = new ConcurrentHashMap();
    public final Executor mExecutor = Executors.newCachedThreadPool();
    public final ISameAccountApCallback mISameAccountApCallback = new ISameAccountApCallback.Stub() { // from class: com.xiaomi.mirror.synergy.MiuiSynergySdk.5
        @Override // com.xiaomi.mirror.ISameAccountApCallback
        public void onApConnectedStatusUpdate(int i, Bundle bundle) {
            if (MiuiSynergySdk.this.mSameAccountApCallback != null) {
                MiuiSynergySdk.this.mSameAccountApCallback.onApConnectedStatusUpdate(i, bundle == null ? null : new SameAccountAccessPoint(bundle));
            }
        }

        @Override // com.xiaomi.mirror.ISameAccountApCallback
        public void onApInfoUpdate(Bundle bundle) {
            if (MiuiSynergySdk.this.mSameAccountApCallback != null) {
                MiuiSynergySdk.this.mSameAccountApCallback.onApInfoUpdate(bundle == null ? null : new SameAccountAccessPoint(bundle));
            }
        }
    };

    @Deprecated
    public IRemoteDeviceListener mRemoteDeviceListener = null;
    public AdvConnectionCallbackWrapper mMirrorAdvConnectionCallback = null;

    public interface AdvConnectionCallback {
        void onAdvConnectionLost();
    }

    public class AdvConnectionCallbackWrapper extends IMirrorAdvConnectionCallback.Stub {
        public final WeakReference<AdvConnectionCallback> mCallback;

        public AdvConnectionCallbackWrapper(AdvConnectionCallback advConnectionCallback) {
            this.mCallback = new WeakReference<>(advConnectionCallback);
        }

        @Override // com.xiaomi.mirror.IMirrorAdvConnectionCallback
        public void onLost() {
            MiuiSynergySdk.this.mMirrorAdvConnectionCallback = null;
            if (this.mCallback.get() != null) {
                this.mCallback.get().onAdvConnectionLost();
            }
        }
    }

    public interface ChooseFileCallback {
        void onFileChosen(ClipData clipData);
    }

    public class DeviceReceiver extends BroadcastReceiver {
        public DeviceReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            CopyOnWriteArrayList<IRemoteDeviceListener> copyOnWriteArrayList;
            String action = intent.getAction();
            if (action == null || !MiuiSynergySdk.ACTION_MIRROR_DEVICE_CHANGED.equals(action) || (copyOnWriteArrayList = MiuiSynergySdk.this.mRemoteDeviceListeners) == null || copyOnWriteArrayList.isEmpty()) {
                return;
            }
            int intExtra = intent.getIntExtra("device_state", -1);
            String stringExtra = intent.getStringExtra("device_id");
            String stringExtra2 = intent.getStringExtra(MiuiSynergySdk.EXTRA_DEVICE_MANUFACTURER);
            if (TextUtils.isEmpty(stringExtra)) {
                for (IRemoteDeviceListener iRemoteDeviceListener : copyOnWriteArrayList) {
                    if (iRemoteDeviceListener.enableHistoryListener()) {
                        iRemoteDeviceListener.onUpdate("");
                    }
                }
                return;
            }
            Log.d("MiuiSynergy", "ACTION_MIRROR_DEVICE_CHANGED received. state=" + intExtra + ",id=" + stringExtra);
            if (intExtra == 0) {
                for (IRemoteDeviceListener iRemoteDeviceListener2 : copyOnWriteArrayList) {
                    String listenManufacturer = iRemoteDeviceListener2.getListenManufacturer();
                    if (listenManufacturer == null || listenManufacturer.equals(stringExtra2)) {
                        iRemoteDeviceListener2.onFound(stringExtra);
                    }
                }
                return;
            }
            if (intExtra == 1) {
                for (IRemoteDeviceListener iRemoteDeviceListener3 : copyOnWriteArrayList) {
                    String listenManufacturer2 = iRemoteDeviceListener3.getListenManufacturer();
                    if (listenManufacturer2 == null || listenManufacturer2.equals(stringExtra2)) {
                        iRemoteDeviceListener3.onUpdate(stringExtra);
                    }
                }
                return;
            }
            if (intExtra != 2) {
                return;
            }
            for (IRemoteDeviceListener iRemoteDeviceListener4 : copyOnWriteArrayList) {
                String listenManufacturer3 = iRemoteDeviceListener4.getListenManufacturer();
                if (listenManufacturer3 == null || listenManufacturer3.equals(stringExtra2)) {
                    iRemoteDeviceListener4.onLost(stringExtra);
                }
            }
        }
    }

    public static final class Holder {
        public static final MiuiSynergySdk INSTANCE = new MiuiSynergySdk();
    }

    public static abstract class IRemoteDeviceListener {
        public boolean enableHistoryListener() {
            return false;
        }

        public String getListenManufacturer() {
            return RemoteDeviceInfo.MANUFACTURER_XIAOMI;
        }

        public abstract void onFound(String str);

        public abstract void onLost(String str);

        public abstract void onUpdate(String str);
    }

    public static class Option {
        public Bitmap icon;
        public String id;
        public String title;

        public int invoke(Activity activity, Uri uri, String str) {
            Bundle bundle = new Bundle();
            bundle.putParcelable(CallMethod.ARG_URI, uri);
            bundle.putString(CallMethod.ARG_EXTRA_STRING, str);
            bundle.putString("id", this.id);
            bundle.putInt(CallMethod.ARG_DISPLAY_ID, activity.getWindow().getDecorView().getDisplay().getDisplayId());
            try {
                CallMethod.doCall(activity.getContentResolver(), CallMethod.METHOD_OPEN_ON_SYNERGY, null, bundle);
                return 0;
            } catch (Exception e) {
                e.printStackTrace();
                return -1;
            }
        }
    }

    public interface QueryOpenCallback {
        void onQueryResult(List<Option> list);
    }

    public interface RelayAppCallback {
        void onFailure(int i);

        void onSuccess();
    }

    public static class SameAccountAccessPoint {
        public int batteryPercent;
        public boolean is5G;
        public String ssid;

        public SameAccountAccessPoint(Bundle bundle) {
            this.batteryPercent = -1;
            if (bundle == null) {
                return;
            }
            this.ssid = bundle.getString("apSsid");
            this.is5G = bundle.getBoolean(CallMethod.RESULT_AP_IS5G);
            this.batteryPercent = bundle.getInt(CallMethod.RESULT_BATTERY_PERCENT, -1);
        }

        public int getBatteryPercent() {
            return this.batteryPercent;
        }

        public String getSsid() {
            return this.ssid;
        }

        public boolean isIs5G() {
            return this.is5G;
        }
    }

    public interface SameAccountApCallback {
        void onApConnectedStatusUpdate(int i, SameAccountAccessPoint sameAccountAccessPoint);

        void onApInfoUpdate(SameAccountAccessPoint sameAccountAccessPoint);
    }

    public static MiuiSynergySdk getInstance() {
        return Holder.INSTANCE;
    }

    public static Uri getUriFor(String str) {
        return new Uri.Builder().scheme("content").authority(CallMethod.CALL_PROVIDER_AUTHORITY).path(str).build();
    }

    private List<RemoteDeviceInfo> queryRemoteDevices(Context context, String str, boolean z) {
        try {
            Bundle bundle = new Bundle();
            bundle.putBoolean(CallMethod.ARG_REMOTE_HISTORY, z);
            bundle.putString(CallMethod.ARG_REMOTE_DEVICE_MANUFACTURER, str);
            Bundle bundleDoCall = CallMethod.doCall(context.getContentResolver(), CallMethod.METHOD_QUERY_REMOTE_DEVICES, null, bundle);
            bundleDoCall.setClassLoader(RemoteDeviceInfo.class.getClassLoader());
            ArrayList parcelableArrayList = bundleDoCall.getParcelableArrayList(CallMethod.RESULT_REMOTE_DEVICES);
            if (parcelableArrayList == null || parcelableArrayList.size() == 0) {
                return null;
            }
            return parcelableArrayList;
        } catch (Exception unused) {
        }
    }

    private void setRemoteDeviceListener(Context context, String str, IRemoteDeviceListener iRemoteDeviceListener) {
        if (iRemoteDeviceListener != null) {
            addRemoteDeviceListener(context, iRemoteDeviceListener);
        } else {
            removeRemoteDeviceListener(context, this.mRemoteDeviceListener);
        }
        this.mRemoteDeviceListener = iRemoteDeviceListener;
    }

    public void addRemoteDeviceListener(Context context, IRemoteDeviceListener iRemoteDeviceListener) {
        if (this.mRemoteDeviceListeners == null) {
            this.mRemoteDeviceListeners = new CopyOnWriteArrayList<>();
        }
        if (this.mReceiver == null) {
            this.mReceiver = new DeviceReceiver();
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(ACTION_MIRROR_DEVICE_CHANGED);
            context.getApplicationContext().registerReceiver(this.mReceiver, intentFilter);
        }
        this.mRemoteDeviceListeners.add(iRemoteDeviceListener);
    }

    public int chooseFileOnSynergy(Activity activity, final ChooseFileCallback chooseFileCallback) {
        final Bundle bundle = new Bundle();
        bundle.putInt(CallMethod.ARG_DISPLAY_ID, activity.getWindow().getDecorView().getDisplay().getDisplayId());
        final ContentResolver contentResolver = activity.getContentResolver();
        this.mExecutor.execute(new Runnable() { // from class: com.xiaomi.mirror.synergy.MiuiSynergySdk.2
            @Override // java.lang.Runnable
            public void run() {
                Bundle bundleDoCall = CallMethod.doCall(contentResolver, CallMethod.METHOD_CHOOSE_FILE_FROM_SYNERGY, null, bundle);
                chooseFileCallback.onFileChosen(bundleDoCall != null ? (ClipData) bundleDoCall.getParcelable("clipData") : null);
            }
        });
        return 0;
    }

    public int closeMirrorFromClient(Context context) {
        try {
            CallMethod.doCall(context.getContentResolver(), CallMethod.METHOD_CLOSE_MIRROR_FROM_CLIENT, null, null);
            return 0;
        } catch (Exception unused) {
            return -1;
        }
    }

    @Deprecated
    public int closeRemoteDeviceMirror(Context context, String str) {
        Bundle bundle = new Bundle();
        bundle.putString(CallMethod.ARG_REMOTE_DEVICE_ID, str);
        try {
            CallMethod.doCall(context.getContentResolver(), CallMethod.METHOD_CLOSE_REMOTE_DEVICE_MIRROR, null, bundle);
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public int closeRemoteDeviceMirror2(Context context, String str) {
        Bundle bundle = new Bundle();
        bundle.putString(CallMethod.ARG_REMOTE_DEVICE_ID, str);
        bundle.putInt(CallMethod.ARG_METHOD_VERSION, 2);
        this.mRelayAppCallbackMap.remove(str);
        try {
            CallMethod.doCall(context.getContentResolver(), CallMethod.METHOD_CLOSE_REMOTE_DEVICE_MIRROR, null, bundle);
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public int connectSameAccountAp(Context context, String str) {
        Bundle bundle = new Bundle();
        bundle.putString("apSsid", str);
        try {
            Bundle bundleDoCall = CallMethod.doCall(context.getContentResolver(), CallMethod.METHOD_CONNECT_SAME_ACCOUNT_AP, null, bundle);
            if (bundleDoCall == null) {
                return -1;
            }
            return bundleDoCall.getInt(CallMethod.RESULT_SOFTAP_STATE, -1);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public IBinder getAliveBinder(Context context) {
        Bundle bundleDoCall = CallMethod.doCall(context.getContentResolver(), CallMethod.METHOD_GET_ALIVE_BINDER, null, null);
        if (bundleDoCall == null) {
            return null;
        }
        return bundleDoCall.getBinder(CallMethod.RESULT_BINDER);
    }

    public CallRelayService getCallRelayService(Context context) {
        IBinder binder;
        Bundle bundleDoCall = CallMethod.doCall(context.getContentResolver(), CallMethod.METHOD_GET_CALL_RELAY_SERVICE, null, null);
        if (bundleDoCall == null || (binder = bundleDoCall.getBinder(CallMethod.RESULT_BINDER)) == null) {
            return null;
        }
        return new CallRelayService(binder);
    }

    public int getInt(Context context, Uri uri, int i) {
        String string = getString(context, uri, null);
        if (string != null) {
            try {
                return Integer.parseInt(string);
            } catch (NumberFormatException unused) {
            }
        }
        return i;
    }

    public int getMirrorAdvConnection(Context context, AdvConnectionCallback advConnectionCallback) {
        if (this.mMirrorAdvConnectionCallback != null) {
            return -1;
        }
        this.mMirrorAdvConnectionCallback = new AdvConnectionCallbackWrapper(advConnectionCallback);
        Bundle bundle = new Bundle();
        bundle.putBinder(CallMethod.ARG_CALLBACK, this.mMirrorAdvConnectionCallback.asBinder());
        try {
            return CallMethod.doCall(context.getContentResolver(), CallMethod.METHOD_GET_MIRROR_ADV_CONNECTION, null, bundle) != null ? 0 : -1;
        } catch (Exception unused) {
        }
    }

    public PackageInfo getRemotePackageInfo(Context context, String str, String str2) throws PackageManager.NameNotFoundException {
        Bundle bundle = new Bundle();
        bundle.putString("package", str);
        Bundle bundleDoCall = CallMethod.doCall(context.getContentResolver(), CallMethod.METHOD_GET_REMOTE_PACKAGE_INFO, str2, bundle);
        if (bundleDoCall == null) {
            throw new RuntimeException("local miui+ not support");
        }
        if (bundleDoCall.getInt("result") != 0) {
            throw new RuntimeException(bundleDoCall.getString("message", ""));
        }
        PackageInfo packageInfo = (PackageInfo) bundleDoCall.getParcelable(CallMethod.ARG_PACKAGE_INFO);
        if (packageInfo != null) {
            return packageInfo;
        }
        throw new PackageManager.NameNotFoundException(str + " not found in remote device");
    }

    public String getShareStatus(Context context) {
        Bundle bundleDoCall = CallMethod.doCall(context.getContentResolver(), CallMethod.METHOD_GET_SHARE_STATUS, null, null);
        if (bundleDoCall != null) {
            return bundleDoCall.getString("value");
        }
        throw new RuntimeException("local miui+ not support");
    }

    public int getShowMirrorType(Context context, String str) {
        Bundle bundle = new Bundle();
        bundle.putString("id", str);
        try {
            Bundle bundleDoCall = CallMethod.doCall(context.getContentResolver(), CallMethod.METHOD_GET_SHOW_MIRROR_TYPE, null, bundle);
            if (bundleDoCall != null) {
                return bundleDoCall.getInt("value");
            }
            return -1;
        } catch (Exception unused) {
            return -1;
        }
    }

    public String getString(Context context, Uri uri, String str) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(CallMethod.ARG_URI, uri);
        try {
            Bundle bundleDoCall = CallMethod.doCall(context.getContentResolver(), CallMethod.METHOD_GET, null, bundle);
            return bundleDoCall == null ? str : bundleDoCall.getString("value");
        } catch (Exception e) {
            e.printStackTrace();
            return str;
        }
    }

    public String getSubScreenAppName(Context context, String str) {
        Bundle bundle = new Bundle();
        bundle.putString("id", str);
        try {
            Bundle bundleDoCall = CallMethod.doCall(context.getContentResolver(), CallMethod.METHOD_GET_SUB_SCREEN_APP_NAME, null, bundle);
            if (bundleDoCall != null) {
                return bundleDoCall.getString("value");
            }
        } catch (Exception unused) {
        }
        return null;
    }

    public List<SynergyDeviceInfo> getSynergyDevices(Context context) {
        try {
            Bundle bundleDoCall = CallMethod.doCall(context.getContentResolver(), CallMethod.METHOD_GET_SYNERGY_DEVICES, null, null);
            bundleDoCall.setClassLoader(SynergyDeviceInfo.class.getClassLoader());
            ArrayList parcelableArrayList = bundleDoCall.getParcelableArrayList(CallMethod.RESULT_REMOTE_DEVICES);
            if (parcelableArrayList == null || parcelableArrayList.isEmpty()) {
                return null;
            }
            return parcelableArrayList;
        } catch (Exception unused) {
        }
    }

    public boolean isAgreePrivacy(Context context) {
        try {
            Bundle bundleDoCall = CallMethod.doCall(context.getContentResolver(), CallMethod.METHOD_IS_AGREE_PRIVACY, null, null);
            if (bundleDoCall == null) {
                bundleDoCall = CallMethod.doCall(context.getContentResolver(), CallMethod.METHOD_IS_AGREE_PRIVACY, null, null);
                Log.w("MiuiSynergy", "isAgreePrivacy, fix binder error, result=" + bundleDoCall);
            }
            return bundleDoCall != null && bundleDoCall.getBoolean("enable");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isFloatWindowShow(Context context) {
        try {
            Bundle bundleDoCall = CallMethod.doCall(context.getContentResolver(), "isFloatWindowShow", null, null);
            return bundleDoCall != null && bundleDoCall.getBoolean("isFloatWindowShow");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isFreedomWindow(Context context) {
        try {
            Bundle bundleDoCall = CallMethod.doCall(context.getContentResolver(), CallMethod.METHOD_IS_FREEDOM_WINDOW, null, null);
            return bundleDoCall != null && bundleDoCall.getBoolean("enable");
        } catch (Exception unused) {
            return false;
        }
    }

    public boolean isP2PWorking(Context context) {
        try {
            Bundle bundleDoCall = CallMethod.doCall(context.getContentResolver(), CallMethod.METHOD_IS_P2P_WORKING, null, null);
            return bundleDoCall != null && bundleDoCall.getBoolean("enable");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isShowMirrorFromPhone(Context context) {
        try {
            Bundle bundleDoCall = CallMethod.doCall(context.getContentResolver(), CallMethod.METHOD_IS_SHOW_MIRROR_FROM_PHONE, null, null);
            return bundleDoCall != null && bundleDoCall.getBoolean("enable");
        } catch (Exception unused) {
            return false;
        }
    }

    public boolean isSplitWindow(Context context) {
        try {
            Bundle bundleDoCall = CallMethod.doCall(context.getContentResolver(), CallMethod.METHOD_IS_SPLIT_WINDOW, null, null);
            return bundleDoCall != null && bundleDoCall.getBoolean("enable");
        } catch (Exception unused) {
            return false;
        }
    }

    public boolean isSupportSendApp(Context context, String str) {
        Bundle bundle = new Bundle();
        bundle.putString("package", str);
        try {
            Bundle bundleDoCall = CallMethod.doCall(context.getContentResolver(), CallMethod.METHOD_IS_SUPPORT_SEND_APP, null, bundle);
            return bundleDoCall != null && bundleDoCall.getBoolean("enable");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isSupportSendAppToPhone(Context context, String str) {
        Bundle bundle = new Bundle();
        bundle.putString("package", str);
        try {
            Bundle bundleDoCall = CallMethod.doCall(context.getContentResolver(), CallMethod.METHOD_IS_SUPPORT_SEND_APP_TO_PHONE, null, bundle);
            return bundleDoCall != null && bundleDoCall.getBoolean("enable");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isSupportTakePhoto(Context context) {
        return isSupportTakePhoto(context, false);
    }

    public boolean isSupportTakePhoto(Context context, boolean z) {
        try {
            Bundle bundle = new Bundle();
            bundle.putBoolean(CallMethod.ARG_INCLUDE_LYRA, z);
            Bundle bundleDoCall = CallMethod.doCall(context.getContentResolver(), CallMethod.METHOD_IS_SUPPORT_TAKE_PHOTO, null, bundle);
            return bundleDoCall != null && bundleDoCall.getBoolean("enable");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isSynergyEnable(Context context) {
        try {
            Bundle bundleDoCall = CallMethod.doCall(context.getContentResolver(), CallMethod.METHOD_IS_SYNERGY_ENABLE, null, null);
            return bundleDoCall != null && bundleDoCall.getBoolean("enable");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isTakingPhoto(Context context, String str) {
        Bundle bundle = new Bundle();
        bundle.putString("id", str);
        Bundle bundleDoCall = CallMethod.doCall(context.getContentResolver(), CallMethod.METHOD_IS_TAKING_PHOTO, null, bundle);
        if (bundleDoCall != null) {
            return bundleDoCall.getBoolean("value");
        }
        throw new RuntimeException("current miui+ version not support");
    }

    public ParcelFileDescriptor openDirect(Context context, Uri uri) throws IOException {
        Bundle bundle = new Bundle();
        bundle.putParcelable(CallMethod.ARG_URI, uri);
        try {
            Bundle bundleDoCall = CallMethod.doCall(context.getContentResolver(), CallMethod.METHOD_OPEN_DIRECT, null, bundle);
            if (bundleDoCall == null) {
                return null;
            }
            return (ParcelFileDescriptor) bundleDoCall.getParcelable(CallMethod.RESULT_FILE_DESCRIPTOR);
        } catch (Exception e) {
            throw new IOException("open failed", e);
        }
    }

    public int openMiCloudOnSynergy(Context context) {
        try {
            CallMethod.doCall(context.getContentResolver(), CallMethod.METHOD_OPEN_MI_CLOUD_ON_SYNERGY, null, null);
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public int openOnSynergy(Activity activity, Uri uri, String str) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(CallMethod.ARG_URI, uri);
        bundle.putString(CallMethod.ARG_EXTRA_STRING, str);
        bundle.putInt(CallMethod.ARG_DISPLAY_ID, activity.getWindow().getDecorView().getDisplay().getDisplayId());
        try {
            CallMethod.doCall(activity.getContentResolver(), CallMethod.METHOD_OPEN_ON_SYNERGY, null, bundle);
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Deprecated
    public void openRemoteDeviceMirror(Context context, String str, final RelayAppCallback relayAppCallback) {
        final Bundle bundle = new Bundle();
        bundle.putString(CallMethod.ARG_REMOTE_DEVICE_ID, str);
        final ContentResolver contentResolver = context.getContentResolver();
        this.mExecutor.execute(new Runnable() { // from class: com.xiaomi.mirror.synergy.MiuiSynergySdk.10
            @Override // java.lang.Runnable
            public void run() {
                Bundle bundleDoCall = null;
                try {
                    bundleDoCall = CallMethod.doCall(contentResolver, CallMethod.METHOD_OPEN_REMOTE_DEVICE_MIRROR, null, bundle);
                } catch (Exception unused) {
                }
                if (relayAppCallback != null) {
                    int i = bundleDoCall != null ? bundleDoCall.getInt("value", -1) : -1;
                    RelayAppCallback relayAppCallback2 = relayAppCallback;
                    if (i == 0) {
                        relayAppCallback2.onSuccess();
                    } else {
                        relayAppCallback2.onFailure(i);
                    }
                }
            }
        });
    }

    public void openRemoteDeviceMirror2(Context context, final String str, RelayAppCallback relayAppCallback) {
        final Bundle bundle = new Bundle();
        bundle.putString(CallMethod.ARG_REMOTE_DEVICE_ID, str);
        bundle.putInt(CallMethod.ARG_METHOD_VERSION, 2);
        this.mRelayAppCallbackMap.put(str, relayAppCallback);
        final ContentResolver contentResolver = context.getContentResolver();
        this.mExecutor.execute(new Runnable() { // from class: com.xiaomi.mirror.synergy.MiuiSynergySdk.9
            @Override // java.lang.Runnable
            public void run() {
                Bundle bundleDoCall = null;
                try {
                    bundleDoCall = CallMethod.doCall(contentResolver, CallMethod.METHOD_OPEN_REMOTE_DEVICE_MIRROR, null, bundle);
                } catch (Exception unused) {
                }
                RelayAppCallback relayAppCallback2 = (RelayAppCallback) MiuiSynergySdk.this.mRelayAppCallbackMap.get(str);
                if (relayAppCallback2 != null) {
                    int i = bundleDoCall != null ? bundleDoCall.getInt("value", -1) : -1;
                    if (i == 0) {
                        relayAppCallback2.onSuccess();
                    } else {
                        relayAppCallback2.onFailure(i);
                    }
                    MiuiSynergySdk.this.mRelayAppCallbackMap.remove(str);
                }
            }
        });
    }

    public void openRemoteDeviceMirrorByBtMac(Context context, final String str, int i, RelayAppCallback relayAppCallback, boolean z) {
        final Bundle bundle = new Bundle();
        bundle.putString(CallMethod.ARG_BT_MAC, str);
        bundle.putInt("deviceType", i);
        bundle.putBoolean(CallMethod.ARG_REMOTE_SUPPORT_LYRA, z);
        this.mRelayAppCallbackMap.put(str, relayAppCallback);
        final ContentResolver contentResolver = context.getContentResolver();
        this.mExecutor.execute(new Runnable() { // from class: com.xiaomi.mirror.synergy.MiuiSynergySdk.7
            @Override // java.lang.Runnable
            public void run() {
                Bundle bundleDoCall = null;
                try {
                    bundleDoCall = CallMethod.doCall(contentResolver, CallMethod.METHOD_OPEN_REMOTE_DEVICE_MIRROR_BY_BT_MAC, null, bundle);
                } catch (Exception unused) {
                }
                RelayAppCallback relayAppCallback2 = (RelayAppCallback) MiuiSynergySdk.this.mRelayAppCallbackMap.get(str);
                if (relayAppCallback2 != null) {
                    int i2 = bundleDoCall != null ? bundleDoCall.getInt("value", -1) : -1;
                    if (i2 == 0) {
                        relayAppCallback2.onSuccess();
                    } else {
                        relayAppCallback2.onFailure(i2);
                    }
                }
            }
        });
    }

    public List<RemoteDeviceInfo> queryAllRemoteDevices(Context context, boolean z) {
        return queryRemoteDevices(context, null, z);
    }

    public List<RemoteDeviceInfo> queryMirrorDesktopDevices(Context context) {
        try {
            Bundle bundleDoCall = CallMethod.doCall(context.getContentResolver(), CallMethod.METHOD_QUERY_MIRROR_DEVICE_LIST, null, new Bundle());
            bundleDoCall.setClassLoader(MirrorDesktopInfo.class.getClassLoader());
            ArrayList parcelableArrayList = bundleDoCall.getParcelableArrayList(CallMethod.RESULT_MIRROR_DEVICE);
            if (parcelableArrayList == null || parcelableArrayList.size() == 0) {
                return null;
            }
            return parcelableArrayList;
        } catch (Exception unused) {
        }
    }

    public MirrorDesktopInfo queryMirrorDesktopInfo(Context context) {
        try {
            Bundle bundleDoCall = CallMethod.doCall(context.getContentResolver(), CallMethod.METHOD_QUERY_MIRROR_DESKTOP_INFO, null, new Bundle());
            bundleDoCall.setClassLoader(MirrorDesktopInfo.class.getClassLoader());
            return (MirrorDesktopInfo) bundleDoCall.getParcelable(CallMethod.RESULT_MIRROR_DESKTOP_INFO);
        } catch (Exception unused) {
            return null;
        }
    }

    public int queryOpenOnSynergy(Context context, String str, final QueryOpenCallback queryOpenCallback) {
        final Bundle bundle = new Bundle();
        bundle.putString(CallMethod.ARG_EXTENSION, str);
        final ContentResolver contentResolver = context.getContentResolver();
        this.mExecutor.execute(new Runnable() { // from class: com.xiaomi.mirror.synergy.MiuiSynergySdk.1
            @Override // java.lang.Runnable
            public void run() {
                Bundle bundleDoCall = CallMethod.doCall(contentResolver, CallMethod.METHOD_QUERY_OPEN_ON_SYNERGY, null, bundle);
                if (bundleDoCall == null) {
                    queryOpenCallback.onQueryResult(null);
                    return;
                }
                ArrayList<Bundle> parcelableArrayList = bundleDoCall.getParcelableArrayList(CallMethod.RESULT_OPTION_LIST);
                if (parcelableArrayList == null) {
                    queryOpenCallback.onQueryResult(null);
                    return;
                }
                ArrayList arrayList = new ArrayList(parcelableArrayList.size());
                for (Bundle bundle2 : parcelableArrayList) {
                    Option option = new Option();
                    option.id = bundle2.getString("id");
                    option.title = bundle2.getString("title");
                    option.icon = (Bitmap) bundle2.getParcelable(CallMethod.RESULT_ICON);
                    arrayList.add(option);
                }
                queryOpenCallback.onQueryResult(arrayList);
            }
        });
        return 0;
    }

    public RemoteDeviceInfo queryRemoteDevice(Context context, String str) {
        try {
            Bundle bundle = new Bundle();
            bundle.putString(CallMethod.ARG_REMOTE_DEVICE_ID, str);
            Bundle bundleDoCall = CallMethod.doCall(context.getContentResolver(), CallMethod.METHOD_QUERY_REMOTE_DEVICE, null, bundle);
            bundleDoCall.setClassLoader(RemoteDeviceInfo.class.getClassLoader());
            return (RemoteDeviceInfo) bundleDoCall.getParcelable(CallMethod.RESULT_REMOTE_DEVICE);
        } catch (Exception unused) {
            return null;
        }
    }

    public List<RemoteDeviceInfo> queryRemoteDevices(Context context, boolean z) {
        return queryRemoteDevices(context, RemoteDeviceInfo.MANUFACTURER_XIAOMI, z);
    }

    public SameAccountAccessPoint querySameAccountApInfo(Context context) {
        try {
            Bundle bundleDoCall = CallMethod.doCall(context.getContentResolver(), CallMethod.METHOD_QUERY_SAME_ACCOUNT_AP, null, null);
            if (bundleDoCall != null && !TextUtils.isEmpty(bundleDoCall.getString("apSsid"))) {
                return new SameAccountAccessPoint(bundleDoCall);
            }
        } catch (Exception unused) {
        }
        return null;
    }

    public int registerSameAccountApCallback(Context context, SameAccountApCallback sameAccountApCallback) {
        try {
            Bundle bundle = new Bundle();
            bundle.putBinder(CallMethod.ARG_AP_CALLBACK, this.mISameAccountApCallback.asBinder());
            this.mSameAccountApCallback = sameAccountApCallback;
            CallMethod.doCall(context.getContentResolver(), CallMethod.METHOD_REGISTER_AP_CALLBACK, null, bundle);
            return 0;
        } catch (Exception unused) {
            return -1;
        }
    }

    public int registerTakePhotoCallback(Context context, MirrorTakePhotoCallback mirrorTakePhotoCallback) {
        if (this.mTakingPhotoManager != null) {
            return -1;
        }
        TakingPhotoManager takingPhotoManager = new TakingPhotoManager();
        this.mTakingPhotoManager = takingPhotoManager;
        return takingPhotoManager.registerTakePhotoCallback(context, mirrorTakePhotoCallback);
    }

    public int releaseMirrorAdvConnection(Context context, AdvConnectionCallback advConnectionCallback) {
        AdvConnectionCallbackWrapper advConnectionCallbackWrapper = this.mMirrorAdvConnectionCallback;
        if (advConnectionCallbackWrapper == null || advConnectionCallbackWrapper.mCallback.get() != advConnectionCallback) {
            return -1;
        }
        Bundle bundle = new Bundle();
        bundle.putBinder(CallMethod.ARG_CALLBACK, this.mMirrorAdvConnectionCallback.asBinder());
        try {
            CallMethod.doCall(context.getContentResolver(), CallMethod.METHOD_RELEASE_MIRROR_ADV_CONNECTION, null, bundle);
        } catch (Exception unused) {
        }
        this.mMirrorAdvConnectionCallback = null;
        return 0;
    }

    public void removeRemoteDeviceListener(Context context, IRemoteDeviceListener iRemoteDeviceListener) {
        CopyOnWriteArrayList<IRemoteDeviceListener> copyOnWriteArrayList = this.mRemoteDeviceListeners;
        if (copyOnWriteArrayList == null) {
            return;
        }
        copyOnWriteArrayList.remove(iRemoteDeviceListener);
        if (this.mReceiver != null) {
            context.getApplicationContext().unregisterReceiver(this.mReceiver);
            this.mReceiver = null;
        }
    }

    public int saveToSynergy(Activity activity, ClipData clipData, String str) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("clipData", clipData);
        bundle.putString(CallMethod.ARG_EXTRA_STRING, str);
        bundle.putInt(CallMethod.ARG_DISPLAY_ID, activity.getWindow().getDecorView().getDisplay().getDisplayId());
        try {
            CallMethod.doCall(activity.getContentResolver(), CallMethod.METHOD_SAVE_TO_SYNERGY, null, bundle);
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public void sendAppToRemoteDevice(Context context, String str, int i, String str2, String str3, String str4, RelayAppCallback relayAppCallback) {
        sendAppToRemoteDevice(context, str, i, str2, str3, str4, false, relayAppCallback);
    }

    public void sendAppToRemoteDevice(Context context, String str, int i, String str2, String str3, String str4, boolean z, final RelayAppCallback relayAppCallback) {
        final Bundle bundle = new Bundle();
        bundle.putString(CallMethod.ARG_REMOTE_DEVICE_ID, str);
        bundle.putInt(CallMethod.ARG_TASK_ID, i);
        bundle.putString("package", str2);
        bundle.putString(CallMethod.ARG_APP_NAME, str3);
        bundle.putString("ref", str4);
        bundle.putBoolean(CallMethod.ARG_ONLY_CAST, z);
        final ContentResolver contentResolver = context.getContentResolver();
        this.mExecutor.execute(new Runnable() { // from class: com.xiaomi.mirror.synergy.MiuiSynergySdk.6
            @Override // java.lang.Runnable
            public void run() {
                Bundle bundleDoCall = CallMethod.doCall(contentResolver, CallMethod.METHOD_SEND_APP_TO_REMOTE_DEVICE, null, bundle);
                RelayAppCallback relayAppCallback2 = relayAppCallback;
                if (relayAppCallback2 != null) {
                    if (bundleDoCall == null) {
                        relayAppCallback2.onFailure(0);
                        return;
                    }
                    int i2 = bundleDoCall.getInt(CallMethod.RESULT_SEND_APP, 0);
                    RelayAppCallback relayAppCallback3 = relayAppCallback;
                    if (i2 == 1) {
                        relayAppCallback3.onSuccess();
                    } else {
                        relayAppCallback3.onFailure(i2);
                    }
                }
            }
        });
    }

    public int sendFileToDeviceByBtMac(Activity activity, String str, int i, ArrayList<Uri> arrayList, int i2) {
        if (i2 == 0) {
            Log.d("MiuiSynergy", "oneHopSendFileByMirror");
            final Bundle bundle = new Bundle();
            bundle.putString(CallMethod.ARG_BT_MAC, str);
            bundle.putInt("deviceType", i);
            bundle.putParcelableArrayList(CallMethod.ARG_FILE_LIST, arrayList);
            final ContentResolver contentResolver = activity.getContentResolver();
            this.mExecutor.execute(new Runnable() { // from class: com.xiaomi.mirror.synergy.MiuiSynergySdk.3
                @Override // java.lang.Runnable
                public void run() {
                    CallMethod.doCall(contentResolver, CallMethod.METHOD_SEND_FILE_BY_BT_MAC, null, bundle);
                }
            });
            return 0;
        }
        if (i2 != 1) {
            return 0;
        }
        String packageName = activity.getApplicationContext().getPackageName();
        Log.d("MiuiSynergy", "oneHopSendFileByMiShare");
        Intent intent = new Intent(ACTION_MISHARE_ONEHOP);
        intent.setPackage("com.miui.mishare.connectivity");
        intent.setFlags(1);
        Bundle bundle2 = new Bundle();
        bundle2.putString(CallMethod.ARG_BT_MAC, str);
        Iterator<Uri> it = arrayList.iterator();
        while (it.hasNext()) {
            activity.grantUriPermission("com.miui.mishare.connectivity", it.next(), 1);
        }
        bundle2.putParcelableArrayList(CallMethod.ARG_FILE_LIST, arrayList);
        bundle2.putInt("deviceType", i);
        bundle2.putString(CallMethod.ARG_ONE_HOP_SHARE_PACKAGE, packageName);
        intent.putExtras(bundle2);
        activity.startForegroundService(intent);
        return 0;
    }

    public int setAgreePrivacy(Context context, boolean z) {
        Bundle bundle = new Bundle();
        bundle.putBoolean("enable", z);
        try {
            CallMethod.doCall(context.getContentResolver(), CallMethod.METHOD_SET_AGREE_PRIVACY, null, bundle);
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Deprecated
    public void setAllRemoteDeviceListener(Context context, IRemoteDeviceListener iRemoteDeviceListener) {
        setRemoteDeviceListener(context, null, iRemoteDeviceListener);
    }

    @Deprecated
    public void setRemoteDeviceListener(Context context, IRemoteDeviceListener iRemoteDeviceListener) {
        setRemoteDeviceListener(context, RemoteDeviceInfo.MANUFACTURER_XIAOMI, iRemoteDeviceListener);
    }

    public void setShareCallback(Context context, MirrorShareCallback mirrorShareCallback) {
        this.mShareCallback = new WeakReference<>(mirrorShareCallback);
        IMirrorShareCallback.Stub stub = new IMirrorShareCallback.Stub() { // from class: com.xiaomi.mirror.synergy.MiuiSynergySdk.11
            @Override // com.xiaomi.mirror.IMirrorShareCallback
            public void onStateChanged(String str) {
                MirrorShareCallback mirrorShareCallback2 = (MirrorShareCallback) MiuiSynergySdk.this.mShareCallback.get();
                if (mirrorShareCallback2 != null) {
                    mirrorShareCallback2.onStateChanged(str);
                }
            }
        };
        Bundle bundle = new Bundle();
        bundle.putBinder(CallMethod.ARG_SHARE_CALLBACK, stub.asBinder());
        if (CallMethod.doCall(context.getContentResolver(), CallMethod.METHOD_SET_SHARE_CALLBACK, null, bundle) == null) {
            throw new RuntimeException("local miui+ not support");
        }
    }

    public void startRemoteMainMirrorDisplay(Context context, final String str, RelayAppCallback relayAppCallback) {
        final Bundle bundle = new Bundle();
        bundle.putString(CallMethod.ARG_REMOTE_DEVICE_ID, str);
        this.mRelayAppCallbackMap.put(str, relayAppCallback);
        final ContentResolver contentResolver = context.getContentResolver();
        this.mExecutor.execute(new Runnable() { // from class: com.xiaomi.mirror.synergy.MiuiSynergySdk.8
            @Override // java.lang.Runnable
            public void run() {
                Bundle bundleDoCall = null;
                try {
                    bundleDoCall = CallMethod.doCall(contentResolver, CallMethod.METHOD_START_REMOTE_MAIN_MIRROR_DISPLAY, null, bundle);
                } catch (Exception unused) {
                }
                RelayAppCallback relayAppCallback2 = (RelayAppCallback) MiuiSynergySdk.this.mRelayAppCallbackMap.get(str);
                if (relayAppCallback2 != null) {
                    int i = bundleDoCall != null ? bundleDoCall.getInt("value", -1) : -1;
                    if (i >= 0) {
                        relayAppCallback2.onSuccess();
                    } else {
                        relayAppCallback2.onFailure(i);
                    }
                }
                MiuiSynergySdk.this.mRelayAppCallbackMap.remove(str);
            }
        });
    }

    public boolean startShare(Context context, String str, boolean z) {
        Bundle bundle = new Bundle();
        bundle.putString("deviceId", str);
        bundle.putBoolean(CallMethod.ARG_IS_START, z);
        Bundle bundleDoCall = CallMethod.doCall(context.getContentResolver(), CallMethod.METHOD_START_SHARE, null, bundle);
        if (bundleDoCall != null) {
            return bundleDoCall.getBoolean("enable");
        }
        throw new RuntimeException("local miui+ not support");
    }

    public int takePhotoCancel(Activity activity) {
        return takePhotoCancel(activity, "");
    }

    public int takePhotoCancel(Activity activity, String str) {
        Bundle bundle = new Bundle();
        bundle.putInt(CallMethod.ARG_DISPLAY_ID, activity.getWindow().getDecorView().getDisplay().getDisplayId());
        if (!TextUtils.isEmpty(str)) {
            bundle.putString("id", str);
        }
        try {
            CallMethod.doCall(activity.getContentResolver(), CallMethod.METHOD_TAKE_PHOTO_CANCEL, null, bundle);
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public int takePhotoOnSynergy(Activity activity, ChooseFileCallback chooseFileCallback) {
        return takePhotoOnSynergy(activity, "", chooseFileCallback);
    }

    public int takePhotoOnSynergy(Activity activity, String str, final ChooseFileCallback chooseFileCallback) {
        final Bundle bundle = new Bundle();
        bundle.putInt(CallMethod.ARG_DISPLAY_ID, activity.getWindow().getDecorView().getDisplay().getDisplayId());
        bundle.putString("id", str);
        final ContentResolver contentResolver = activity.getContentResolver();
        this.mExecutor.execute(new Runnable() { // from class: com.xiaomi.mirror.synergy.MiuiSynergySdk.4
            @Override // java.lang.Runnable
            public void run() {
                Bundle bundleDoCall = CallMethod.doCall(contentResolver, CallMethod.METHOD_TAKE_PHOTO_FROM_SYNERGY, null, bundle);
                chooseFileCallback.onFileChosen(bundleDoCall != null ? (ClipData) bundleDoCall.getParcelable("clipData") : null);
            }
        });
        return 0;
    }

    public int unRegisterSameAccountApCallback(Context context) {
        try {
            CallMethod.doCall(context.getContentResolver(), CallMethod.METHOD_UNREGISTER_AP_CALLBACK, null, null);
            this.mSameAccountApCallback = null;
            return 0;
        } catch (Exception unused) {
            return -1;
        }
    }

    public int unregisterTakePhotoCallback(Context context, MirrorTakePhotoCallback mirrorTakePhotoCallback) {
        TakingPhotoManager takingPhotoManager = this.mTakingPhotoManager;
        if (takingPhotoManager == null) {
            return -1;
        }
        return takingPhotoManager.unregisterTakePhotoCallback(context, mirrorTakePhotoCallback);
    }

    public int updateTitle(Context context, String str) {
        Bundle bundle = new Bundle();
        bundle.putString("title", str);
        try {
            CallMethod.doCall(context.getContentResolver(), CallMethod.METHOD_UPDATE_TITLE, null, bundle);
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}
