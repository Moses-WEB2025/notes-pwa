package com.miui.mishare.tapshare;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public class TapShareDataHandler {
    private static final String TAG = "TapShareDataHandler";
    protected final Context mContext;
    private TapShareConnectivity mTapShareConnectivity;
    private Bundle mTapShareInfo;

    protected TapShareDataHandler(Context context) {
        this.mContext = context;
    }

    protected TapShareDataHandler(TapShareConnectivity tapShareConnectivity, Context context) {
        this.mTapShareConnectivity = tapShareConnectivity;
        this.mContext = context;
    }

    protected TapShareDataHandler(TapShareConnectivity tapShareConnectivity, Context context, Bundle bundle) {
        this.mTapShareConnectivity = tapShareConnectivity;
        this.mContext = context;
        this.mTapShareInfo = bundle;
    }

    public int sendByUris(List<Uri> list, TapShareSendBean tapShareSendBean) {
        Log.i(TAG, "sendByUris called!");
        if (this.mContext != null) {
            try {
                Iterator<Uri> it = list.iterator();
                while (it.hasNext()) {
                    this.mContext.grantUriPermission("com.miui.mishare.connectivity", it.next(), 1);
                }
                if (tapShareSendBean != null) {
                    if (tapShareSendBean.getPreviewUri() != null) {
                        this.mContext.grantUriPermission("com.miui.mishare.connectivity", tapShareSendBean.getPreviewUri(), 1);
                    }
                    List<TapSharePreviewBean> previewInfoList = tapShareSendBean.getPreviewInfoList();
                    if (previewInfoList != null) {
                        Iterator<TapSharePreviewBean> it2 = previewInfoList.iterator();
                        while (it2.hasNext()) {
                            this.mContext.grantUriPermission("com.miui.mishare.connectivity", it2.next().getUri(), 1);
                        }
                    }
                }
            } catch (Exception unused) {
                Log.e(TAG, "uri grant permission failed!");
            }
        }
        TapShareConnectivity tapShareConnectivity = this.mTapShareConnectivity;
        if (tapShareConnectivity == null) {
            return 0;
        }
        tapShareConnectivity.sendByUris(list, tapShareSendBean);
        return 0;
    }

    public int sendByString(String str, TapShareSendBean tapShareSendBean) {
        Log.i(TAG, "sendByString called!");
        if (this.mContext != null && tapShareSendBean != null && tapShareSendBean.getPreviewUri() != null) {
            this.mContext.grantUriPermission("com.miui.mishare.connectivity", tapShareSendBean.getPreviewUri(), 1);
        }
        TapShareConnectivity tapShareConnectivity = this.mTapShareConnectivity;
        if (tapShareConnectivity == null) {
            return 0;
        }
        tapShareConnectivity.sendByString(str, tapShareSendBean);
        return 0;
    }

    public int sendByLinkAndUris(String str, List<Uri> list, TapShareSendBean tapShareSendBean) {
        Log.i(TAG, "sendByLinkAndUris!");
        if (this.mContext != null) {
            try {
                Iterator<Uri> it = list.iterator();
                while (it.hasNext()) {
                    this.mContext.grantUriPermission("com.miui.mishare.connectivity", it.next(), 1);
                }
                if (tapShareSendBean != null) {
                    if (tapShareSendBean.getPreviewUri() != null) {
                        this.mContext.grantUriPermission("com.miui.mishare.connectivity", tapShareSendBean.getPreviewUri(), 1);
                    }
                    List<TapSharePreviewBean> previewInfoList = tapShareSendBean.getPreviewInfoList();
                    if (previewInfoList != null) {
                        Iterator<TapSharePreviewBean> it2 = previewInfoList.iterator();
                        while (it2.hasNext()) {
                            this.mContext.grantUriPermission("com.miui.mishare.connectivity", it2.next().getUri(), 1);
                        }
                    }
                }
            } catch (Exception unused) {
                Log.e(TAG, "uri grant permission failed!");
            }
        }
        TapShareConnectivity tapShareConnectivity = this.mTapShareConnectivity;
        if (tapShareConnectivity == null) {
            return 0;
        }
        tapShareConnectivity.sendByLinkAndUris(str, list, tapShareSendBean);
        return 0;
    }

    public int cancelSend(int i, String str) {
        Log.i(TAG, "cancelSend called!");
        TapShareConnectivity tapShareConnectivity = this.mTapShareConnectivity;
        if (tapShareConnectivity == null) {
            return 0;
        }
        tapShareConnectivity.cancelSend(i, str);
        return 0;
    }

    public Bundle getTapShareInfo() {
        Log.i(TAG, "getTapShareInfo called!");
        return this.mTapShareInfo;
    }
}
