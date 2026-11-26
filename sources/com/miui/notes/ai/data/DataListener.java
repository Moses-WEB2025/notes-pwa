package com.miui.notes.ai.data;

import com.miui.notes.ai.data.output.AiOutputData;
import com.miui.notes.ai.network.NetworkError;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public interface DataListener {
    void finishListen();

    void listenRecommendData(List<String> list);

    void listenString(AiOutputData aiOutputData);

    void netError(NetworkError networkError);
}
