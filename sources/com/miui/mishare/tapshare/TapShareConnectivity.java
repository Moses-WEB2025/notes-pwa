package com.miui.mishare.tapshare;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes3.dex */
public class TapShareConnectivity {
    public static final String ACTION_APP_BIND_TAP_SHARE = "com.miui.mishare.action.APP_BIND_TAP_SHARE";
    private static final int BIND_RETRY_DELAY = 5;
    private static final String TAG = "TapShareConnectivity";
    private Context mContext;
    private CountDownLatch mCountDownLatch;
    private final ITapShareSendCallback mTapShareSendInternalCallback;
    private ITapShareService mTapShareService;
    private Runnable mUnBindServiceRunnable;
    private final ServiceConnection mConnection = new MyServiceConnection();
    private final Handler mHandler = new Handler(Looper.getMainLooper());
    private final ExecutorService mExecutor = Executors.newSingleThreadExecutor();

    public TapShareConnectivity(ITapShareSendCallback iTapShareSendCallback) {
        this.mTapShareSendInternalCallback = iTapShareSendCallback;
    }

    private final class MyServiceConnection implements ServiceConnection {
        private MyServiceConnection() {
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.i(TapShareConnectivity.TAG, "onServiceConnected");
            TapShareConnectivity.this.mTapShareService = ITapShareService.Stub.asInterface(iBinder);
            if (TapShareConnectivity.this.mCountDownLatch != null) {
                TapShareConnectivity.this.mCountDownLatch.countDown();
            }
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            Log.w(TapShareConnectivity.TAG, "onServiceDisconnected");
            TapShareConnectivity.this.mTapShareService = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void bindTapShareService(Context context) {
        boolean zBindService;
        if (this.mTapShareService == null && context != null) {
            this.mContext = context;
            Log.i(TAG, "start bind tap share service!");
            this.mCountDownLatch = new CountDownLatch(1);
            try {
                Intent intent = new Intent();
                intent.setAction(ACTION_APP_BIND_TAP_SHARE);
                intent.setComponent(new ComponentName("com.miui.mishare.connectivity", "com.miui.mishare.connectivity.TapShareSdkService"));
                zBindService = context.bindService(intent, this.mConnection, 1);
            } catch (Exception unused) {
                Log.e(TAG, "bind service exception!");
                zBindService = false;
            }
            if (!zBindService) {
                Log.e(TAG, "call bindService method failed!");
                return;
            }
            try {
                Log.i(TAG, "bind Result: " + this.mCountDownLatch.await(5L, TimeUnit.SECONDS));
            } catch (InterruptedException unused2) {
                Log.w(TAG, "countDownLatch failed");
            }
        }
    }

    protected void registerTapShareSendCallback(final Context context, final TapShareRegisterBean tapShareRegisterBean, final String str) {
        this.mExecutor.execute(new Runnable() { // from class: com.miui.mishare.tapshare.TapShareConnectivity.1
            @Override // java.lang.Runnable
            public void run() {
                if (TapShareConnectivity.this.mUnBindServiceRunnable != null) {
                    TapShareConnectivity.this.mHandler.removeCallbacks(TapShareConnectivity.this.mUnBindServiceRunnable);
                    TapShareConnectivity.this.mUnBindServiceRunnable = null;
                    Log.i(TapShareConnectivity.TAG, "cancel unbind service task by register!");
                }
                TapShareConnectivity.this.bindTapShareService(context);
                Log.i(TapShareConnectivity.TAG, "registerTapShareSendCallback, mTapShareService == null: " + (TapShareConnectivity.this.mTapShareService == null));
                try {
                    if (TapShareConnectivity.this.isServiceAvailable()) {
                        int serviceVersion = TapShareConnectivity.this.mTapShareService.getServiceVersion();
                        Log.i(TapShareConnectivity.TAG, "service version: " + serviceVersion);
                        if (serviceVersion >= 1) {
                            Log.i(TapShareConnectivity.TAG, "call registerTapShareSendCallbackV2!");
                            TapShareConnectivity.this.mTapShareService.registerTapShareSendCallbackV2(TapShareConnectivity.this.mTapShareSendInternalCallback, str, tapShareRegisterBean);
                            return;
                        } else {
                            if (serviceVersion == 0) {
                                Log.i(TapShareConnectivity.TAG, "call registerTapShareSendCallback!");
                                TapShareConnectivity.this.mTapShareService.registerTapShareSendCallback(TapShareConnectivity.this.mTapShareSendInternalCallback, str);
                                return;
                            }
                            return;
                        }
                    }
                    Log.e(TapShareConnectivity.TAG, "tap share service not available!");
                } catch (Exception e) {
                    Log.e(TapShareConnectivity.TAG, "registerTapShareSendCallback failed", e);
                }
            }
        });
    }

    protected void unregisterTapShareSendCallback() {
        this.mExecutor.execute(new Runnable() { // from class: com.miui.mishare.tapshare.TapShareConnectivity.2
            @Override // java.lang.Runnable
            public void run() {
                Log.i(TapShareConnectivity.TAG, "unregisterTapShareSendCallback, mTapShareService == null: " + (TapShareConnectivity.this.mTapShareService == null));
                try {
                    if (TapShareConnectivity.this.isServiceAvailable()) {
                        Log.i(TapShareConnectivity.TAG, "call unregisterTapShareSendCallback!");
                        TapShareConnectivity.this.mTapShareService.unregisterTapShareSendCallback(TapShareConnectivity.this.mTapShareSendInternalCallback);
                        TapShareConnectivity.this.unBindService();
                    }
                } catch (Exception e) {
                    Log.e(TapShareConnectivity.TAG, "unregisterTapShareSendCallback failed", e);
                }
            }
        });
    }

    protected void notifyShowTipsView(final Context context) {
        this.mExecutor.execute(new Runnable() { // from class: com.miui.mishare.tapshare.TapShareConnectivity.3
            @Override // java.lang.Runnable
            public void run() {
                TapShareConnectivity.this.bindTapShareService(context);
                try {
                    if (TapShareConnectivity.this.isServiceAvailable()) {
                        Log.i(TapShareConnectivity.TAG, "call notifyShowTipsView!");
                        TapShareConnectivity.this.mTapShareService.notifyShowTipsView();
                    }
                } catch (Exception e) {
                    Log.e(TapShareConnectivity.TAG, "notifyShowTipsView failed", e);
                }
            }
        });
    }

    protected void notifyHiddenTipsView() {
        this.mExecutor.execute(new Runnable() { // from class: com.miui.mishare.tapshare.TapShareConnectivity.4
            @Override // java.lang.Runnable
            public void run() {
                try {
                    if (TapShareConnectivity.this.isServiceAvailable()) {
                        Log.i(TapShareConnectivity.TAG, "call notifyHiddenTipsView!");
                        TapShareConnectivity.this.mTapShareService.notifyHiddenTipsView();
                    }
                } catch (Exception unused) {
                    Log.e(TapShareConnectivity.TAG, "notifyHiddenTipsView failed");
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void unBindService() {
        Log.i(TAG, "unbind service after 10s by unregister!");
        if (this.mUnBindServiceRunnable == null) {
            this.mUnBindServiceRunnable = new Runnable() { // from class: com.miui.mishare.tapshare.TapShareConnectivity$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m906xa6d9ff7f();
                }
            };
        }
        this.mHandler.removeCallbacks(this.mUnBindServiceRunnable);
        this.mHandler.postDelayed(this.mUnBindServiceRunnable, 10000L);
    }

    /* JADX INFO: renamed from: lambda$unBindService$0$com-miui-mishare-tapshare-TapShareConnectivity, reason: not valid java name */
    /* synthetic */ void m906xa6d9ff7f() {
        if (this.mContext != null) {
            try {
                Log.i(TAG, "call unbind service!");
                this.mContext.unbindService(this.mConnection);
            } catch (Exception unused) {
                Log.e(TAG, "unbind tap share service failed!");
            }
            this.mTapShareService = null;
            this.mUnBindServiceRunnable = null;
        }
    }

    protected void sendByUris(final List<Uri> list, final TapShareSendBean tapShareSendBean) {
        this.mExecutor.execute(new Runnable() { // from class: com.miui.mishare.tapshare.TapShareConnectivity.5
            @Override // java.lang.Runnable
            public void run() {
                try {
                    if (TapShareConnectivity.this.isServiceAvailable()) {
                        Log.i(TapShareConnectivity.TAG, "call sendByUris!");
                        TapShareConnectivity.this.mTapShareService.sendByUris(list, tapShareSendBean);
                    }
                } catch (Exception unused) {
                    Log.e(TapShareConnectivity.TAG, "sendTapShareByUri failed");
                }
            }
        });
    }

    protected void sendByString(final String str, final TapShareSendBean tapShareSendBean) {
        this.mExecutor.execute(new Runnable() { // from class: com.miui.mishare.tapshare.TapShareConnectivity.6
            @Override // java.lang.Runnable
            public void run() {
                try {
                    if (TapShareConnectivity.this.isServiceAvailable()) {
                        Log.i(TapShareConnectivity.TAG, "call sendByString!");
                        TapShareConnectivity.this.mTapShareService.sendByString(str, tapShareSendBean);
                    }
                } catch (Exception unused) {
                    Log.e(TapShareConnectivity.TAG, "sendByString failed");
                }
            }
        });
    }

    protected void sendByLinkAndUris(final String str, final List<Uri> list, final TapShareSendBean tapShareSendBean) {
        this.mExecutor.execute(new Runnable() { // from class: com.miui.mishare.tapshare.TapShareConnectivity.7
            @Override // java.lang.Runnable
            public void run() {
                try {
                    if (TapShareConnectivity.this.isServiceAvailable()) {
                        Log.i(TapShareConnectivity.TAG, "call sendByLinkAndUris!");
                        TapShareConnectivity.this.mTapShareService.sendByLinkAndUris(str, list, tapShareSendBean);
                    }
                } catch (Exception unused) {
                    Log.e(TapShareConnectivity.TAG, "sendByLinkAndUris failed");
                }
            }
        });
    }

    protected void cancelSend(final int i, final String str) {
        this.mExecutor.execute(new Runnable() { // from class: com.miui.mishare.tapshare.TapShareConnectivity.8
            @Override // java.lang.Runnable
            public void run() {
                try {
                    if (TapShareConnectivity.this.isServiceAvailable()) {
                        Log.i(TapShareConnectivity.TAG, "call cancelSend!");
                        TapShareConnectivity.this.mTapShareService.cancelSend(i, str);
                    }
                } catch (Exception unused) {
                    Log.e(TapShareConnectivity.TAG, "cancelSend failed");
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isServiceAvailable() {
        ITapShareService iTapShareService = this.mTapShareService;
        return iTapShareService != null && iTapShareService.asBinder().isBinderAlive();
    }
}
