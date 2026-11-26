package com.miui.notes.ai.network;

import android.content.Context;
import com.miui.notes.ai.data.DataListener;
import com.miui.notes.ai.local.LLMSearchListener;
import com.miui.notes.ai.local.LocalMIAIUtils;
import com.miui.notes.base.utils.Logger;

/* JADX INFO: loaded from: classes4.dex */
public class AINetWorkUtils {
    private LocalMIAIUtils localMIAIUtils;
    private boolean mCloudIsDevice;
    private boolean mIsSupportSearchDevice;
    private NetworkUtils networkUtils;

    public AINetWorkUtils(Context context, boolean z, boolean z2) {
        this.mIsSupportSearchDevice = z2;
        this.mCloudIsDevice = z;
        if (z) {
            this.networkUtils = new NetworkUtils(context);
        } else {
            this.localMIAIUtils = new LocalMIAIUtils(context);
        }
    }

    public void initManger() {
        if (this.mCloudIsDevice) {
            return;
        }
        this.localMIAIUtils.initManager();
    }

    public void startGetRecommendData(String str, DataListener dataListener) {
        if (this.mCloudIsDevice) {
            this.networkUtils.startGetRecommendData(str, dataListener);
        }
    }

    public void startRequest(DataListener dataListener, boolean z) {
        if (this.mCloudIsDevice) {
            this.networkUtils.startRequest(dataListener, Boolean.valueOf(z));
        } else {
            this.localMIAIUtils.startRequest(dataListener, z ? 1 : 0);
        }
    }

    public void stopRequest() {
        if (this.mCloudIsDevice) {
            this.networkUtils.stopRequest();
        } else {
            this.localMIAIUtils.stopRequest();
        }
    }

    public void releaseConnect() {
        LocalMIAIUtils localMIAIUtils;
        if (this.mCloudIsDevice || (localMIAIUtils = this.localMIAIUtils) == null) {
            return;
        }
        localMIAIUtils.cancelRequest();
    }

    public void searchDevice(LLMSearchListener lLMSearchListener) {
        LocalMIAIUtils localMIAIUtils;
        Logger.INSTANCE.d("AINetWorkUtils", "searchDevice localMIAIUtils:" + this.localMIAIUtils);
        if (!this.mIsSupportSearchDevice || (localMIAIUtils = this.localMIAIUtils) == null) {
            return;
        }
        localMIAIUtils.setLLMSearchListener(lLMSearchListener);
        this.localMIAIUtils.searchDevice();
    }

    public void cancelSearchDevice() {
        LocalMIAIUtils localMIAIUtils;
        Logger.INSTANCE.d("AINetWorkUtils", "cancelSearchDevice localMIAIUtils:" + this.localMIAIUtils);
        if (!this.mIsSupportSearchDevice || (localMIAIUtils = this.localMIAIUtils) == null) {
            return;
        }
        localMIAIUtils.cancelSearchDevice();
    }

    public boolean textEditCheck() {
        Logger.INSTANCE.d("AINetWorkUtils", "textEditCheck localMIAIUtils:" + this.localMIAIUtils);
        LocalMIAIUtils localMIAIUtils = this.localMIAIUtils;
        if (localMIAIUtils != null) {
            return localMIAIUtils.textEditCheck();
        }
        return false;
    }

    public void initModel(String str) {
        Logger.INSTANCE.d("AINetWorkUtils", "initModel");
        LocalMIAIUtils localMIAIUtils = this.localMIAIUtils;
        if (localMIAIUtils != null) {
            localMIAIUtils.initModel(str);
        }
    }
}
