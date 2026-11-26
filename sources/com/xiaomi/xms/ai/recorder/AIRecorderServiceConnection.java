package com.xiaomi.xms.ai.recorder;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import androidx.core.app.NotificationCompat;
import com.android.soundrecorder.ai.base.IAIRecordService;
import com.android.soundrecorder.ai.base.IAIRecorderClientStub;
import com.android.soundrecorder.ai.base.constants.BaseConstants;
import com.xiaomi.mirror.synergy.CallMethod;
import com.xiaomi.xms.ai.recorder.callback.AIRecorderServiceStateCallback;
import com.xiaomi.xms.ai.recorder.callback.BindServiceCallback;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: AIRecorderServiceConnection.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010#\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0005H\u0007J\n\u0010\u0014\u001a\u0004\u0018\u00010\u000bH\u0007J\u0010\u0010\u0015\u001a\u00020\u00122\u0006\u0010\u0016\u001a\u00020\u0017H\u0002J\b\u0010\u0018\u001a\u00020\u0012H\u0007J\b\u0010\u0019\u001a\u00020\u0012H\u0007R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\f\u001a\u0004\u0018\u00010\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u0016\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000¨\u0006\u001a"}, d2 = {"Lcom/xiaomi/xms/ai/recorder/AIRecorderServiceConnection;", "", "()V", "bindServiceCallbacks", "", "Lcom/xiaomi/xms/ai/recorder/callback/BindServiceCallback;", "connectStartTime", "", "connection", "Landroid/content/ServiceConnection;", NotificationCompat.CATEGORY_SERVICE, "Lcom/android/soundrecorder/ai/base/IAIRecordService;", "serviceStartIntent", "Landroid/content/Intent;", "stateCallbacks", "", "Lcom/xiaomi/xms/ai/recorder/callback/AIRecorderServiceStateCallback;", "bindService", "", CallMethod.ARG_CALLBACK, "getService", "registerClient", "packageName", "", "stopService", "unbindService", "AIRecorderSDK_debug"}, k = 1, mv = {1, 8, 0}, xi = 48)
public final class AIRecorderServiceConnection {
    private static long connectStartTime;
    private static IAIRecordService service;
    private static Intent serviceStartIntent;
    public static final AIRecorderServiceConnection INSTANCE = new AIRecorderServiceConnection();
    public static final Set<AIRecorderServiceStateCallback> stateCallbacks = new HashSet();
    private static final Set<BindServiceCallback> bindServiceCallbacks = new LinkedHashSet();
    private static final ServiceConnection connection = new ServiceConnection() { // from class: com.xiaomi.xms.ai.recorder.AIRecorderServiceConnection$connection$1
        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName name, IBinder binder) {
            Intrinsics.checkNotNullParameter(name, "name");
            Intrinsics.checkNotNullParameter(binder, "binder");
            long jCurrentTimeMillis = System.currentTimeMillis() - AIRecorderServiceConnection.connectStartTime;
            AIRecorderServiceConnection aIRecorderServiceConnection = AIRecorderServiceConnection.INSTANCE;
            AIRecorderServiceConnection.service = IAIRecordService.Stub.asInterface(binder);
            Context context = AIRecorderManager.instance.getContext();
            String str = context.getApplicationInfo().packageName;
            Intrinsics.checkNotNullExpressionValue(str, "context.applicationInfo.packageName");
            AIRecorderServiceConnection.INSTANCE.registerClient(str);
            Iterator<AIRecorderServiceStateCallback> it = AIRecorderServiceConnection.stateCallbacks.iterator();
            while (it.hasNext()) {
                it.next().onServiceConnected(name, binder);
            }
            if (Constants.RECORDER_PACKAGE_NAME.equals(str)) {
                AIRecorderServiceConnection aIRecorderServiceConnection2 = AIRecorderServiceConnection.INSTANCE;
                Intent intent = new Intent();
                intent.putExtra(BaseConstants.CALLER_PACKAGE_NAME, str);
                intent.setComponent(new ComponentName(Constants.RECORDER_PACKAGE_NAME, "com.android.soundrecorder.ai.airecorder.AIRecordService"));
                AIRecorderServiceConnection.serviceStartIntent = intent;
                if (context != null) {
                    Intent intent2 = AIRecorderServiceConnection.serviceStartIntent;
                    Intrinsics.checkNotNull(intent2);
                    context.startService(intent2);
                }
            }
            for (BindServiceCallback bindServiceCallback : AIRecorderServiceConnection.bindServiceCallbacks) {
                Log.w(Constants.TAG, "bindServiceCallback: " + bindServiceCallback);
                bindServiceCallback.onServiceConnected();
            }
            AIRecorderServiceConnection.bindServiceCallbacks.clear();
            Log.d(Constants.TAG, "AIRecordService Connected ,connect duration:" + jCurrentTimeMillis);
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName name) {
            Intrinsics.checkNotNullParameter(name, "name");
            Log.d(Constants.TAG, "AIRecordService Disconnect bindServiceCallbacks: " + CollectionsKt.toList(AIRecorderServiceConnection.bindServiceCallbacks));
            AIRecorderServiceConnection aIRecorderServiceConnection = AIRecorderServiceConnection.INSTANCE;
            AIRecorderServiceConnection.service = null;
            AIRecorderServiceConnection.bindServiceCallbacks.clear();
            Iterator<AIRecorderServiceStateCallback> it = AIRecorderServiceConnection.stateCallbacks.iterator();
            while (it.hasNext()) {
                it.next().onServiceDisconnected(name);
            }
        }
    };

    @JvmStatic
    public static final void stopService() {
    }

    private AIRecorderServiceConnection() {
    }

    @JvmStatic
    public static final void bindService(BindServiceCallback callback) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        Log.w(Constants.TAG, "bindService add callback: " + callback);
        bindServiceCallbacks.add(callback);
        Context context = AIRecorderManager.instance.getContext();
        String str = context.getApplicationInfo().packageName;
        Intrinsics.checkNotNullExpressionValue(str, "context.applicationInfo.packageName");
        Intent intent = new Intent();
        Log.d(Constants.TAG, "bindService:" + str);
        intent.putExtra(BaseConstants.CALLER_PACKAGE_NAME, str);
        intent.setComponent(new ComponentName(Constants.RECORDER_PACKAGE_NAME, "com.android.soundrecorder.ai.airecorder.AIRecordService"));
        if (context != null) {
            connectStartTime = System.currentTimeMillis();
            Log.d(Constants.TAG, str + " bindService " + context.bindService(intent, connection, 1));
        }
    }

    @JvmStatic
    public static final void unbindService() {
        Context context = AIRecorderManager.instance.getContext();
        if (context != null) {
            context.unbindService(connection);
        }
        bindServiceCallbacks.clear();
    }

    @JvmStatic
    public static final IAIRecordService getService() {
        return service;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void registerClient(final String packageName) {
        try {
            IAIRecordService iAIRecordService = service;
            if (iAIRecordService != null) {
                iAIRecordService.registerClient(new IAIRecorderClientStub.Stub() { // from class: com.xiaomi.xms.ai.recorder.AIRecorderServiceConnection.registerClient.1
                    @Override // com.android.soundrecorder.ai.base.IAIRecorderClientStub
                    /* JADX INFO: renamed from: register, reason: from getter */
                    public String get$packageName() throws RemoteException {
                        return packageName;
                    }
                });
            }
        } catch (RemoteException e) {
            Log.e(Constants.TAG, "registerClient fail", e);
        }
    }
}
