package com.miui.mishare.tapshare;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks2;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.RemoteException;
import android.util.Log;
import com.miui.mishare.tapshare.Util.FlipDeviceUtil;
import com.miui.mishare.tapshare.Util.TapShareConstant;
import com.miui.mishare.tapshare.Util.WindowUtil;
import java.util.concurrent.atomic.AtomicBoolean;

/* JADX INFO: loaded from: classes3.dex */
public class TapShareManager {
    private static final String TAG = "TapShareManager";
    private static volatile TapShareManager instance;
    private final Application.ActivityLifecycleCallbacks mActivityLifecycleCallbacks;
    private Context mApplicationContext;
    private final Handler mHandler;
    private String mPackageName;
    private TapShareRegisterBean mRegisterBean;
    private final TapShareConnectivity mTapShareConnectivity;
    private TapShareDataHandler mTapShareDataHandler;
    private TapShareSendCallback mTapShareSendCallback;
    private final ITapShareSendCallback mTapShareSendInternalCallback;
    private final AtomicBoolean mIsSpecialWindow = new AtomicBoolean(false);
    private final AtomicBoolean mIsShowTipsView = new AtomicBoolean(false);
    private final ComponentCallbacks2 mConfigurationChangeCallback = new ComponentCallbacks2() { // from class: com.miui.mishare.tapshare.TapShareManager.1
        @Override // android.content.ComponentCallbacks
        public void onLowMemory() {
        }

        @Override // android.content.ComponentCallbacks2
        public void onTrimMemory(int i) {
        }

        @Override // android.content.ComponentCallbacks
        public void onConfigurationChanged(Configuration configuration) {
            Log.i(TapShareManager.TAG, "onConfigurationChanged!");
            if (TapShareManager.this.mTapShareSendCallback != null) {
                boolean z = WindowUtil.isSpecialWindowMode(TapShareManager.this.mApplicationContext, configuration) || FlipDeviceUtil.isFlipDeviceFolded(TapShareManager.this.mApplicationContext);
                Log.i(TapShareManager.TAG, "currentIsSpecialWindow: " + z + " lastIsSpecialWindow: " + TapShareManager.this.mIsSpecialWindow);
                if (TapShareManager.this.mIsSpecialWindow.compareAndSet(!z, z)) {
                    if (!TapShareManager.this.mIsSpecialWindow.get()) {
                        Log.i(TapShareManager.TAG, "not special window, register again!");
                        TapShareManager.this.handleNfcTouchRegister();
                        TapShareManager.this.mTapShareConnectivity.registerTapShareSendCallback(TapShareManager.this.mApplicationContext, TapShareManager.this.mRegisterBean, TapShareManager.this.mPackageName);
                        if (TapShareManager.this.mIsShowTipsView.get()) {
                            TapShareManager.this.mTapShareConnectivity.notifyShowTipsView(TapShareManager.this.mApplicationContext);
                            return;
                        }
                        return;
                    }
                    Log.i(TapShareManager.TAG, "is special window, unregister!");
                    TapShareManager.this.mTapShareConnectivity.unregisterTapShareSendCallback();
                    if (TapShareManager.this.mIsShowTipsView.get()) {
                        TapShareManager.this.mTapShareConnectivity.notifyHiddenTipsView();
                        return;
                    }
                    return;
                }
                return;
            }
            Log.i(TapShareManager.TAG, "mTapShareSendCallback is null, return!");
        }
    };

    public interface TapShareSendCallback {
        default void onSendResult(int i) {
        }

        void onTapShareCancel(TapShareDataHandler tapShareDataHandler);

        boolean onTapShareSend(TapShareDataHandler tapShareDataHandler);
    }

    private TapShareManager() {
        ITapShareSendCallback.Stub stub = new ITapShareSendCallback.Stub() { // from class: com.miui.mishare.tapshare.TapShareManager.2
            @Override // com.miui.mishare.tapshare.ITapShareSendCallback
            public void onTapShareSend() {
                Log.i(TapShareManager.TAG, "miShare call onTapShareSend!");
                doTapShareSend(null);
            }

            @Override // com.miui.mishare.tapshare.ITapShareSendCallback
            public void onTapShareCancel() {
                Log.i(TapShareManager.TAG, "miShare call onTapShareCancel!");
                TapShareManager.this.mHandler.post(new Runnable() { // from class: com.miui.mishare.tapshare.TapShareManager.2.1
                    @Override // java.lang.Runnable
                    public void run() {
                        if (TapShareManager.this.mTapShareSendCallback != null) {
                            TapShareManager.this.mTapShareSendCallback.onTapShareCancel(TapShareManager.this.mTapShareDataHandler);
                        }
                    }
                });
            }

            @Override // com.miui.mishare.tapshare.ITapShareSendCallback
            public void onTapShareSendV2(Bundle bundle) {
                Log.i(TapShareManager.TAG, "miShare call onTapShareSendV2!");
                doTapShareSend(bundle);
            }

            @Override // com.miui.mishare.tapshare.ITapShareSendCallback
            public int getSdkVersion() throws RemoteException {
                Log.i(TapShareManager.TAG, "miShare call getSdkVersion!");
                return 2;
            }

            @Override // com.miui.mishare.tapshare.ITapShareSendCallback
            public void onSendResult(final int i) throws RemoteException {
                Log.i(TapShareManager.TAG, "miShare call onSendResult!");
                TapShareManager.this.mHandler.post(new Runnable() { // from class: com.miui.mishare.tapshare.TapShareManager.2.2
                    @Override // java.lang.Runnable
                    public void run() {
                        if (TapShareManager.this.mTapShareSendCallback != null) {
                            TapShareManager.this.mTapShareSendCallback.onSendResult(i);
                        }
                    }
                });
            }

            private void doTapShareSend(final Bundle bundle) {
                TapShareManager.this.mHandler.post(new Runnable() { // from class: com.miui.mishare.tapshare.TapShareManager.2.3
                    @Override // java.lang.Runnable
                    public void run() {
                        if (TapShareManager.this.mTapShareSendCallback != null) {
                            TapShareManager.this.mTapShareDataHandler = new TapShareDataHandler(TapShareManager.this.mTapShareConnectivity, TapShareManager.this.mApplicationContext, bundle);
                            TapShareManager.this.mTapShareSendCallback.onTapShareSend(TapShareManager.this.mTapShareDataHandler);
                        }
                    }
                });
            }
        };
        this.mTapShareSendInternalCallback = stub;
        this.mActivityLifecycleCallbacks = new Application.ActivityLifecycleCallbacks() { // from class: com.miui.mishare.tapshare.TapShareManager.3
            @Override // android.app.Application.ActivityLifecycleCallbacks
            public void onActivityCreated(Activity activity, Bundle bundle) {
            }

            @Override // android.app.Application.ActivityLifecycleCallbacks
            public void onActivityPaused(Activity activity) {
            }

            @Override // android.app.Application.ActivityLifecycleCallbacks
            public void onActivityResumed(Activity activity) {
            }

            @Override // android.app.Application.ActivityLifecycleCallbacks
            public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
            }

            @Override // android.app.Application.ActivityLifecycleCallbacks
            public void onActivityStarted(Activity activity) {
            }

            @Override // android.app.Application.ActivityLifecycleCallbacks
            public void onActivityStopped(Activity activity) {
                Log.w(TapShareManager.TAG, "unregisterSendCallback by activity life cycle call stop!");
                TapShareManager tapShareManager = TapShareManager.this;
                tapShareManager.unregisterSendCallback(activity, tapShareManager.mTapShareSendCallback);
            }

            @Override // android.app.Application.ActivityLifecycleCallbacks
            public void onActivityDestroyed(Activity activity) {
                Log.e(TapShareManager.TAG, "activity destroyed! exception case!");
            }
        };
        this.mTapShareConnectivity = new TapShareConnectivity(stub);
        this.mHandler = new Handler(Looper.getMainLooper());
    }

    public static TapShareManager getInstance() {
        if (instance == null) {
            synchronized (TapShareManager.class) {
                if (instance == null) {
                    instance = new TapShareManager();
                }
            }
        }
        return instance;
    }

    public int registerSendCallback(Activity activity, TapShareRegisterBean tapShareRegisterBean, TapShareSendCallback tapShareSendCallback) {
        if (activity == null) {
            Log.e(TAG, "activity is null, return!");
            return 4;
        }
        if (TapShareConstant.SETTING_PACKAGE_NAME.equals(activity.getPackageName())) {
            Log.e(TAG, "method called invalid!");
            return 3;
        }
        return doRegisterSendCallback(activity, tapShareRegisterBean, tapShareSendCallback, activity.getPackageName());
    }

    public int registerSendCallback(Activity activity, TapShareSendCallback tapShareSendCallback) {
        return registerSendCallback(activity, (TapShareRegisterBean) null, tapShareSendCallback);
    }

    public int registerSendCallback(Activity activity, TapShareSendCallback tapShareSendCallback, String str) {
        if (activity == null) {
            Log.e(TAG, "activity is null, return!");
            return 4;
        }
        if (!TapShareConstant.SETTING_PACKAGE_NAME.equals(activity.getPackageName())) {
            Log.e(TAG, "method called invalid!");
            return 3;
        }
        return doRegisterSendCallback(activity, null, tapShareSendCallback, str);
    }

    private int doRegisterSendCallback(Activity activity, TapShareRegisterBean tapShareRegisterBean, TapShareSendCallback tapShareSendCallback, String str) {
        Log.i(TAG, "doRegisterSendCallback called! source: " + str);
        this.mApplicationContext = activity.getApplicationContext();
        activity.registerComponentCallbacks(this.mConfigurationChangeCallback);
        Log.i(TAG, "registerActivityLifecycleCallbacks");
        activity.registerActivityLifecycleCallbacks(this.mActivityLifecycleCallbacks);
        this.mTapShareSendCallback = tapShareSendCallback;
        this.mPackageName = str;
        this.mRegisterBean = tapShareRegisterBean;
        if (WindowUtil.isSpecialWindowMode(this.mApplicationContext) || FlipDeviceUtil.isFlipDeviceFolded(this.mApplicationContext)) {
            Log.e(TAG, "is in special window, not register!");
            this.mIsSpecialWindow.set(true);
            return 0;
        }
        this.mTapShareConnectivity.registerTapShareSendCallback(this.mApplicationContext, tapShareRegisterBean, str);
        handleNfcTouchRegister();
        return 0;
    }

    public int unregisterSendCallback(Activity activity, TapShareSendCallback tapShareSendCallback) {
        Log.i(TAG, "unregisterSendCallback called!");
        if (activity != null) {
            activity.unregisterComponentCallbacks(this.mConfigurationChangeCallback);
            Log.i(TAG, "unregisterActivityLifecycleCallbacks");
            activity.unregisterActivityLifecycleCallbacks(this.mActivityLifecycleCallbacks);
            Log.i(TAG, "unregister activity callbacks");
        }
        if (this.mTapShareSendCallback == tapShareSendCallback) {
            this.mTapShareSendCallback = null;
            this.mTapShareConnectivity.unregisterTapShareSendCallback();
            return 0;
        }
        Log.w(TAG, "callback match failed, ignore unregister!");
        return 2;
    }

    public int notifyShowTipsView(Activity activity) {
        if (activity == null) {
            Log.e(TAG, "activity is null, return!");
            return 4;
        }
        this.mApplicationContext = activity.getApplicationContext();
        this.mIsShowTipsView.set(true);
        if (WindowUtil.isSpecialWindowMode(this.mApplicationContext) || FlipDeviceUtil.isFlipDeviceFolded(this.mApplicationContext)) {
            Log.e(TAG, "is in special window, not show tips!");
            return 0;
        }
        this.mTapShareConnectivity.notifyShowTipsView(this.mApplicationContext);
        return 0;
    }

    public int notifyHiddenTipsView() {
        this.mIsShowTipsView.set(false);
        this.mTapShareConnectivity.notifyHiddenTipsView();
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleNfcTouchRegister() {
        if (TapShareConstant.GALLERY_PACKAGE_NAME.equals(this.mPackageName) || TapShareConstant.FILE_EXPLORER_PACKAGE_NAME.equals(this.mPackageName) || TapShareConstant.FILE_EXPLORER_GLOBAL_PACKAGE_NAME.equals(this.mPackageName)) {
            Log.i(TAG, "nfc touch register by: " + this.mPackageName);
            NfcTouchManager.getInstance().registerOneHopShareReceiver(this.mApplicationContext, new NfcTouchManager.OneHopShareCallback() { // from class: com.miui.mishare.tapshare.TapShareManager$$ExternalSyntheticLambda0
                @Override // com.miui.mishare.tapshare.NfcTouchManager.OneHopShareCallback
                public final boolean onOneHopShare(int i, String str, int i2) {
                    return this.f$0.m907x8bad3f7f(i, str, i2);
                }
            });
        }
    }

    /* JADX INFO: renamed from: lambda$handleNfcTouchRegister$0$com-miui-mishare-tapshare-TapShareManager, reason: not valid java name */
    /* synthetic */ boolean m907x8bad3f7f(int i, String str, int i2) {
        Log.i(TAG, "get send data by nfc touch!");
        if (WindowUtil.isSpecialWindowMode(this.mApplicationContext) || FlipDeviceUtil.isFlipDeviceFolded(this.mApplicationContext)) {
            Log.i(TAG, "is in special window, handler to mirror!");
            return false;
        }
        if (this.mTapShareSendCallback != null) {
            Log.i(TAG, "handler by mishare, go get data!");
            return this.mTapShareSendCallback.onTapShareSend(new NfcTouchDataHandler(this.mApplicationContext, i, str, i2));
        }
        Log.i(TAG, "mTapShareSendCallback is null, handler to mirror!");
        return false;
    }
}
