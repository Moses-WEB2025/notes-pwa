package com.xiaomi.aiinput;

import android.content.Context;
import android.view.View;

/* JADX INFO: loaded from: classes4.dex */
public class AIInputTextManager {
    public static final String AIInputText_SERVICE = "aiinputtext";
    public static final int AI_TEXT_GET = 2;
    public static final int AI_TEXT_INSERT = 1;
    public static final int AI_TEXT_REPLACE = 0;

    public interface AITextCallback {
        void onFocusedAITextEdit(View view, String str, int i, AIInputResultListener aIInputResultListener);

        void onRequestFocusedAITextInfo(View view, AIInputResultListener aIInputResultListener);
    }

    public static AIInputTextManager getInstance(Context context) {
        return null;
    }

    public IAIInputTextManagerClient getAIInputTextClient() {
        return null;
    }

    public void registerAITextCallback(AITextCallback aITextCallback) {
    }

    public void unregisterAITextCallback(AITextCallback aITextCallback) {
    }
}
