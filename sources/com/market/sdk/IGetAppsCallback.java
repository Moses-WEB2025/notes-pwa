package com.market.sdk;

import java.util.List;

/* JADX INFO: loaded from: classes2.dex */
public interface IGetAppsCallback {
    void onLoadFailed(int i);

    void onLoadSuccess(List<AppUpdate64> list);
}
