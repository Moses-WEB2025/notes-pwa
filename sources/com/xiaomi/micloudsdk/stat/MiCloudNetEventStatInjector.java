package com.xiaomi.micloudsdk.stat;

import android.os.Looper;

/* JADX INFO: loaded from: classes4.dex */
public class MiCloudNetEventStatInjector {
    public static final String ACTION_RECEIVE_NET_STAT_PARAM = "com.miui.cloudservice.action.RECEIVE_NET_STAT_PARAM";
    public static final String KEY_NET_STAT_PARAM = "net_stat_param";
    public static final String PKG_NET_STAT_RECEIVER = "com.miui.cloudservice";
    public static final int RESULT_TYPE_FAILED = 1;
    public static final int RESULT_TYPE_SUCCESS = 0;
    public static final int RESULT_TYPE_TIMEOUT = 2;
    private IMiCloudDownloadFileNetEventStatCallback mIMiCloudDownloadFileNetEventStatCallback;
    private IMiCloudNetEventStatCallback mIMiCloudStatCallback;
    private boolean mIsDownloadCallbackInitialized;
    private boolean mIsInitialized;

    private MiCloudNetEventStatInjector() {
        this.mIsInitialized = false;
        this.mIsDownloadCallbackInitialized = false;
    }

    public static MiCloudNetEventStatInjector getInstance() {
        return MiCloudNetEventStatInjectorHolder.instance;
    }

    public void init(IMiCloudNetEventStatCallback iMiCloudNetEventStatCallback) {
        if (!Looper.getMainLooper().getThread().equals(Thread.currentThread())) {
            throw new IllegalStateException("init() must be invoked in main thread");
        }
        if (this.mIsInitialized) {
            return;
        }
        this.mIMiCloudStatCallback = iMiCloudNetEventStatCallback;
        this.mIsInitialized = true;
    }

    public void initDownloadFile(IMiCloudDownloadFileNetEventStatCallback iMiCloudDownloadFileNetEventStatCallback) {
        if (!Looper.getMainLooper().getThread().equals(Thread.currentThread())) {
            throw new IllegalStateException("initDownloadFile() must be invoked in main thread");
        }
        if (this.mIsDownloadCallbackInitialized) {
            return;
        }
        this.mIMiCloudDownloadFileNetEventStatCallback = iMiCloudDownloadFileNetEventStatCallback;
        this.mIsDownloadCallbackInitialized = true;
    }

    public void addNetSuccessEvent(NetSuccessStatParam netSuccessStatParam) {
        IMiCloudNetEventStatCallback iMiCloudNetEventStatCallback = this.mIMiCloudStatCallback;
        if (iMiCloudNetEventStatCallback != null) {
            iMiCloudNetEventStatCallback.onAddNetSuccessEvent(netSuccessStatParam);
        }
    }

    public void addNetFailedEvent(NetFailedStatParam netFailedStatParam) {
        IMiCloudNetEventStatCallback iMiCloudNetEventStatCallback = this.mIMiCloudStatCallback;
        if (iMiCloudNetEventStatCallback != null) {
            iMiCloudNetEventStatCallback.onAddNetFailedEvent(netFailedStatParam);
        }
    }

    public boolean isGetDownloadFileRequestUrl(String str, String str2) {
        IMiCloudDownloadFileNetEventStatCallback iMiCloudDownloadFileNetEventStatCallback = this.mIMiCloudDownloadFileNetEventStatCallback;
        return iMiCloudDownloadFileNetEventStatCallback != null && iMiCloudDownloadFileNetEventStatCallback.isGetDownloadFileRequestUrl(str, str2);
    }

    public void addGetDownloadFileUrlsFailedEvent(GetDownloadFileUrlFailedStatParam getDownloadFileUrlFailedStatParam) {
        IMiCloudDownloadFileNetEventStatCallback iMiCloudDownloadFileNetEventStatCallback = this.mIMiCloudDownloadFileNetEventStatCallback;
        if (iMiCloudDownloadFileNetEventStatCallback != null) {
            iMiCloudDownloadFileNetEventStatCallback.onAddGetDownloadFileUrlsFailedEvent(getDownloadFileUrlFailedStatParam);
        }
    }

    public void addAddDownloadFileFailedEvent(DownloadFileFailedStatParam downloadFileFailedStatParam) {
        IMiCloudDownloadFileNetEventStatCallback iMiCloudDownloadFileNetEventStatCallback = this.mIMiCloudDownloadFileNetEventStatCallback;
        if (iMiCloudDownloadFileNetEventStatCallback != null) {
            iMiCloudDownloadFileNetEventStatCallback.onAddDownloadFileFailedEvent(downloadFileFailedStatParam);
        }
    }

    private static class MiCloudNetEventStatInjectorHolder {
        private static final MiCloudNetEventStatInjector instance = new MiCloudNetEventStatInjector();

        private MiCloudNetEventStatInjectorHolder() {
        }
    }
}
