package com.miui.notes.ai.network.fake;

import com.miui.notes.ai.data.DataListener;

/* JADX INFO: loaded from: classes4.dex */
public class FakeNetworkUtils {
    public void startRequest(DataListener dataListener) {
        FakeNetworkResponse.mockGetResult(dataListener);
    }

    public void startGetRecommendDate(DataListener dataListener) {
        FakeNetworkResponse.mockGetRecommendDate(dataListener);
    }
}
