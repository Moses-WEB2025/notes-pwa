package com.xiaomi.mimoverbackup.backup;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.util.Log;
import android.util.Pair;
import com.xiaomi.mimoverbackup.utils.MiMoverBackupConstants;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: loaded from: classes4.dex */
public abstract class MiMoverBackupServiceBase extends Service {
    private static final String TAG = "MiMoverBackupServiceBase";
    private IBinder mBinder;
    private Messenger mClientMessenger;
    private Context mContext;
    private IMiMoverBackup mMimoverBackupImpl;
    private ExecutorService mSecretExecutor;
    private Messenger mServiceMessenger;
    private Handler mServiceMessengerHandler;
    private Handler mWorkHandler;
    private HandlerThread mWorkHandlerThread;
    private Map<String, Pair<Boolean, String>> resultMap = new HashMap();
    ParcelFileDescriptor[] pips = null;

    protected abstract IMiMoverBackup getSecretBackupImpl();

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        String str = TAG;
        Log.d(str, "onCreate");
        this.mContext = this;
        getSecretBackupImpl();
        HandlerThread handlerThread = new HandlerThread(str);
        this.mWorkHandlerThread = handlerThread;
        handlerThread.start();
        this.mWorkHandler = new WorkHalder(this, this.mWorkHandlerThread.getLooper());
        this.mServiceMessengerHandler = new ServiceMsgHandler(this, getMainLooper(), this.mWorkHandler);
        this.mServiceMessenger = new Messenger(this.mServiceMessengerHandler);
        this.mSecretExecutor = Executors.newFixedThreadPool(3, new ThreadFactory() { // from class: com.xiaomi.mimoverbackup.backup.MiMoverBackupServiceBase.1
            private final ThreadFactory defaultFactory = Executors.defaultThreadFactory();

            @Override // java.util.concurrent.ThreadFactory
            public Thread newThread(Runnable runnable) {
                Thread threadNewThread = this.defaultFactory.newThread(runnable);
                threadNewThread.setName("backup_" + threadNewThread.getName());
                return threadNewThread;
            }
        });
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind");
        if (this.mBinder == null) {
            this.mBinder = this.mServiceMessenger.getBinder();
        }
        return this.mBinder;
    }

    @Override // android.app.Service
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind");
        onCancel();
        ExecutorService executorService = this.mSecretExecutor;
        if (executorService != null && !executorService.isShutdown()) {
            this.mSecretExecutor.shutdownNow();
            this.mSecretExecutor = null;
        }
        return super.onUnbind(intent);
    }

    private class WorkHalder extends Handler {
        private final WeakReference<MiMoverBackupServiceBase> mReference;

        public WorkHalder(MiMoverBackupServiceBase miMoverBackupServiceBase, Looper looper) {
            super(looper);
            this.mReference = new WeakReference<>(miMoverBackupServiceBase);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            if (this.mReference.get() == null) {
                Log.d(MiMoverBackupServiceBase.TAG, "context is null");
                return;
            }
            if (message.replyTo == null) {
                Log.d(MiMoverBackupServiceBase.TAG, "replyTo is null");
                return;
            }
            if (MiMoverBackupServiceBase.this.mMimoverBackupImpl == null) {
                MiMoverBackupServiceBase miMoverBackupServiceBase = MiMoverBackupServiceBase.this;
                miMoverBackupServiceBase.mMimoverBackupImpl = miMoverBackupServiceBase.getSecretBackupImpl();
            }
            Log.d(MiMoverBackupServiceBase.TAG, "mMimoverBackupImpl " + MiMoverBackupServiceBase.this.mMimoverBackupImpl);
            MiMoverBackupServiceBase.this.mClientMessenger = message.replyTo;
            Bundle bundle = new Bundle();
            if (message.obj instanceof Bundle) {
                bundle.putString(MiMoverBackupConstants.PARAM_MSG_TOKEN, ((Bundle) message.obj).getString(MiMoverBackupConstants.PARAM_MSG_TOKEN, ""));
            }
            Log.d(MiMoverBackupServiceBase.TAG, "handle message " + bundle.getString(MiMoverBackupConstants.PARAM_MSG_TOKEN, ""));
            switch (message.what) {
                case 100:
                    if (MiMoverBackupServiceBase.this.messageInvalid(message)) {
                        return;
                    } else {
                        MiMoverBackupServiceBase.this.getBackupInfo(bundle);
                    }
                    break;
                case 101:
                    if (MiMoverBackupServiceBase.this.messageInvalid(message)) {
                        return;
                    }
                    ParcelFileDescriptor parcelFileDescriptorBackupData = MiMoverBackupServiceBase.this.backupData(((Bundle) message.obj).getString(MiMoverBackupConstants.PARAM_TASK_ID, ""));
                    if (parcelFileDescriptorBackupData != null) {
                        bundle.putParcelable(MiMoverBackupConstants.DATA_FD, parcelFileDescriptorBackupData);
                    }
                    break;
                case 102:
                    if (MiMoverBackupServiceBase.this.messageInvalid(message)) {
                        return;
                    }
                    String string = ((Bundle) message.obj).getString(MiMoverBackupConstants.PARAM_TASK_ID, "");
                    MiMoverBackupServiceBase.this.restoreData(string, (ParcelFileDescriptor) ((Bundle) message.obj).getParcelable(MiMoverBackupConstants.DATA_FD));
                    MiMoverBackupServiceBase.this.getResult(string, bundle);
                    break;
                case 103:
                    MiMoverBackupServiceBase.this.onCancel();
                    break;
                case 104:
                    if (MiMoverBackupServiceBase.this.messageInvalid(message)) {
                        return;
                    } else {
                        MiMoverBackupServiceBase.this.getResult(((Bundle) message.obj).getString(MiMoverBackupConstants.PARAM_TASK_ID, ""), bundle);
                    }
                    break;
            }
            MiMoverBackupServiceBase miMoverBackupServiceBase2 = MiMoverBackupServiceBase.this;
            miMoverBackupServiceBase2.returnMsg(miMoverBackupServiceBase2.mClientMessenger, message.what, bundle);
        }
    }

    private static class ServiceMsgHandler extends Handler {
        private WeakReference<MiMoverBackupServiceBase> mReference;
        private Handler mWorkHandler;

        public ServiceMsgHandler(MiMoverBackupServiceBase miMoverBackupServiceBase, Looper looper, Handler handler) {
            this.mReference = new WeakReference<>(miMoverBackupServiceBase);
            this.mWorkHandler = handler;
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            if (this.mReference.get() != null) {
                handleMsg(this.mReference.get(), message);
            }
        }

        private void handleMsg(MiMoverBackupServiceBase miMoverBackupServiceBase, Message message) {
            this.mWorkHandler.sendMessage(Message.obtain(message));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void returnMsg(Messenger messenger, int i, Bundle bundle) {
        Message messageObtain = Message.obtain(null, i, 0, 0, bundle);
        try {
            Log.d(TAG, " returnMsg " + i);
            messenger.send(messageObtain);
        } catch (RemoteException unused) {
            Log.d(TAG, StringUtils.SPACE);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean messageInvalid(Message message) {
        return message == null || !(message.obj instanceof Bundle);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getBackupInfo(Bundle bundle) {
        IMiMoverBackup iMiMoverBackup = this.mMimoverBackupImpl;
        if (iMiMoverBackup != null) {
            iMiMoverBackup.getBackupInfo(this.mContext, bundle);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public ParcelFileDescriptor backupData(String str) {
        try {
            this.pips = ParcelFileDescriptor.createSocketPair();
        } catch (IOException e) {
            Log.e(TAG, "backupData pips error", e);
        }
        if (this.pips == null) {
            return null;
        }
        this.mSecretExecutor.submit(new BackupTask(this.mContext, this.pips[1], str));
        try {
            return ParcelFileDescriptor.dup(this.pips[0].getFileDescriptor());
        } catch (IOException e2) {
            Log.e(TAG, "dup file descriptor failed", e2);
            return null;
        } finally {
            try {
                this.pips[0].close();
            } catch (IOException e3) {
                Log.e(TAG, "backupData pips close error", e3);
            }
        }
    }

    private class BackupTask implements Runnable {
        private Context mContext;
        private ParcelFileDescriptor mFd;
        private String mTaskId;

        private BackupTask(Context context, ParcelFileDescriptor parcelFileDescriptor, String str) {
            this.mContext = context;
            this.mFd = parcelFileDescriptor;
            this.mTaskId = str;
        }

        @Override // java.lang.Runnable
        public void run() throws Throwable {
            boolean z = false;
            boolean z2 = true;
            try {
                try {
                    if (MiMoverBackupServiceBase.this.mMimoverBackupImpl != null) {
                        Log.d(MiMoverBackupServiceBase.TAG, "onBackup");
                        MiMoverBackupServiceBase.this.mMimoverBackupImpl.onBackup(this.mContext, this.mFd);
                        z = true;
                    } else {
                        Log.d(MiMoverBackupServiceBase.TAG, "onBackup fail");
                    }
                    MiMoverBackupServiceBase.this.resultMap.put(this.mTaskId, new Pair(Boolean.valueOf(z), ""));
                    this.mFd.close();
                    Log.d(MiMoverBackupServiceBase.TAG, "onBackup mFd close");
                } catch (Exception e) {
                    try {
                        Log.e(MiMoverBackupServiceBase.TAG, "onBackup error", e);
                        MiMoverBackupServiceBase.this.resultMap.put(this.mTaskId, new Pair(false, ""));
                        this.mFd.close();
                        Log.d(MiMoverBackupServiceBase.TAG, "onBackup mFd close");
                    } catch (Throwable th) {
                        z2 = false;
                        th = th;
                        MiMoverBackupServiceBase.this.resultMap.put(this.mTaskId, new Pair(Boolean.valueOf(z2), ""));
                        try {
                            this.mFd.close();
                            Log.d(MiMoverBackupServiceBase.TAG, "onBackup mFd close");
                        } catch (IOException e2) {
                            Log.e(MiMoverBackupServiceBase.TAG, "BackupTask finally error", e2);
                        }
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    MiMoverBackupServiceBase.this.resultMap.put(this.mTaskId, new Pair(Boolean.valueOf(z2), ""));
                    this.mFd.close();
                    Log.d(MiMoverBackupServiceBase.TAG, "onBackup mFd close");
                    throw th;
                }
            } catch (IOException e3) {
                Log.e(MiMoverBackupServiceBase.TAG, "BackupTask finally error", e3);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void restoreData(String str, ParcelFileDescriptor parcelFileDescriptor) {
        boolean z = true;
        try {
            if (this.mMimoverBackupImpl != null) {
                Log.d(TAG, "onRestore");
                this.mMimoverBackupImpl.onRestore(this.mContext, parcelFileDescriptor);
            } else {
                z = false;
            }
        } finally {
            this.resultMap.put(str, new Pair<>(true, ""));
            try {
                parcelFileDescriptor.close();
            } catch (IOException e) {
                Log.e(TAG, "restoreData finally error", e);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getResult(String str, Bundle bundle) {
        Pair<Boolean, String> pairRemove;
        if (!this.resultMap.containsKey(str) || (pairRemove = this.resultMap.remove(str)) == null) {
            return;
        }
        Log.d(TAG, "getResult " + pairRemove.first + StringUtils.SPACE + ((String) pairRemove.second));
        bundle.putBoolean(MiMoverBackupConstants.RTN_RESULT, ((Boolean) pairRemove.first).booleanValue());
        bundle.putString(MiMoverBackupConstants.RTN_RESULT_ERROR_DESC, (String) pairRemove.second);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onCancel() {
        if (this.mMimoverBackupImpl != null) {
            Log.d(TAG, "onCancel");
            this.mMimoverBackupImpl.onCancel();
        }
    }

    @Override // android.app.Service
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
        this.mServiceMessenger = null;
        HandlerThread handlerThread = this.mWorkHandlerThread;
        if (handlerThread != null) {
            handlerThread.quitSafely();
        }
        this.resultMap.clear();
    }
}
