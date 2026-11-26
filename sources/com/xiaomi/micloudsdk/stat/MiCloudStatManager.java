package com.xiaomi.micloudsdk.stat;

/* JADX INFO: loaded from: classes4.dex */
@Deprecated
public class MiCloudStatManager {
    private IMiCloudStatCallback mCldStatCallback;
    private boolean mEnable;
    private boolean mIsInitialized;

    private MiCloudStatManager() {
        this.mEnable = false;
        this.mIsInitialized = false;
    }

    public static MiCloudStatManager getInstance() {
        return Holder._instance;
    }

    public void init(IMiCloudStatCallback iMiCloudStatCallback) {
        this.mCldStatCallback = iMiCloudStatCallback;
        this.mEnable = true;
        iMiCloudStatCallback.onInitialize();
        this.mIsInitialized = true;
        this.mCldStatCallback.onSetUploadPolicy();
        this.mCldStatCallback.onSetEventFilter();
        this.mCldStatCallback.onEnableAutoRecord();
    }

    public void addHttpEvent(String str, long j, long j2, int i, String str2) {
        IMiCloudStatCallback iMiCloudStatCallback;
        if (this.mEnable && this.mIsInitialized && (iMiCloudStatCallback = this.mCldStatCallback) != null) {
            iMiCloudStatCallback.onAddHttpEvent(str, j, j2, i, str2);
        }
    }

    public void setEnable(boolean z) {
        this.mEnable = z;
    }

    public boolean getEnable() {
        return this.mEnable;
    }

    private static class Holder {
        private static final MiCloudStatManager _instance = new MiCloudStatManager();

        private Holder() {
        }
    }
}
